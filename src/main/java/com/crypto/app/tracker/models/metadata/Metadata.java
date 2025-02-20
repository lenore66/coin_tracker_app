package com.crypto.app.tracker.models.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Metadata {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("description")
    private Description description;
    @JsonProperty("ticker")
    private List<Ticker> tickers;

}
