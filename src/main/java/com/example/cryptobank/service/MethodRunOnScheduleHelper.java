package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.repository.AssetDao;
import com.example.cryptobank.repository.JdbcAssetDao;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MethodRunOnScheduleHelper {
    private CurrencyCollector collector = new CurrencyCollector();
    private AssetService assetService;

    @Autowired
    public MethodRunOnScheduleHelper(AssetService assetService) {
        this.assetService = assetService;
    }

    public void getCurrencyDaily() {

        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            collector.makeRequest();
                            //here comes the method that you want to run on scheduled day/time

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
        List<Asset> assetList = assetService.showAssetList();
        CurrencyHistory currencyHistory = new CurrencyHistory();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                assetList.forEach(asset -> setAssetHelper(asset, currencyHistory));
                //assetList.forEach(asset -> assetService.update(asset));
            }
        }, Date.from(Instant.now()), Duration.ofSeconds(20).toMillis());
        System.out.println(assetList.get(0));
    }

    private void setAssetHelper(Asset asset, CurrencyHistory currencyHistory){
        try {
            asset.setValueYesterday(currencyHistory.historyValuefrom(asset.getName(), currencyHistory.dateYesterday()));
            asset.setValueLastWeek(currencyHistory.historyValuefrom(asset.getName(), currencyHistory.dateLasteWeek()));
            asset.setValueLastMonth(currencyHistory.historyValuefrom(asset.getName(), currencyHistory.dateLastMonth()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
