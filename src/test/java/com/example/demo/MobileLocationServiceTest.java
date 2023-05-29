package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MobileLocationServiceTest {
    private MobileLocationService serviceUnderTest;
    @Mock
    private BaseStationRepository mockBaseStationRepository;
    @Mock
    private MobileStationRepository mockMobileStationRepository;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new MobileLocationService(mockBaseStationRepository, mockMobileStationRepository);
    }

    @Test
    void acceptNotification_shouldUpdateMobileStationWithCorrectData() {
        UUID baseStationId = UUID.fromString("0f79a374-42e1-41a3-830a-fd88343ae8d6");
        UUID mobileStationId = UUID.fromString("28661547-4333-4ac2-ae21-b43fa52fcfa0");
        double distance = 10.5;
        BaseStationNotification notification = new BaseStationNotification();
        notification.setBaseStationId(baseStationId);
        BaseStationNotification.MobileLocationReport report = new BaseStationNotification.MobileLocationReport();
        report.setMobileStationId(mobileStationId);
        report.setDistance(distance);
        notification.setReports(List.of(report));
        BaseStation baseStation = new BaseStation();
        baseStation.setX(100);
        baseStation.setY(200);
        MobileStation mobileStation = new MobileStation();
        when(mockBaseStationRepository.findById(baseStationId)).thenReturn(Optional.of(baseStation));
        when(mockMobileStationRepository.findById(mobileStationId)).thenReturn(Optional.of(mobileStation));

        serviceUnderTest.acceptNotification(notification);

        assertEquals(baseStation.getX(), mobileStation.getLastKnownX());
        assertEquals(baseStation.getY(), mobileStation.getLastKnownY());
        assertEquals(distance, mobileStation.getLastKnownAccuracy());
    }

    @Test
    void acceptNotification_shouldCreateNewMobileStationIfNotFound() {
        UUID baseStationId = UUID.fromString("8df5ef3e-df36-4903-8251-aaa1f56a2a9f");
        UUID mobileStationId = UUID.fromString("718a9b0c-d308-4179-9a01-c5a895de9392");
        double distance = 10.5;
        BaseStationNotification notification = new BaseStationNotification();
        notification.setBaseStationId(baseStationId);
        BaseStationNotification.MobileLocationReport report = new BaseStationNotification.MobileLocationReport();
        report.setMobileStationId(mobileStationId);
        report.setDistance(distance);
        notification.setReports(List.of(report));
        BaseStation baseStation = new BaseStation();
        baseStation.setX(100);
        baseStation.setY(200);
        when(mockBaseStationRepository.findById(baseStationId)).thenReturn(Optional.of(baseStation));
        when(mockMobileStationRepository.findById(mobileStationId)).thenReturn(Optional.empty());
        when(mockMobileStationRepository.save(any(MobileStation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        serviceUnderTest.acceptNotification(notification);

        verify(mockMobileStationRepository).save(any(MobileStation.class));
    }


    @Test
    void findLocation_shouldReturnSuccessfulResponseIfMobileStationFound() {
        UUID mobileStationId = UUID.fromString("8df5ef3e-df36-4903-8251-aaa1f56a2a9f");
        MobileStation mobileStation = new MobileStation();
        mobileStation.setId(mobileStationId);
        when(mockMobileStationRepository.findById(mobileStationId)).thenReturn(Optional.of(mobileStation));

        MobileLocationResponse response = serviceUnderTest.findLocation(mobileStationId);

        assertThat(response.getMobileId()).isEqualTo(mobileStationId);
    }

    @Test
    void findLocation_shouldReturnErrorResponseIfMobileStationNotFound() {
        UUID mobileStationId = UUID.fromString("8df5ef3e-df36-4903-8251-aaa1f56a2a9f");
        when(mockMobileStationRepository.findById(mobileStationId)).thenReturn(Optional.empty());

        MobileLocationResponse response = serviceUnderTest.findLocation(mobileStationId);

        assertThat(response).isEqualTo(ErrorMobileLocationResponse.notFound(mobileStationId));
    }
}