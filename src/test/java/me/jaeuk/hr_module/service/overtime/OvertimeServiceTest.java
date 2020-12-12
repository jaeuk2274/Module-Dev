package me.jaeuk.hr_module.service.overtime;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.common.exception.OverTimeValidateException;
import me.jaeuk.hr_module.domain.overtime.Overtime;
import me.jaeuk.hr_module.domain.work.WorkTime;
import me.jaeuk.hr_module.domain.work.WorkType;
import me.jaeuk.hr_module.service.work.WorkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static me.jaeuk.hr_module.service.overtime.OverTimeCommon.getMinusTime;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class OvertimeServiceTest {

    @Autowired
    private OvertimeService overtimeService;

    @Test
    @DisplayName("연장근로 validate 2조2교대")
    public void validateOverTimeReq_twoGroup(){
        Overtime twoGroup1 = new Overtime();
        Overtime twoGroup2 = new Overtime();
        Overtime twoGroup3 = new Overtime();
        Overtime twoGroup4 = new Overtime();
        Overtime twoGroup5 = new Overtime();

        twoGroup1.setAttendTime(LocalTime.of(12,0));
        twoGroup1.setLeaveTime(LocalTime.of(15,0));
        twoGroup2.setAttendTime(LocalTime.of(13,0));
        twoGroup2.setLeaveTime(LocalTime.of(15,0));
        twoGroup3.setAttendTime(LocalTime.of(13,0));
        twoGroup3.setLeaveTime(LocalTime.of(16,0));
        twoGroup4.setAttendTime(LocalTime.of(22,30));
        twoGroup4.setLeaveTime(LocalTime.of(16,0));
        twoGroup5.setAttendTime(LocalTime.of(23,0));
        twoGroup5.setLeaveTime(LocalTime.of(16,0));

        assertAll("2조2교대 경계테스트",
                () -> assertTrue(overtimeService.validateOverTimeReq(twoGroup1, WorkTime.TWO_GROUP_TWO_SHIFT_W2)),
                () -> assertTrue(overtimeService.validateOverTimeReq(twoGroup2, WorkTime.TWO_GROUP_TWO_SHIFT_W2)),
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(twoGroup3, WorkTime.TWO_GROUP_TWO_SHIFT_W2);}),
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(twoGroup4, WorkTime.TWO_GROUP_TWO_SHIFT_W2);}),
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.validateOverTimeReq(twoGroup5, WorkTime.TWO_GROUP_TWO_SHIFT_W2);})
        );

    }


    @Test
    @DisplayName("연장근로 validate 상주")
    public void validateOverTimeReq_resident(){
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
                () -> assertThrows(OverTimeValidateException.class, () -> {overtimeService.calOverTimeHrs(resident9, WorkTime.RESIDENT_W);})
        );
    }

    @Test
    @DisplayName("시간외,휴일,휴일연장,야간 시간계산-상주")
    public void calOverTimeHrs_resident(){

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

        Overtime resident13 = new Overtime();
        Overtime resident14 = new Overtime();
        Overtime resident15 = new Overtime();
        Overtime resident16 = new Overtime();
        Overtime resident17 = new Overtime();
        Overtime resident18 = new Overtime();
        resident13.setAttendTime(LocalTime.of(11,0));
        resident13.setLeaveTime(LocalTime.of(23,0));
        resident14.setAttendTime(LocalTime.of(14,0));
        resident14.setLeaveTime(LocalTime.of(2,0));
        resident15.setAttendTime(LocalTime.of(23,0));
        resident15.setLeaveTime(LocalTime.of(23,30));
        resident16.setAttendTime(LocalTime.of(23,0));
        resident16.setLeaveTime(LocalTime.of(1,0));
        resident17.setAttendTime(LocalTime.of(1,0));
        resident17.setLeaveTime(LocalTime.of(3,0));
        resident18.setAttendTime(LocalTime.of(13,0));
        resident18.setLeaveTime(LocalTime.of(1,0));

        assertAll("상주 야간시간 테스트",
                () -> assertEquals(LocalTime.of(1,0),overtimeService.calOverTimeHrs(resident13, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(4,0),overtimeService.calOverTimeHrs(resident14, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(0,30),overtimeService.calOverTimeHrs(resident15, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(2,0),overtimeService.calOverTimeHrs(resident16, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(2,0),overtimeService.calOverTimeHrs(resident17, WorkTime.RESIDENT_H).getNightHrs()),
                () -> assertEquals(LocalTime.of(3,0),overtimeService.calOverTimeHrs(resident18, WorkTime.RESIDENT_H).getNightHrs())
        );


// 11 ~ 23 - 1 , 12시간 나온다

// 14 ~ 02  . 4시간

// 23~ 23 30
// 23 ~ 01
// 01 ~ 03
    }



}