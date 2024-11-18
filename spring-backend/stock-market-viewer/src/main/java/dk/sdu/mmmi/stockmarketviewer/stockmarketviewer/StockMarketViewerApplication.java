package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockMarketViewerApplication implements CommandLineRunner {

    @Autowired
    public StockMarketViewerApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(StockMarketViewerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
