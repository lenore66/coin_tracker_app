package com.crypto.app.tracker.client.impl;

import com.crypto.app.tracker.client.CoinMarketDataClient;
import com.crypto.app.tracker.models.marketdata.MarketData;
import com.crypto.app.tracker.models.marketdata.CoinMarketData;

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
public class CoinMarketDataClientImpl implements CoinMarketDataClient {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public CoinMarketData getCoinData(String coinTicker) {
        if (coinTicker == null || coinTicker.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }

        CoinMarketData coinMarketDataOptional = null;
        try {
            coinMarketDataOptional = getCoinDataOptional(coinTicker).orElse(null);
            System.out.println(coinMarketDataOptional);
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinTicker, e);
        }

        return coinMarketDataOptional;
    }

    private String buildUrl(String coinTicker) {
        //TODO Add optional params to query
        //TODO add other currency options
        //TODO add exchange options
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_COMPARE_HOST).path(COIN_DATA_URL_PATH).queryParam("fsym",coinTicker).queryParam("e", COIN_EXCHANGE)
                .queryParam("tsym","USD").queryParam("api_key", COIN_COMPARE_API_KEY).build().toUriString();
        System.out.println(url);
        return url;

    }
    private Optional<CoinMarketData> getCoinDataOptional(String coinTicker){

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<MarketData> response =  restTemplate.exchange(buildUrl(coinTicker), HttpMethod.GET, httpEntity, MarketData.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Optional.of(response.getBody().coinMarketData);
        } else {
            return Optional.empty();
        }
    }

}
