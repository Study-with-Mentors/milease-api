package vn.id.milease.mileaseapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.service.PlaceService;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component
public class BackgroundRunner {
    private final PlaceService placeService;

    @Scheduled(cron = "@daily", zone = "Asia/Ho_Chi_Minh")
    public void updateDisplayIndexScheduleTask() {
        log.info("Test Scheduler");
        placeService.updateDisplayIndex()
                .thenApply(r -> r)
                .join();
    }
}
