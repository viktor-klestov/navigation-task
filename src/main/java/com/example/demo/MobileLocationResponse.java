package com.example.demo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class MobileLocationResponse {
    protected final UUID mobileId;
}
