package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TradeValueRepository extends JpaRepository<TradeValue, Long> {

    @Query("SELECT tv FROM TradeValue tv JOIN ExchangeInfo ei ON ei.tradeValue = tv WHERE ei.tradeTime >= :lastWeek ORDER BY ei.tradeTime ASC")
    List<TradeValue> findAllFromLastWeek(@Param("lastWeek") Date lastWeek);

    @Query("SELECT tv FROM TradeValue tv JOIN ExchangeInfo ei ON ei.tradeValue = tv WHERE ei.tradeTime >= :lastMonth ORDER BY ei.tradeTime ASC")
    List<TradeValue> findAllFromLastMonth(@Param("lastMonth") Date lastMonth);

    @Query("SELECT tv FROM TradeValue tv JOIN ExchangeInfo ei ON ei.tradeValue = tv WHERE ei.tradeTime >= :lastDay ORDER BY ei.tradeTime ASC")
    List<TradeValue> findAllFromLastDay(@Param("lastDay") Date lastDay);
}
