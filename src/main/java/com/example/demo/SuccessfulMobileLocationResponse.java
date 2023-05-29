package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SuccessfulMobileLocationResponse extends MobileLocationResponse {
    private final double x;
    private final double y;
    @JsonProperty("error_radius")
    private final double errorRadius;

    public SuccessfulMobileLocationResponse(UUID mobileId, double x, double y, double errorRadius) {
        super(mobileId);
        this.x = x;
        this.y = y;
        this.errorRadius = errorRadius;
    }

    public SuccessfulMobileLocationResponse(MobileStation mobile) {
        this(mobile.getId(), mobile.getLastKnownX(), mobile.getLastKnownY(), mobile.getLastKnownAccuracy());
    }
}
