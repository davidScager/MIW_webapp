package com.example.cryptobank.service.currency;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import com.example.cryptobank.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class MethodRunOnScheduleHelper {
    private CurrencyCollector collector = new CurrencyCollector();
    private AssetService assetService;
    private final RootRepository rootRepository;
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public MethodRunOnScheduleHelper(AssetService assetService, RootRepository rootRepository, JdbcTemplate jdbcTemplate) {
        this.assetService = assetService;
        this.rootRepository = rootRepository;
        this.jdbcTemplate = jdbcTemplate;
        logger.info("ubvievb iwv e");
    }
    private final Logger logger = LoggerFactory.getLogger(MethodRunOnScheduleHelper.class);

    public void getCurrencyDaily() {
        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Asset btc = rootRepository.getAsset("BTC");
                            Asset eth = rootRepository.getAsset("ETH");
                            collector.makeRequestPerAsset(jdbcTemplate, btc);
                            collector.makeRequestPerAsset(jdbcTemplate, eth);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Date.from(Instant.now()),
                Duration.ofSeconds(20).toMillis() //The timer. You can also choose onHours, onDays etc.
        );
    }


    public void getCurrencyDailyForHistoryValue() {
        System.out.println("Asset history is updating");
        List<Asset> assetList = assetService.showAssetList();
        System.out.println(assetList);
        CurrencyHistory currencyHistory = new CurrencyHistory();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                assetList.forEach(asset -> setAssetHelper(asset, currencyHistory));
                assetList.forEach(asset -> assetService.update(asset));
            }
        }, Date.from(Instant.now()), Duration.ofDays(1).toMillis());
    }

    private void setAssetHelper(Asset asset, CurrencyHistory currencyHistory){
        try {
            asset.setValueYesterday(currencyHistory.historyValuefrom(currencyHistory.dateYesterday(),asset.getName()));
            asset.setValueLastWeek(currencyHistory.historyValuefrom(currencyHistory.dateLasteWeek(),asset.getName()));
            asset.setValueLastMonth(currencyHistory.historyValuefrom(currencyHistory.dateLastMonth(), asset.getName()));
            logger.info(asset.getName() + " history has been updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
