package me.jaeuk.hr_module.service.overtime;

import me.jaeuk.hr_module.domain.work.AttendType;

import java.time.LocalTime;

public class OverTimeCommon {

    static LocalTime MAX_HOLIDAY_HRS = LocalTime.of(8,0);
    static LocalTime START_NIGHT_HRS = LocalTime.of(22,0);
    static LocalTime END_NIGHT_HRS = LocalTime.of(6,0);
    static LocalTime MIDNIGHT = LocalTime.of(0,0);
    static LocalTime NOON = LocalTime.of(12,0);

    static LocalTime getMinusTime(LocalTime stdTime, LocalTime minTime) {
        return stdTime.minusHours(minTime.getHour()).minusMinutes(minTime.getMinute());
    }

    static LocalTime getPlusTime(LocalTime stdTime, LocalTime plusTime) {
        return stdTime.plusHours(plusTime.getHour()).plusMinutes(plusTime.getMinute());
    }

    static boolean isSmaller(LocalTime stdTime, LocalTime compareTime) {
        int value = stdTime.compareTo(compareTime);
        if(value == -1){ return true; }
        else{ return false; }
    }

    static boolean isBigger(LocalTime stdTime, LocalTime compareTime) {
        int value = stdTime.compareTo(compareTime);
        if(value == 1){ return true;}
        else{ return false; }
    }

    static boolean isEqual(LocalTime stdTime, LocalTime compareTime) {
        int value = stdTime.compareTo(compareTime);
        if(value == 0){ return true; }
        else{ return false; }
    }

    static boolean isEqualOrSmaller(LocalTime stdTime, LocalTime compareTime) {
        int value = stdTime.compareTo(compareTime);
        if(value == -1 || value == 0){ return true; }
        else{ return false; }
    }

    static boolean isEqualOrBigger(LocalTime stdTime, LocalTime compareTime) {
        int value = stdTime.compareTo(compareTime);
        if(value == 1 || value == 0){ return true;}
        else{ return false; }
    }

    static boolean isHoliday(AttendType attendType) {
        return AttendType.H.equals(attendType);
    }
}
