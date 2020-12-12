package me.jaeuk.hr_module.service.overtime;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.common.exception.OverTimeValidateException;
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
    @DisplayName("연장근로 validate")
    public void validateOverTimeReq(){
        Overtime resident1 = new Overtime();
        Overtime resident2 = new Overtime();
        Overtime resident3 = new Overtime();
        Overtime resident4 = new Overtime();
        Overtime resident5 = new Overtime();
        Overtime resident6 = new Overtime();
        resident1.setAttendTime(LocalTime.of(16,30));
        resident1.setLeaveTime(LocalTime.of(21,30));
        resident2.setAttendTime(LocalTime.of(17,00));
        resident2.setLeaveTime(LocalTime.of(21,30));
        resident3.setAttendTime(LocalTime.of(17,30));
        resident3.setLeaveTime(LocalTime.of(21,30));
        resident4.setAttendTime(LocalTime.of(1,30));
        resident4.setLeaveTime(LocalTime.of(7,30));
        resident5.setAttendTime(LocalTime.of(1,30));
        resident5.setLeaveTime(LocalTime.of(8,00));
        resident6.setAttendTime(LocalTime.of(1,30));
        resident6.setLeaveTime(LocalTime.of(8,30));

        assertAll("상주 경계테스트",
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(resident1, WorkTime.RESIDENT_W);}),
                () -> assertTrue(overtimeService.validateOverTimeReq(resident2, WorkTime.RESIDENT_W)),
                () -> assertTrue(overtimeService.validateOverTimeReq(resident3, WorkTime.RESIDENT_W)),
                () -> assertTrue(overtimeService.validateOverTimeReq(resident4, WorkTime.RESIDENT_W)),
                () -> assertTrue(overtimeService.validateOverTimeReq(resident5, WorkTime.RESIDENT_W)),
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(resident6, WorkTime.RESIDENT_W);})
        );

        Overtime resident7 = new Overtime();
        Overtime resident8 = new Overtime();
        Overtime resident9 = new Overtime();
        resident7.setAttendTime(LocalTime.of(1,30));
        resident7.setLeaveTime(LocalTime.of(8,30));
        resident8.setAttendTime(LocalTime.of(2,30));
        resident8.setLeaveTime(LocalTime.of(2,30));
        resident9.setAttendTime(LocalTime.of(1,30));
        resident9.setLeaveTime(LocalTime.of(2,30));
        resident9.setRestTime(LocalTime.of(2,0));

        assertAll("상주 통합테스트",
                () -> assertTrue(overtimeService.validateOverTimeReq(resident7, WorkTime.RESIDENT_H)),
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(resident8, WorkTime.RESIDENT_W);}),
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(resident9, WorkTime.RESIDENT_W);})
        );
    }

    @Test
    @DisplayName("시간외,휴일,휴일연장,야간 시간계산")
    public void calOverTimeHrs(){
        Overtime resident1 = new Overtime();
        Overtime resident2 = new Overtime();
        Overtime resident3 = new Overtime();
        Overtime resident4 = new Overtime();
        Overtime resident5 = new Overtime();
        Overtime resident6 = new Overtime();
        resident1.setAttendTime(LocalTime.of(17,0));
        resident1.setLeaveTime(LocalTime.of(23,30));
        resident2.setAttendTime(LocalTime.of(17,00));
        resident2.setLeaveTime(LocalTime.of(01,30));
        resident3.setAttendTime(LocalTime.of(17,0));
        resident3.setLeaveTime(LocalTime.of(03,30));
        resident3.setRestTime(LocalTime.of(01,0));
        resident4.setAttendTime(LocalTime.of(0,0));
        resident4.setLeaveTime(LocalTime.of(3,30));
        resident5.setAttendTime(LocalTime.of(1,0));
        resident5.setLeaveTime(LocalTime.of(3,30));
        resident6.setAttendTime(LocalTime.of(6,0));
        resident6.setLeaveTime(LocalTime.of(8,0));

        assertAll("상주 평일테스트",
                () -> assertEquals(LocalTime.of(6,30),overtimeService.calOverTimeHrs(resident1, WorkTime.RESIDENT_W).getOverHrs()),
                () -> assertEquals(LocalTime.of(1,30),overtimeService.calOverTimeHrs(resident1, WorkTime.RESIDENT_W).getNightHrs()),
                () -> assertEquals(LocalTime.of(8,30),overtimeService.calOverTimeHrs(resident2, WorkTime.RESIDENT_W).getOverHrs()),
                () -> assertEquals(LocalTime.of(3,30),overtimeService.calOverTimeHrs(resident2, WorkTime.RESIDENT_W).getNightHrs()),
                () -> assertEquals(LocalTime.of(9,30),overtimeService.calOverTimeHrs(resident3, WorkTime.RESIDENT_W).getOverHrs()),
                () -> assertEquals(LocalTime.of(5,30),overtimeService.calOverTimeHrs(resident3, WorkTime.RESIDENT_W).getNightHrs()),
                () -> assertEquals(LocalTime.of(3,30),overtimeService.calOverTimeHrs(resident4, WorkTime.RESIDENT_W).getOverHrs()),
                () -> assertEquals(LocalTime.of(3,30),overtimeService.calOverTimeHrs(resident4, WorkTime.RESIDENT_W).getNightHrs()),
                () -> assertEquals(LocalTime.of(2,30),overtimeService.calOverTimeHrs(resident5, WorkTime.RESIDENT_W).getOverHrs()),
                () -> assertEquals(LocalTime.of(2,30),overtimeService.calOverTimeHrs(resident5, WorkTime.RESIDENT_W).getNightHrs()),
                () -> assertEquals(LocalTime.of(2,0),overtimeService.calOverTimeHrs(resident6, WorkTime.RESIDENT_W).getOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident6, WorkTime.RESIDENT_W).getNightHrs()),

                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident1, WorkTime.RESIDENT_W).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident1, WorkTime.RESIDENT_W).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident2, WorkTime.RESIDENT_W).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident2, WorkTime.RESIDENT_W).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident3, WorkTime.RESIDENT_W).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident3, WorkTime.RESIDENT_W).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident4, WorkTime.RESIDENT_W).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident4, WorkTime.RESIDENT_W).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident5, WorkTime.RESIDENT_W).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident5, WorkTime.RESIDENT_W).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident6, WorkTime.RESIDENT_W).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident6, WorkTime.RESIDENT_W).getHolidayOverHrs())
        );

        Overtime resident7 = new Overtime();
        Overtime resident8 = new Overtime();
        Overtime resident9 = new Overtime();
        Overtime resident10 = new Overtime();
        Overtime resident11 = new Overtime();
        Overtime resident12 = new Overtime();
        resident7.setAttendTime(LocalTime.of(17,0));
        resident7.setLeaveTime(LocalTime.of(23,30));
        resident8.setAttendTime(LocalTime.of(17,00));
        resident8.setLeaveTime(LocalTime.of(01,30));
        resident9.setAttendTime(LocalTime.of(17,0));
        resident9.setLeaveTime(LocalTime.of(03,30));
        resident9.setRestTime(LocalTime.of(01,0));
        resident10.setAttendTime(LocalTime.of(0,0));
        resident10.setLeaveTime(LocalTime.of(3,30));
        resident11.setAttendTime(LocalTime.of(1,0));
        resident11.setLeaveTime(LocalTime.of(3,30));
        resident12.setAttendTime(LocalTime.of(6,0));
        resident12.setLeaveTime(LocalTime.of(8,0));

        assertAll("상주 휴일테스트",
                () -> assertEquals(LocalTime.of(6,30),overtimeService.calOverTimeHrs(resident7, WorkTime.RESIDENT_H).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident7, WorkTime.RESIDENT_H).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(1,30),overtimeService.calOverTimeHrs(resident7, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(8,0),overtimeService.calOverTimeHrs(resident8, WorkTime.RESIDENT_H).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,30),overtimeService.calOverTimeHrs(resident8, WorkTime.RESIDENT_H).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(3,30),overtimeService.calOverTimeHrs(resident8, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(8,0),overtimeService.calOverTimeHrs(resident9, WorkTime.RESIDENT_H).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(1,30),overtimeService.calOverTimeHrs(resident9, WorkTime.RESIDENT_H).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(5,30),overtimeService.calOverTimeHrs(resident9, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(3,30),overtimeService.calOverTimeHrs(resident10, WorkTime.RESIDENT_H).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident10, WorkTime.RESIDENT_H).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(3,30),overtimeService.calOverTimeHrs(resident10, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(2,30),overtimeService.calOverTimeHrs(resident11, WorkTime.RESIDENT_H).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident11, WorkTime.RESIDENT_H).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(2,30),overtimeService.calOverTimeHrs(resident11, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(2,0),overtimeService.calOverTimeHrs(resident12, WorkTime.RESIDENT_H).getHolidayHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident12, WorkTime.RESIDENT_H).getHolidayOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident12, WorkTime.RESIDENT_H).getNightHrs()),

                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident7, WorkTime.RESIDENT_H).getOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident8, WorkTime.RESIDENT_H).getOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident9, WorkTime.RESIDENT_H).getOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident10, WorkTime.RESIDENT_H).getOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident11, WorkTime.RESIDENT_H).getOverHrs()),
                () -> assertEquals(LocalTime.of(0,0),overtimeService.calOverTimeHrs(resident12, WorkTime.RESIDENT_H).getOverHrs())
        );
    }


}