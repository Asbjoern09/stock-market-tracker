package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.ExchangeInfoRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.TradeValueRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.VolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.*;

@Service
@EnableScheduling
//This class is responsible for calling the API and storing the information in the database
public class APIPollingService {

    private final ExchangeInfoRepository exchangeInfoRepository;
    private final TradeValueRepository tradeValueRepository;
    private final VolumeRepository volumeRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate;

    @Autowired
    public APIPollingService(ExchangeInfoRepository exchangeInfoRepository, TradeValueRepository tradeValueRepository, VolumeRepository volumeRepository){
        this.exchangeInfoRepository = exchangeInfoRepository;
        this.tradeValueRepository = tradeValueRepository;
        this.volumeRepository = volumeRepository;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 300000)
    public JsonNode[] callApi() {
        String ticker = "";
        String apiUrl = "https://api.tiingo.com/tiingo/crypto/prices?tickers=btcusd,fldcbtc&token=<insert-token>";
        JsonNode exchangeInfoObject = objectMapper.createObjectNode();
        JsonNode volumeObject = objectMapper.createObjectNode();
        JsonNode tradeValueObject = objectMapper.createObjectNode();
        JsonNode priceDataObject = null;

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                for (JsonNode objectNode : jsonNode) {
                    JsonNode priceDataNode = objectNode.get("priceData");
                    ticker = objectNode.get("ticker").asText();
                    ((ObjectNode) exchangeInfoObject).put("ticker", ticker);
                    if (priceDataNode != null && priceDataNode.isArray() && priceDataNode.size() > 0) {
                        priceDataObject = priceDataNode.get(priceDataNode.size() - 1);
                        break;
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("API failed with status code: " + response.getStatusCodeValue());
        }
        ((ObjectNode) exchangeInfoObject).put("tradeTime", priceDataObject.get("date"));
        ((ObjectNode) exchangeInfoObject).put("trade_values_id", priceDataObject.get("date"));

        ObjectNode tradeDataNode = objectMapper.createObjectNode();
        double midValue = calculateMid(priceDataObject.get("high").asDouble(), priceDataObject.get("low").asDouble());
        tradeDataNode.put("high", priceDataObject.get("high").asDouble());
        tradeDataNode.put("mid", midValue);
        tradeDataNode.put("low", priceDataObject.get("low").asDouble());
        tradeDataNode.put("open", priceDataObject.get("open").asDouble());
        tradeDataNode.put("close", priceDataObject.get("close").asDouble());
        tradeValueObject = tradeDataNode;

        ((ObjectNode) volumeObject).put("volume", priceDataObject.get("volume").asDouble());
        ((ObjectNode) volumeObject).put("volumeNotional", priceDataObject.get("volumeNotional").asDouble());
        ((ObjectNode) volumeObject).put("tradesDone", priceDataObject.get("tradesDone").asDouble());


        saveJsonData(exchangeInfoObject, volumeObject, tradeValueObject);

        return new JsonNode[]{exchangeInfoObject, volumeObject, tradeValueObject};
    }


    public double calculateMid(double a, double b){
        double c = (a + b)/2;

        return c;
    }

    public void saveJsonData(JsonNode exchangeInfoObject, JsonNode volumeObject, JsonNode tradeValueObject) {
        Volume volume = new Volume();
        volume.setVolumeNotional(volumeObject.get("volumeNotional").asDouble());
        volume.setVolume(volumeObject.get("volume").asDouble());
        volume.setTradesDone(volumeObject.get("tradesDone").asDouble());

        TradeValue tradeValue = new TradeValue();
        tradeValue.setCloseValue(tradeValueObject.get("close").asDouble());
        tradeValue.setOpenValue(tradeValueObject.get("open").asDouble());
        tradeValue.setHighValue(tradeValueObject.get("high").asDouble());
        tradeValue.setMidValue(tradeValueObject.get("mid").asDouble());
        tradeValue.setLowValue(tradeValueObject.get("low").asDouble());

        volumeRepository.save(volume);
        tradeValueRepository.save(tradeValue);

        ExchangeInfo exchangeInfo = new ExchangeInfo();
        exchangeInfo.setTicker(exchangeInfoObject.get("ticker").asText().toUpperCase());
        String tradeTimeString = exchangeInfoObject.get("tradeTime").asText();
        tradeTimeString = tradeTimeString.substring(0, 19);
        Timestamp tradeTime = Timestamp.valueOf(tradeTimeString.replace("T", " "));
        exchangeInfo.setTradeTime(tradeTime);
        exchangeInfo.setVolume(volume);
        exchangeInfo.setTradeValue(tradeValue);
        exchangeInfoRepository.save(exchangeInfo);
    }

}
