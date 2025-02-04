package com.crypto.app.tracker.client.impl;

import com.crypto.app.tracker.client.CoinMetaDataClient;
import com.crypto.app.tracker.models.marketdata.MarketData;
import com.crypto.app.tracker.models.metadata.CoinMetaData;
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
public class CoinMetaDataClientImpl implements CoinMetaDataClient {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public CoinMetaData getCoinMetaData(String coinName) {
        if (coinName == null || coinName.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }

        CoinMetaData coinMarketDataOptional = null;
        try {
            coinMarketDataOptional = getCoinDataOptional(coinName).orElse(null);
            System.out.println(coinMarketDataOptional);
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinName, e);
        }

        return coinMarketDataOptional;
    }

    private Optional<CoinMetaData> getCoinDataOptional(String coinName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.CONTENT_TYPE,X_CMC_PRO_API_KEY);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        if (coinName == null || coinName.isEmpty()) {
            throw new IllegalArgumentException("Coin name must not be null or empty");
        }

        Optional<CoinMetaData> coinMetaDataOptional = null;

        try {
            ResponseEntity<CoinMetaData> response =  restTemplate.exchange(buildUrl(coinName), HttpMethod.GET, httpEntity, CoinMetaData.class);
            coinMetaDataOptional = Optional.of(response.getBody());
            return coinMetaDataOptional;
        } catch (Exception e) {
            // Log the error and handle it appropriately
            throw new RuntimeException("Failed to fetch coin data for " + coinName, e);
        }

}


    private String buildUrl(String coinName) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(COIN_SCHEME).host(COIN_MARKET_CAP_HOST).path(COIN_METATDATA_URL_PATH).queryParam("slug", coinName.toLowerCase()).build().toUriString();
        System.out.println(url);
        return url;

    }
}
