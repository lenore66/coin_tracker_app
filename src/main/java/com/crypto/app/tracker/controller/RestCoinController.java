package com.crypto.app.tracker.controller;

import com.crypto.app.tracker.models.CoinData;
import com.crypto.app.tracker.service.CoinRestService;
import com.crypto.app.tracker.service.impl.CoinRestServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class RestCoinController {

//TODO search for coin search matches
    //TODO Search by either ticker or coin name to get market info
    //TODO Get the days top Five coins all
    //TODO Get top 5 meme coins
    //TODO Nice have tracker functionality that saves a name of a coin to a database and the user can come back and view the data of those coins

@Autowired
private  CoinRestService coinService;

    @GetMapping("/getCoins/{coinTicker}")
    public List<CoinData> getCoins(@PathVariable String coinTicker){
        ArrayList coinDataList = new ArrayList<>();
        coinDataList.add(coinService.getCoinData(coinTicker));
        System.out.println(coinDataList);
        return coinDataList;
    }
}
