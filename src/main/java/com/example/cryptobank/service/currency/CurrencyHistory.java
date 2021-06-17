package com.example.cryptobank.service.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Service
public class CurrencyHistory {
        private final Logger logger = LoggerFactory.getLogger(CurrencyHistory.class);
        private final String[] tolowwerCase = {"Bitcoin", "Cardano", "Dash", "Dogecoin", "Eos", "Ethereum","Filecoin",
        "Litecoin", "Monero", "Neo", "Polkadot", "Stellar", "Tron", "Vechain"};

        private ObjectMapper objectMapper;
        private String adress = "https://api.coingecko.com/api/v3/coins/";
        private String finishadress = "/history?date=";

        public CurrencyHistory() {
                super();
                logger.info("New Currencyhistory");
        }

        public Double historyValuefrom(String date, String cryptocoin) throws IOException{
                if (cryptocoin.equals("Dollar")) return 1.0;
                objectMapper = new ObjectMapper();
                String url = adress + getStringForApi(cryptocoin) + finishadress + date;
                Map<String, Object> coinwithcurrency = objectMapper.readValue(new URL(url), Map.class);//jackson object mapper
                String stringtoSplit = String.valueOf(coinwithcurrency.get("market_data"));//dit moet map zijn
                /*Map<String, Object> marketData = coinwithcurrency.get("market_data");*/
                String[] strings = stringtoSplit.split(",");
                for (int teller = 0; teller < strings.length; teller++) {
                        String[] stringssplit = strings[teller].split("=");
                        if (stringssplit[0].equals(" usd")){
                                double result = Double.valueOf(stringssplit[1]);
                                if (result <1){
                                        BigDecimal round = BigDecimal.valueOf(result).setScale(6, RoundingMode.HALF_UP);
                                        return round.doubleValue();
                                } else {
                                        BigDecimal round = BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_UP);
                                        return round.doubleValue();
                                }

                        }
                }
                return 0.0;
        }

        private String getStringForApi(String cryptocoin){
                if (cryptocoin.equals("Bitcoin") || cryptocoin.equals("Cardano") || cryptocoin.equals("Dash") ||
                cryptocoin.equals("Dogecoin") || cryptocoin.equals("Eos") || cryptocoin.equals("Ethereum") || cryptocoin.equals("Filecoin") ||
                cryptocoin.equals("Litecoin") || cryptocoin.equals("Monero") || cryptocoin.equals("Neo") || cryptocoin.equals("Polkadot") || cryptocoin.equals("Stellar") ||
                cryptocoin.equals("Tron") || cryptocoin.equals("Vechain")) {
                        String teruggave = cryptocoin.toLowerCase(Locale.ROOT);
                        return teruggave;
                }//maar hier een array en zet dit om naar lowercase
                else if (cryptocoin.equals("Bitcoin Cash") || cryptocoin.equals("Ethereum Classic") || cryptocoin.equals("Internet Computer"))
                        return cryptocoin.toLowerCase(Locale.ROOT).replace(" ", "-");
                else if (cryptocoin.equals("XRP")) return "xrp-classic";
                else if (cryptocoin.equals("Theta")) return "theta-token";
                else if (cryptocoin.equals("Binance Coin")) return "binancecoin";
                else return "dollars";
        }

        public String dateYesterday(){
               return LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        public String dateLasteWeek(){
                return LocalDate.now().minusWeeks(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        }

        public String dateLastMonth(){
                return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
}
