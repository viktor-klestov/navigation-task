package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class BaseStation {
    @Id
    private UUID id;
    private String name;
    private double x;
    private double y;
    private double detectionRadiusInMeters;
}
