package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MobileLocationService {
    private final BaseStationRepository baseStationRepository;
    private final MobileStationRepository mobileStationRepository;

    @Transactional
    public void acceptNotification(BaseStationNotification notification) {
        baseStationRepository.findById(notification.getBaseStationId())
                .ifPresent(base -> {
                    for (BaseStationNotification.MobileLocationReport report : notification.getReports()) {
                        Optional<MobileStation> existed = mobileStationRepository.findById(report.getMobileStationId());
                        MobileStation mobileStation = existed.orElseGet(() -> newMobileStation(report.getMobileStationId()));
                        mobileStation.setLastKnownX(base.getX());
                        mobileStation.setLastKnownY(base.getY());
                        mobileStation.setLastKnownAccuracy(report.getDistance());
                    }
                });
    }

    private MobileStation newMobileStation(UUID id) {
        MobileStation created = new MobileStation();
        created.setId(id);
        return mobileStationRepository.save(created);
    }

    public MobileLocationResponse findLocation(UUID mobileStationId) {
        return mobileStationRepository.findById(mobileStationId)
                .map(found -> (MobileLocationResponse) new SuccessfulMobileLocationResponse(found))
                .orElse(ErrorMobileLocationResponse.notFound(mobileStationId));
    }
}
