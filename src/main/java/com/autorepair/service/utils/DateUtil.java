package com.autorepair.service.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

  public static String getDate() {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
    Calendar calObj = Calendar.getInstance();
    return df.format(calObj.getTime());
  }

  public static String getLocalCurrentDate() {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calObj = Calendar.getInstance();
    return df.format(calObj.getTime());
  }

  public static boolean isDateWithinRange(LocalDate startDate, LocalDate endDate,
      LocalDate existingStartDate, LocalDate existingEndDate) {

    return !startDate.isBefore(existingStartDate) && !startDate.isAfter(existingEndDate)
        || !endDate.isBefore(existingStartDate) && !endDate.isAfter(existingEndDate);
  }

  public static boolean isDateWithStartAndEndRange(LocalDate startDate, LocalDate endDate,
      LocalDate existingSlotDate) {
    return existingSlotDate.isAfter(startDate) && existingSlotDate.isBefore(endDate);
  }

  public static LocalDate getLocalDate(String date) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter);
  }

  public static String getSlotStartDate(String startDate) {
    return DateUtil.getLocalDate(startDate).isBefore(
        DateUtil.getLocalDate(LocalDate.now().toString())) ? DateUtil.getLocalCurrentDate()
            : startDate;
  }
}
