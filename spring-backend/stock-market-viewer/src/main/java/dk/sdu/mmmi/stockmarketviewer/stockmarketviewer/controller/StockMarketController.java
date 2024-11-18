package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.controller;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.ExchangeInfoRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.TradeValueRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories.VolumeRepository;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.services.ORMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class StockMarketController {
    private ORMService ormService;

    private final TradeValueRepository tradeValueRepository;
    private final ExchangeInfoRepository exchangeInfoRepository;
    private final VolumeRepository volumeRepository;
    @Autowired
    public StockMarketController(TradeValueRepository tradeValueRepository, ExchangeInfoRepository exchangeInfoRepository, VolumeRepository volumeRepository) {
        this.tradeValueRepository = tradeValueRepository;
        this.exchangeInfoRepository = exchangeInfoRepository;
        this.volumeRepository = volumeRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        ormService = ORMService.getInstance(tradeValueRepository, exchangeInfoRepository, volumeRepository);

        Map<String, Object> lastWeekData = ormService.getAllDataFromLastWeek();
        Map<String, Object> lastDayData = ormService.getAllDataFromLastDay();
        Map<String, Object> lastMonthData = ormService.getAllDataFromLastMonth();

        model.addAttribute("lastWeekData",lastWeekData);
        model.addAttribute("lastDayData",lastDayData);
        model.addAttribute("lastMonthData",lastMonthData);

        System.out.println("Loading Page");
        return "index";
    }
}
