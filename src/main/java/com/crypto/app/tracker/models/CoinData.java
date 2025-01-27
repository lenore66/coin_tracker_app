package com.crypto.app.tracker.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class CoinData {
    @JsonProperty("usd")
    @JsonAlias("usd")
    private Double usd;
    @JsonProperty("usd_market_cap")
    @JsonAlias("usd_market_cap")
    private Double usdMarketCap;
    @JsonProperty("usd_24h_vol")
    @JsonAlias("usd_24h_vol")
    private Double usd24hVolume;
    @JsonProperty("usd_24h_change")
    @JsonAlias("usd_24h_change")
    private Double usd24hChange;
    @JsonProperty("last_updated_at")
    @JsonAlias("last_updated_at")
    private  Double lastUpdated;
        }