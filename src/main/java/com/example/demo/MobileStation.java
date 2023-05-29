package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class MobileStation {
    @Id
    private UUID id;
    @Column(name = "last_known_x")
    private double lastKnownX;
    @Column(name = "last_known_y")
    private double lastKnownY;
    private double lastKnownAccuracy;
}
