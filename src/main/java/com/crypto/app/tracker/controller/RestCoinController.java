package com.crypto.app.tracker.controller;

import com.crypto.app.tracker.models.CoinData;
import com.crypto.app.tracker.service.CoinRestService;
import com.crypto.app.tracker.service.impl.CoinRestServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class RestCoinController {

//TODO in the controller Initially I should have an endpoint to get data on a coin
//TODO search for coin search matches
    //TODO Get the days top Five coins all
    //TODO Get top 5 meme coins
    //TODO get top

@Autowired
private  CoinRestService coinService;

    @GetMapping("/getCoins")
    public List<CoinData> getCoins(){
        ArrayList coinDataList = new ArrayList<>();
        coinDataList.add(coinService.getCoinData());
        return coinDataList;
    }
}
