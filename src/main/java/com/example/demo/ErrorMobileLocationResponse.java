package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ErrorMobileLocationResponse extends MobileLocationResponse {
    @JsonProperty("error_code")
    private final int errorCode;
    @JsonProperty("error_description")
    private final String errorDescription;

    public ErrorMobileLocationResponse(UUID mobileId, int errorCode, String errorDescription) {
        super(mobileId);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public static ErrorMobileLocationResponse notFound(UUID mobileId) {
        return new ErrorMobileLocationResponse(mobileId, 404, "mobile not found");
    }
}
