package com.crypto.app.tracker.client.impl;

import com.crypto.app.tracker.client.CoinMarketDataClient;
import com.crypto.app.tracker.models.marketdata.MarketData;

import com.crypto.app.tracker.models.metadata.Coin;
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

import static com.crypto.app.tracker.constants.CoinMarketApiConstants.*;
import static com.crypto.app.tracker.constants.CoinMetaDataApiConstants.COIN_GECKO_KEY;

@Component
public class CoinMarketDataClientImpl implements CoinMarketDataClient {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public MarketData getCoinData(String coinTicker, String toCurrency) {
        if (coinTicker == null || coinTicker.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }

        MarketData coinMarketDataOptional = null;
        try {
            coinMarketDataOptional = Objects.requireNonNull(getCoinDataOptional(coinTicker, toCurrency).orElse(null)).getFirst();
            System.out.println(coinMarketDataOptional);
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinTicker, e);
        }

        return coinMarketDataOptional;
    }

    private String buildUrl(String coinTicker, String toCurrency) {
        //TODO Add optional params to query
        if(coinTicker.contains("$")){
            coinTicker = coinTicker.replace("$","");
        }
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_GECKO_HOST).path(COIN_GECKO_PATH).queryParam("ids",coinTicker)
                .queryParam("vs_currency",toCurrency).build().toUriString();
        System.out.println(url);
        return url;

    }
    private Optional<List<MarketData>> getCoinDataOptional(String coinTicker, String fiatCurrency){

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("x-cg-demo-api-key", COIN_GECKO_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<MarketData>> response;
        response = restTemplate.exchange(buildUrl(coinTicker, fiatCurrency), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<MarketData>>() {});
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }

}
