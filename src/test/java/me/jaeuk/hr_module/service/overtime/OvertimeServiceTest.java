package me.jaeuk.hr_module.service.overtime;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.overtime.Overtime;
import me.jaeuk.hr_module.domain.work.WorkTime;
import me.jaeuk.hr_module.service.work.WorkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class OvertimeServiceTest {

    @Autowired
    private OvertimeService overtimeService;

    @Test
    @DisplayName("시간외,휴일,휴일연장,야간 시간계산")
    public void calOverTimeHrs(){
        Overtime reqOvertime = new Overtime();
        reqOvertime.setDutyDate(LocalDate.of(2020,12,11)); // 금
        reqOvertime.setAttendTime(LocalTime.of(7,0));
        reqOvertime.setLeaveTime(LocalTime.of(14,30));
        reqOvertime = overtimeService.calOverTimeHrs(reqOvertime, WorkTime.THREE_GROUP_TWO_SHIFT_W1);
        assertEquals(LocalTime.of(7,30), reqOvertime.getOverHrs());

        reqOvertime.setDutyDate(LocalDate.of(2020,12,12)); // 토
        reqOvertime.setAttendTime(LocalTime.of(14,0));
        reqOvertime.setLeaveTime(LocalTime.of(07,30));
        reqOvertime.setRestTime(LocalTime.of(2, 0));
        overtimeService.calOverTimeHrs(reqOvertime, WorkTime.THREE_GROUP_TWO_SHIFT_H);
        assertEquals(LocalTime.of(8,0), reqOvertime.getHolidayHrs());
        assertEquals(LocalTime.of(7,30), reqOvertime.getHolidayOverHrs());
        assertEquals(LocalTime.of(8,0), reqOvertime.getNightHrs());

        reqOvertime.setAttendTime(LocalTime.of(23,0));
        reqOvertime.setLeaveTime(LocalTime.of(4,30));
        overtimeService.calOverTimeHrs(reqOvertime, WorkTime.THREE_GROUP_TWO_SHIFT_H);
        assertEquals(LocalTime.of(5,30), reqOvertime.getNightHrs());

        reqOvertime.setAttendTime(LocalTime.of(19,0));
        reqOvertime.setLeaveTime(LocalTime.of(04,30));
        overtimeService.calOverTimeHrs(reqOvertime, WorkTime.THREE_GROUP_TWO_SHIFT_H);
        assertEquals(LocalTime.of(6,30), reqOvertime.getNightHrs());

        reqOvertime.setAttendTime(LocalTime.of(23,0));
        reqOvertime.setLeaveTime(LocalTime.of(6,30));
        overtimeService.calOverTimeHrs(reqOvertime, WorkTime.THREE_GROUP_TWO_SHIFT_H);
        assertEquals(LocalTime.of(7,0), reqOvertime.getNightHrs());
    }
}