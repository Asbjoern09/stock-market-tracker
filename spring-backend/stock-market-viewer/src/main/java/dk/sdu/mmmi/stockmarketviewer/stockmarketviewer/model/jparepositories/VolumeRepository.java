package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VolumeRepository extends JpaRepository<Volume, Long> {

    @Query("SELECT v FROM Volume v JOIN ExchangeInfo ei ON ei.volume = v WHERE ei.tradeTime >= :lastWeek ORDER BY ei.tradeTime ASC")
    List<Volume> findAllFromLastWeek(@Param("lastWeek") Date lastWeek);

    @Query("SELECT v FROM Volume v JOIN ExchangeInfo ei ON ei.volume = v WHERE ei.tradeTime >= :lastMonth ORDER BY ei.tradeTime ASC")
    List<Volume> findAllFromLastMonth(@Param("lastMonth") Date lastMonth);

    @Query("SELECT v FROM Volume v JOIN ExchangeInfo ei ON ei.volume = v WHERE ei.tradeTime >= :lastDay ORDER BY ei.tradeTime ASC")
    List<Volume> findAllFromLastDay(@Param("lastDay") Date lastDay);
}
