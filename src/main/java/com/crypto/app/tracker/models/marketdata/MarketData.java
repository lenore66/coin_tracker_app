package com.crypto.app.tracker.models.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class MarketData implements Serializable {
    @JsonProperty("RAW")
    public CoinMarketData coinMarketData;
}
