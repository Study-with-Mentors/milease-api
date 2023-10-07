package vn.id.milease.mileaseapi.configuration;

import java.time.ZoneId;

public class AppConstant {
    public static ZoneId VN_ZONE_ID = ZoneId.of("Asia/Ho_Chi_Minh");
    public static long ALLOW_TIME_INTERVAL_TO_CAL_BONUS = (long) 30 * 24 * 60 * 60;

    //TODO [Khanh, P1]: Change this to real banking account
    public static String BANK_ID = "970415";
    public static String BANK_NUMBER = "123456789012";

}
