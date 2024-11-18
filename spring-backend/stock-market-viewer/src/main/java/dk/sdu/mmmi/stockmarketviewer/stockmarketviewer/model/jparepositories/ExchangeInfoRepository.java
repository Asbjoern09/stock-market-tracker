package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.jparepositories;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExchangeInfoRepository extends JpaRepository<ExchangeInfo, Long> {

    @Query("SELECT ei FROM ExchangeInfo ei WHERE ei.tradeTime >= :lastWeek ORDER BY ei.tradeTime ASC")
    List<ExchangeInfo> findAllFromLastWeek(@Param("lastWeek") Date lastWeek);

    @Query("SELECT ei FROM ExchangeInfo ei WHERE ei.tradeTime >= :lastMonth ORDER BY ei.tradeTime ASC")
    List<ExchangeInfo> findAllFromLastMonth(@Param("lastMonth") Date lastMonth);

    @Query("SELECT ei FROM ExchangeInfo ei WHERE ei.tradeTime >= :lastDay ORDER BY ei.tradeTime ASC")
    List<ExchangeInfo> findAllFromLastDay(@Param("lastDay") Date lastDay);
}
