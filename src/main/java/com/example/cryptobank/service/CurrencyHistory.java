package com.example.cryptobank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

@Service
public class CurrencyHistory {
        private final Logger logger = LoggerFactory.getLogger(CurrencyHistory.class);

        private ObjectMapper objectMapper;
        private String adress = "https://api.coingecko.com/api/v3/coins/";
        private String finishadress = "/history?date=";

        public CurrencyHistory() {
                super();
                logger.info("New Currencyhistory");
        }

        public Double historyValuefrom(String date, String cryptocoin) throws IOException{
                objectMapper = new ObjectMapper();
                String url = adress + getStringForApi(cryptocoin) + finishadress + date;
                Map<String, Object> coinwithcurrency = objectMapper.readValue(new URL(url), Map.class);
                String stringtoSplit = String.valueOf(coinwithcurrency.get("market_data"));//dit moet map zijn
                /*Map<String, Object> marketData = coinwithcurrency.get("market_data");*/
                String[] strings = stringtoSplit.split(",");
                for (int teller = 0; teller < strings.length; teller++) {
                        String[] stringssplit = strings[teller].split("=");
                        if (stringssplit[0].equals(" usd")){
                                return Double.valueOf(stringssplit[1]);
                        }
                }
                return null;
        }

        private String getStringForApi(String crytocoin){
                if (crytocoin.equals("Bitcoin") || crytocoin.equals("Binance Coin") || crytocoin.equals("Cardano") || crytocoin.equals("DASH") ||
                crytocoin.equals("Dogecoin") || crytocoin.equals("EOS") || crytocoin.equals("Ethereum") || crytocoin.equals("Filecoin") ||
                crytocoin.equals("Litecoin") || crytocoin.equals("Monero") || crytocoin.equals("Neo") || crytocoin.equals("Polkadot") || crytocoin.equals("Stellar") ||
                crytocoin.equals("TRON") || crytocoin.equals("Vechain")) return crytocoin.toLowerCase(Locale.ROOT);
                else if (crytocoin.equals("Bitcoin Cash") || crytocoin.equals("Ethereum Classic") || crytocoin.equals("Internet Computer"))
                        return crytocoin.toLowerCase(Locale.ROOT).replace(" ", "-");
                else if (crytocoin.equals("XRP")) return "erp-classic";
                else if (crytocoin.equals("THETA")) return "theta-token";
                else return "dollars";
        }

        public String dateYesterday(){
                String dateUsa = String.valueOf(LocalDate.now().minusDays(1));
                String[] strings = dateUsa.split("-");
                String dateAPI = strings[2] + "-" + strings[1] + "-" + strings[0];
                return dateAPI;
        }

        public String dateLasteWeek(){
                String dateUsa = String.valueOf(LocalDate.now().minusWeeks(1));
                String[] strings = dateUsa.split("-");
                String dateAPI = strings[2] + "-" + strings[1] + "-" + strings[0];
                return dateAPI;
        }

        public String dateLastMonth(){
                String dateUsa = String.valueOf(LocalDate.now().minusMonths(1));
                String[] strings = dateUsa.split("-");
                String dateAPI = strings[2] + "-" + strings[1] + "-" + strings[0];
                return dateAPI;
        }
}
