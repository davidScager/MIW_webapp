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
                logger.info("Nieuwe opvraag");
                if (cryptocoin.equals("Dollar")) return 1.0;
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
                return 0.0;
        }

        private String getStringForApi(String cryptocoin){
                if (cryptocoin.equals("Bitcoin") || cryptocoin.equals("Cardano") || cryptocoin.equals("DASH") ||
                cryptocoin.equals("Dogecoin") || cryptocoin.equals("EOS") || cryptocoin.equals("Ethereum") || cryptocoin.equals("Filecoin") ||
                cryptocoin.equals("Litecoin") || cryptocoin.equals("Monero") || cryptocoin.equals("Neo") || cryptocoin.equals("Polkadot") || cryptocoin.equals("Stellar") ||
                cryptocoin.equals("TRON") || cryptocoin.equals("VeChain")) {
                        String teruggave = cryptocoin.toLowerCase(Locale.ROOT);
                        return teruggave;
                }
                else if (cryptocoin.equals("Bitcoin Cash") || cryptocoin.equals("Ethereum Classic") || cryptocoin.equals("Internet Computer"))
                        return cryptocoin.toLowerCase(Locale.ROOT).replace(" ", "-");
                else if (cryptocoin.equals("XRP")) return "xrp-classic";
                else if (cryptocoin.equals("THETA")) return "theta-token";
                else if (cryptocoin.equals("Binance Coin")) return "binancecoin";
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
