package vn.id.milease.mileaseapi;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.id.milease.mileaseapi.service.PlaceService;

import java.time.*;

@Component
@Slf4j
@AllArgsConstructor
public class BackgroundRunner implements CommandLineRunner {
    private final PlaceService placeService;

    @Override
    public void run(String... args) throws Exception {
        do {
            //New day
            var targetTimeToUpdate = LocalTime.of(0, 0,0);
            var now = LocalTime.now((ZoneId.of("Asia/Ho_Chi_Minh")));
            long durationInSecond = Math.abs(Duration.between(targetTimeToUpdate, now).getSeconds());
            long secondSpan;
            if(targetTimeToUpdate.isBefore(now))
                secondSpan = 24 * 60 * 60 - durationInSecond;
            else
                secondSpan = durationInSecond;

            long numberOfSecond = secondSpan;
            if(secondSpan == 0) {
                placeService.updateDisplayIndex()
                        .thenApply(null)
                        .join();
                numberOfSecond = (long)24 * 60 * 60;
            }
            var remainTime = LocalTime.ofSecondOfDay(numberOfSecond);
            log.info(String.format("Next update will come after: %dh%dm%ds", remainTime.getHour(), remainTime.getMinute(), remainTime.getSecond()));
            Thread.sleep(numberOfSecond * 1000);
        } while (true);
    }
}
