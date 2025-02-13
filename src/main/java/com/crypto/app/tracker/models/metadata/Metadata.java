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
public class Metadata {
    @JsonProperty("name")
    public String name;
    @JsonProperty("symbol")
    public String symbol;
    @JsonProperty("logo")
    public String logo;
    @JsonProperty("description")
    public String description;

}
