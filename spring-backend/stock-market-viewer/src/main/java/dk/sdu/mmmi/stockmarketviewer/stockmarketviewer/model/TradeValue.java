package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trade_value")
public class TradeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "high_value")
    private Double highValue;

    @Column(name = "mid_value")
    private Double midValue;

    @Column(name = "low_value")
    private Double lowValue;

    @Column(name = "open_value")
    private Double openValue;

    @Column(name = "close_value")
    private Double closeValue;


    public Long getId() {
        return id;
    }

    public Double getHighValue() {
        return highValue;
    }

    public void setHighValue(Double highValue) {
        this.highValue = highValue;
    }

    public Double getMidValue() {
        return midValue;
    }

    public void setMidValue(Double midValue) {
        this.midValue = midValue;
    }

    public Double getLowValue() {
        return lowValue;
    }

    public void setLowValue(Double lowValue) {
        this.lowValue = lowValue;
    }

    public Double getOpenValue() {
        return openValue;
    }

    public void setOpenValue(Double openValue) {
        this.openValue = openValue;
    }

    public Double getCloseValue() {
        return closeValue;
    }

    public void setCloseValue(Double closeValue) {
        this.closeValue = closeValue;
    }
}
