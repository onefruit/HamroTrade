package com.prabin.hamrotrading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "coins")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coin {

    @Id
    @Column(length = 100)
    private String id;

    private String symbol;
    private String name;

    private String image;

    @Column(precision = 32, scale = 8)
    @JsonProperty("current_price")
    private BigDecimal currentPrice;

    @Column(precision = 32, scale = 0)
    @JsonProperty("market_cap")
    private BigDecimal marketCap;

    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;

    @Column(precision = 32, scale = 0)
    @JsonProperty("fully_diluted_valuation")
    private BigDecimal fullyDilutedValuation;

    @Column(precision = 32, scale = 0)
    @JsonProperty("total_volume")
    private BigDecimal totalVolume;

    @Column(precision = 32, scale = 8)
    @JsonProperty("high_24h")
    private BigDecimal high24h;

    @Column(precision = 32, scale = 8)
    @JsonProperty("low_24h")
    private BigDecimal low24h;

    @Column(precision = 32, scale = 8)
    @JsonProperty("price_change_24h")
    private BigDecimal priceChange24h;

    @Column(precision = 10, scale = 6)
    @JsonProperty("price_change_percentage_24h")
    private BigDecimal priceChangePercentage24h;

    @Column(precision = 32, scale = 0)
    @JsonProperty("market_cap_change_24h")
    private BigDecimal marketCapChange24h;

    @Column(precision = 10, scale = 6)
    @JsonProperty("market_cap_change_percentage_24h")
    private BigDecimal marketCapChangePercentage24h;

    @Column(precision = 32, scale = 8)
    @JsonProperty("circulating_supply")
    private BigDecimal circulatingSupply;

    @Column(precision = 32, scale = 8)
    @JsonProperty("total_supply")
    private BigDecimal totalSupply;

    @Column(precision = 32, scale = 8)
    @JsonProperty("max_supply")
    private BigDecimal maxSupply;

    @Column(precision = 32, scale = 8)
    private BigDecimal ath;

    @Column(precision = 10, scale = 6)
    @JsonProperty("ath_change_percentage")
    private BigDecimal athChangePercentage;

    @JsonProperty("ath_date")
    private Instant athDate;

    @Column(precision = 32, scale = 8)
    private BigDecimal atl;

    @Column(precision = 10, scale = 6)
    @JsonProperty("atl_change_percentage")
    private BigDecimal atlChangePercentage;

    @JsonProperty("atl_date")
    private Instant atlDate;

    // ROI often comes as an object; storing raw JSON string
    @JsonIgnore
    private String roi;

    @JsonProperty("last_updated")
    private Instant lastUpdated;

    @Column(precision = 10, scale = 6)
    @JsonProperty("price_change_percentage_1h_in_currency")
    private BigDecimal priceChangePercentage1hInCurrency;
}
