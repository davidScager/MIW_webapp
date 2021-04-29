package com.example.cryptobank.service;

import com.example.cryptobank.model.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class CurrencyCollector {

    private ArrayList<Asset> listOfCryptoAssets = new ArrayList<>();
    private ObjectMapper objectMapper;
    private final static String ADDRESS =
            "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin%2Caitra%2Cairwaves&vs_currencies=usd%2Ceur";

    public void makeRequest() throws IOException {
        ArrayList <Number> temp = new ArrayList<>();
        objectMapper = new ObjectMapper();

        Map<String, Map<String,Number>> coinsWithCurrencies = objectMapper.readValue(new URL(ADDRESS), Map.class);

        for ( String coin : coinsWithCurrencies.keySet() ) {
            for ( Map.Entry<String, Number> currencyWithValue : coinsWithCurrencies.get(coin).entrySet() ) {
                temp.add(currencyWithValue.getValue());
            }
            Asset asset = new Asset();
            asset.setValueUSD(temp.get(0));
            asset.setValueEUR(temp.get(1));
            asset.setName(coin);
            listOfCryptoAssets.add(asset);
            temp.clear();
        }
        listOfCryptoAssets.clear();
    }
}
