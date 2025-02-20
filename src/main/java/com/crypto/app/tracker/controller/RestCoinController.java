package com.crypto.app.tracker.controller;

import com.crypto.app.tracker.models.CoinMetaMarketData;
import com.crypto.app.tracker.service.CoinRestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class RestCoinController {

//TODO search for coin search matches
    //TODO Search by either ticker or coin name to get market info
    //TODO GET Daily data for coin
    //TODO Get ticker if you ticker is not provided
    //TODO Nice have tracker functionality that saves a name of a coin to a database and the user can come back and view the data of those coins

@Autowired
private  CoinRestService coinService;

    @GetMapping("/getCoinByNameData/{coinName}/{fiatCurrency}")
    public CoinMetaMarketData getCoins(@PathVariable String coinName, @PathVariable String fiatCurrency){
        CoinMetaMarketData  coinDataList = coinService.getCoinDataFromCoinName(coinName, fiatCurrency);
        System.out.println(coinDataList);
        return coinDataList;
    }
    @GetMapping("/getCoinByTickerData/{coinTicker}/{fiatCurrency}")
    public CoinMetaMarketData getCoinsByTicker(@PathVariable String coinTicker, @PathVariable String fiatCurrency){

        CoinMetaMarketData coinDataList =  coinService.getCoinsByTicker(coinTicker, fiatCurrency);
        System.out.println(coinDataList);
        return coinDataList;
    }
}
