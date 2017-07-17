package win.sourcecode.java.feature.stream;

import org.junit.Test;

import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

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
    public void localDate() {
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

    @Test
    public void localTime() {
        LocalTime bedTime = LocalTime.of(22, 30);
        LocalTime wakeupTime = bedTime.plusHours(8);
        System.out.println(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(wakeupTime));
    }

    @Test
    public void formatter() {
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
        System.out.println(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(OffsetDateTime.now()));
        System.out.println(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()));
        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(ZonedDateTime.now()));
        System.out.println(DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));

        System.out.println("");

        System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now()));
        System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(LocalDateTime.now()));
        System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).format(LocalDateTime.now()));
        System.out.println(DateTimeFormatter.ofPattern("yyyy MM dd HH MM ss a").format(LocalDateTime.now()));
    }

    @Test
    public void adapter() {
        // Instant form need locale and zone
        Date date = Date.from(Instant.now());
        Instant instant = date.toInstant();
        System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.CHINA).withZone(ZoneId.systemDefault()).format(instant));

        GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.now());
        ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();
        System.out.println(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));

        // java.sql.Timestamp
        Timestamp timestamp = Timestamp.from(instant);
        System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.CHINA).withZone(ZoneId.systemDefault()).format(timestamp.toInstant()));

        timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(timestamp.toLocalDateTime()));

        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
        System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(sqlDate.toLocalDate()));

        java.sql.Time sqlTime = java.sql.Time.valueOf(LocalTime.now());
        System.out.println(DateTimeFormatter.ISO_LOCAL_TIME.format(sqlTime.toLocalTime()));

        try {
            System.out.println(DateTimeFormatter.ofPattern("yyyy").toFormat().parseObject("1997"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.of("+08:00"));
        System.out.println(timeZone.toZoneId().getId());

        FileTime fileTime = FileTime.from(instant);
        System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.CHINA).withZone(ZoneId.systemDefault()).format(fileTime.toInstant()));
    }
}
