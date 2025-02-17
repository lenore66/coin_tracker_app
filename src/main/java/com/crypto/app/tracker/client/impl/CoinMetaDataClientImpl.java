package com.crypto.app.tracker.client.impl;

import com.crypto.app.tracker.client.CoinMetaDataClient;

import com.crypto.app.tracker.models.metadata.Coin;
import com.crypto.app.tracker.models.metadata.CoinList;
import com.crypto.app.tracker.models.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static com.crypto.app.tracker.constants.CoinMarketApiConstants.COIN_SCHEME;
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.*;

@Component
public class CoinMetaDataClientImpl implements CoinMetaDataClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Metadata getCoinMetaData(String coinName) {
        if (coinName == null || coinName.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }

        Metadata coinMarketDataOptional = null;
        try {
            coinMarketDataOptional = getCoinDataOptional(coinName).orElse(null);
            System.out.println(coinMarketDataOptional);
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinName, e);
        }

        return coinMarketDataOptional;
    }

    private Optional<Metadata> getCoinDataOptional(String coinName) {
        if (coinName == null || coinName.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }
        Optional<Metadata> coinMetaDataOptional = null;

        try {
            if (coinName.contains("$")) {
                
                
                String id = validateCoinWithTicker(coinName);
                ResponseEntity<Metadata> response = restTemplate.exchange(buildUrl(id), HttpMethod.GET, getEntity(), Metadata.class);
                coinMetaDataOptional = Optional.ofNullable(response.getBody());
            } else {
                String id = validateCoinWitName(coinName);
                ResponseEntity<Metadata> response = restTemplate.exchange(buildUrl(id), HttpMethod.GET, getEntity(), Metadata.class);
                coinMetaDataOptional = Optional.ofNullable(response.getBody());
            }
            return coinMetaDataOptional;
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinName, e);
        }

    }

    private String buildUrl(String coinName) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_METADATA).queryParam("id", coinName.toLowerCase().strip()).build().toUriString();
        System.out.println(url);
        return url;
    }
    private String validateCoinWitName(String coinName) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_LIST).build().toUriString();
        ResponseEntity<CoinList> response = restTemplate.exchange(url, HttpMethod.GET, getEntity(), CoinList.class);

        Coin validCoin = response.getBody().getCoins().stream().filter(coin -> coin.getCoinName().contains(coinName)).findAny().get();
        return validCoin.getCoinId();

    }

    private String validateCoinWithTicker(String coinName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_LIST).build().toUriString();
        ResponseEntity<CoinList> response = restTemplate.exchange(url, HttpMethod.GET, getEntity(), CoinList.class);

        Coin validCoin = response.getBody().getCoins().stream().filter(coin -> coin.getCoinId().contains(coinName)).findAny().get();
        return validCoin.getCoinId();

    }

    private  HttpEntity getEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        return new HttpEntity<>(headers);
    }

}
