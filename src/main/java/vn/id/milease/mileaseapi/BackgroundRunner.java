package vn.id.milease.mileaseapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.service.PlaceService;
import vn.id.milease.mileaseapi.service.TravelerService;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component
public class BackgroundRunner {
    private final PlaceService placeService;
    private final TravelerService travelerService;

    @Scheduled(cron = "@daily", zone = "Asia/Ho_Chi_Minh")
    public void updateDisplayIndexScheduleTask() {
        placeService.updateDisplayIndex()
                .thenApply(r -> r)
                .join();
    }

    @Scheduled(cron = "@daily", zone = "Asia/Ho_Chi_Minh")
    public void updateTravelerStatus() {
        travelerService.updateTravelerStatus(null).thenApply(t -> t).join();
    }
}
