package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "exchange_info")
public class ExchangeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trade_values_id")
    private TradeValue tradeValue;

    @ManyToOne
    @JoinColumn(name = "volume_id")
    private Volume volume;

    private String ticker;

    @Column(name = "trade_time")
    private Timestamp tradeTime;


    public Long getId() {
        return id;
    }

    public TradeValue getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(TradeValue tradeValue) {
        this.tradeValue = tradeValue;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Timestamp getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Timestamp tradeTime) {
        this.tradeTime = tradeTime;
    }
}
