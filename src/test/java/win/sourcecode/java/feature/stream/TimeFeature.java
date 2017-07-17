package win.sourcecode.java.feature.stream;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by wenyou on 2017/7/17.
 */
public class TimeFeature {
    @Test
    public void create()
            throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(50);
        Instant end = Instant.now();
        Thread.sleep(50);
        Instant end2 = Instant.now();

        // 时间差
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());

        // 比较大小
        Duration duration2 = Duration.between(start, end2);
        System.out.println(duration.minus(duration2).isNegative());
    }

    @Test
    public void localDate(){
        LocalDate localDate = LocalDate.of(2017, 7, 17);
        // 第 256 天
        LocalDate programmersDay = LocalDate.of(2017, Month.JANUARY, 1).plusDays(255);
        // 日期差
        System.out.println(programmersDay.until(localDate).getDays());
        // 1-7 周日为 7
        System.out.println(LocalDate.now().getDayOfWeek().getValue());
        // 7 月第一个周末
        System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(LocalDate.of(2017, 7, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY))));

    }
}
