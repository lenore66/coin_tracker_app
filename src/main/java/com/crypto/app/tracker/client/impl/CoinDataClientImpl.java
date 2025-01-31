package com.crypto.app.tracker.client.impl;

import com.crypto.app.tracker.client.CoinDataClient;
import com.crypto.app.tracker.models.Coin;
import com.crypto.app.tracker.models.CoinData;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static com.crypto.app.tracker.constants.CoinAPIConstants.*;

@Component
public class CoinDataClientImpl implements CoinDataClient {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public CoinData getCoinData(String coinTicker) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (coinTicker == null || coinTicker.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }

        CoinData coinDataOptional = null;
        try {
            coinDataOptional = getCoinDataOptional(coinTicker).orElse(null);
            System.out.println(coinDataOptional);
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinTicker, e);
        }

        return coinDataOptional;
    }

    private String buildUrl(String coinTicker) {
        //TODO Add optional params to query
        //TODO add other currency options
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_HOST).path(COIN_DATA_URL_PATH).queryParam("fsym",coinTicker).queryParam("e", COIN_EXCHANGE)
                .queryParam("tsym","USD").queryParam("api_key", COIN_API_KEY).build().toUriString();
        System.out.println(url);
        return url;

    }
    private Optional<CoinData> getCoinDataOptional(String coinTicker){

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Coin> response =  restTemplate.exchange(buildUrl(coinTicker), HttpMethod.GET, httpEntity, Coin.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Optional.of(response.getBody().coinData);
        } else {
            return Optional.empty();
        }
    }

}
