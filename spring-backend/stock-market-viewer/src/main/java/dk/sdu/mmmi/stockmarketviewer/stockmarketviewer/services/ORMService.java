package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.services;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.ExchangeInfoRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.TradeValueRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.VolumeRepository;

import java.util.*;

public class ORMService {
    private static ORMService instance;
    private final TradeValueRepository tradeValueRepository;
    private final ExchangeInfoRepository exchangeInfoRepository;
    private final VolumeRepository volumeRepository;

    private ORMService(TradeValueRepository tradeValueRepository, ExchangeInfoRepository exchangeInfoRepository, VolumeRepository volumeRepository) {
        this.tradeValueRepository = tradeValueRepository;
        this.exchangeInfoRepository = exchangeInfoRepository;
        this.volumeRepository = volumeRepository;
    }

    public static ORMService getInstance(TradeValueRepository tradeValueRepository, ExchangeInfoRepository exchangeInfoRepository, VolumeRepository volumeRepository) {
        if (instance == null) {
            instance = new ORMService(tradeValueRepository, exchangeInfoRepository, volumeRepository);
        }
        return instance;
    }

    public Map<String, Object> getAllDataFromLastWeek() {
        List<TradeValue> tradeValues = tradeValueRepository.findAllFromLastWeek(getLastWeekDate());
        List<ExchangeInfo> exchangeInfos = exchangeInfoRepository.findAllFromLastWeek(getLastWeekDate());
        List<Volume> volumes = volumeRepository.findAllFromLastWeek(getLastWeekDate());
        return collectDataForPeriod(tradeValues, exchangeInfos, volumes);
    }

    public Map<String, Object> getAllDataFromLastDay() {
        List<TradeValue> tradeValues = tradeValueRepository.findAllFromLastDay(getLastDayDate());
        List<ExchangeInfo> exchangeInfos = exchangeInfoRepository.findAllFromLastDay(getLastDayDate());
        List<Volume> volumes = volumeRepository.findAllFromLastDay(getLastDayDate());
        return collectDataForPeriod(tradeValues, exchangeInfos, volumes);
    }

    public Map<String, Object> getAllDataFromLastMonth() {
        List<TradeValue> tradeValues = tradeValueRepository.findAllFromLastMonth(getLastMonthDate());
        List<ExchangeInfo> exchangeInfos = exchangeInfoRepository.findAllFromLastMonth(getLastMonthDate());
        List<Volume> volumes = volumeRepository.findAllFromLastMonth(getLastMonthDate());
        return collectDataForPeriod(tradeValues, exchangeInfos, volumes);
    }

    private Map<String, Object> collectDataForPeriod(List<TradeValue> tradeValues, List<ExchangeInfo> exchangeInfos, List<Volume> volumes) {
        Map<String, Object> data = new HashMap<>();

        List<String> tickers = exchangeInfos.stream().map(ExchangeInfo::getTicker).toList();
        List<java.sql.Timestamp> tradeTimes = exchangeInfos.stream().map(ExchangeInfo::getTradeTime).toList();
        List<Double> highValues = tradeValues.stream().map(TradeValue::getHighValue).toList();
        List<Double> midValues = tradeValues.stream().map(TradeValue::getMidValue).toList();
        List<Double> lowValues = tradeValues.stream().map(TradeValue::getLowValue).toList();
        List<Double> volumeValues = volumes.stream().map(Volume::getVolume).toList();
        List<Double> tradesDone = volumes.stream().map(Volume::getTradesDone).toList();

        data.put("tickers", tickers);
        data.put("tradeTimes", tradeTimes);
        data.put("highValues", highValues);
        data.put("midValues", midValues);
        data.put("lowValues", lowValues);
        data.put("volumes", volumeValues);
        data.put("tradesDone", tradesDone);

        return data;
    }

    private Date getLastWeekDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        return calendar.getTime();
    }

    private Date getLastMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    private Date getLastDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
}
