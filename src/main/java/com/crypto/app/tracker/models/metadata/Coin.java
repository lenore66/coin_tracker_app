package com.crypto.app.tracker.models.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Coin {
    @JsonProperty("id")
    public String coinId;
    @JsonProperty("name")
    public String coinName;
    @JsonProperty("symbol")
    public String symbol;
}
