package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.repository.JdbcAssetDao;
import com.example.cryptobank.repository.RootRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
//            asset.setValueUSD(temp.get(0));
//            asset.setValueEUR(temp.get(1));
            asset.setName(coin);
            listOfCryptoAssets.add(asset);
            temp.clear();
        }
        listOfCryptoAssets.clear();
    }

    public void makeRequestPerAsset(JdbcTemplate jdbcTemplate, Asset asset) throws IOException {
        String urlName = encodeValue(asset.getName());

        String jsonName = encodeValue(asset.getName().toLowerCase());

        String url = "https://api.coingecko.com/api/v3/simple/price?ids="+urlName +
                "&vs_currencies=usd&include_24hr_vol=true&include_24hr_change=true";

        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        JSONObject json = null;
        try {
            json = new JSONObject(response.toString());
            System.out.println(json.getJSONObject(jsonName).getDouble("usd"));
            asset.setValueInUsd(json.getJSONObject(jsonName).getDouble("usd"));
            //asset.setAdjustmentFactor(json.getJSONObject(jsonName).getDouble("usd_24h_change"));
            asset.setAdjustmentFactor(1);
            JdbcAssetDao jdbcAssetDao = new JdbcAssetDao(jdbcTemplate);
            jdbcAssetDao.update(asset);
        } catch (JSONException e) {
            System.out.println("Problem coin "+asset.getName());
            System.out.println("Url: "+url);
            e.printStackTrace();
        }


        //Map<String, Map<String, Number>> coinsWithCurrencies = objectMapper.readValue(new URL(url), Map.class);
        //coinsWithCurrencies.get(0).get(0);

        //System.out.println(coinsWithCurrencies);
    }

    private String encodeValue(String strValue) {
        /*
        try {
            return URLEncoder.encode(strValue, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
        */
        return strValue.replace(" ","-");
    }

}
