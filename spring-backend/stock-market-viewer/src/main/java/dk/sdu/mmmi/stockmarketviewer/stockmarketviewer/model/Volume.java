package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "volume")
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double volume;

    @Column(name = "volume_notional")
    private Double volumeNotional;

    @Column(name = "trades_done")
    private Double tradesDone;

    public Long getId() {
        return id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getVolumeNotional() {
        return volumeNotional;
    }

    public void setVolumeNotional(Double volumeNotional) {
        this.volumeNotional = volumeNotional;
    }

    public Double getTradesDone() {
        return tradesDone;
    }

    public void setTradesDone(Double tradesDone) {
        this.tradesDone = tradesDone;
    }
}
