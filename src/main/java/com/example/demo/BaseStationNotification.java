package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BaseStationNotification {
    @JsonProperty("base_station_id")
    private UUID baseStationId;
    private List<MobileLocationReport> reports;

    @Getter
    @Setter
    public static class MobileLocationReport {
        @JsonProperty("mobile_station_id")
        private UUID mobileStationId;
        private double distance;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp;
    }
}
