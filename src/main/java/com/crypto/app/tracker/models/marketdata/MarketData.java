package com.crypto.app.tracker.models.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarketData implements Serializable {
    @JsonProperty("RAW")
    private CoinMarketData coinMarketData;
}
