package com.crypto.app.tracker.client.impl;

import com.crypto.app.tracker.client.CoinMetaDataClient;

import com.crypto.app.tracker.models.metadata.Coin;
import com.crypto.app.tracker.models.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
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
            System.out.println(coinName);;

                ResponseEntity<Metadata> response = restTemplate.exchange(buildUrl(validateCoinWithTicker(coinName.toLowerCase())), HttpMethod.GET, getEntity(), Metadata.class);
                coinMetaDataOptional = Optional.ofNullable(response.getBody());
            } else {
                ResponseEntity<Metadata> response = restTemplate.exchange(buildUrl(validateCoinWitName(coinName.toLowerCase())), HttpMethod.GET, getEntity(), Metadata.class);
                coinMetaDataOptional = Optional.ofNullable(response.getBody());
            }
            return coinMetaDataOptional;
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinName, e);
        }

    }


    private String validateCoinWitName(String coinName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME)
                .host(COIN_GECKO_HOST)
                .path(COIN_GECKO_PATH_LIST)
                .build()
                .toUriString();

        ResponseEntity<List<Coin>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Coin>>() {});

        System.out.println(response.getBody());
        if (response.getBody() == null) {
            throw new RuntimeException("No coins found in the response.");
        }
        List<Coin> coinList = response.getBody().stream().toList();

        Optional<Coin> matchingCoin = response.getBody().stream()
                .filter(coin -> coin != null && coin.getCoinName() != null && coin.getCoinName().toLowerCase().equals(coinName.toLowerCase()))
                .findAny();


        return matchingCoin
                .map(Coin::getCoinName)
                .orElseThrow(() -> new RuntimeException("No coin found with ticker: " + coinName));
    }
    private String validateCoinWithTicker(String coinName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME)
                .host(COIN_GECKO_HOST)
                .path(COIN_GECKO_PATH_LIST)
                .build()
                .toUriString();

        ResponseEntity<List<Coin>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Coin>>() {});
        if (response.getBody() == null) {
            throw new RuntimeException("No coins found in the response.");
        }
        String sanitizedCoinName = coinName.replace("$", "").toLowerCase();

        Optional<Coin> matchingCoin = response.getBody().stream()
                .filter(coin -> coin != null && coin.getCoinId() != null && coin.getSymbol().equals(sanitizedCoinName))
                .findAny();

        return matchingCoin
                .map(Coin::getCoinId)
                .orElseThrow(() -> new RuntimeException("No coin found with ticker: " + coinName));

    }

    private String buildUrl(String coinName) {

        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH_METADATA.concat("/").concat(coinName.toLowerCase())).build().toUriString();
        System.out.println(url);
        return url;
    }

    private  HttpEntity getEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        return new HttpEntity<>(headers);
    }

}
