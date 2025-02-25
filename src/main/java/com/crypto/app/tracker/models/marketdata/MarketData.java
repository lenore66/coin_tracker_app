package com.crypto.app.tracker.models.marketdata;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MarketData implements Serializable {


    private Long current_price;
    private Long market_cap;
    private Long market_cap_rank;
    private Long fully_diluted_valuation;
    private Long total_volume;
    private Long low_24h;
    private Long high_24h;
    private Long price_change_24h;
    private Double price_change_percentage_24h;
    private Long circulating_supply;
    private Long total_supply;
}

