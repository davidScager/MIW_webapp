package com.example.cryptobank.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MethodRunOnScheduleHelper {
    private CurrencyCollector collector = new CurrencyCollector();

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
}
