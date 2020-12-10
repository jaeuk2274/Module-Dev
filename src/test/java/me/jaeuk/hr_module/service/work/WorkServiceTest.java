package me.jaeuk.hr_module.service.work;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.work.Work;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class WorkServiceTest {

    @Autowired
    private WorkService workService;

    @Test
    @DisplayName("근무조+해당날짜 근무시간 조회")
    public void getWorkTime_test(){
        // 2조2교대 - 주간5일 휴일2일 야간5일 휴일2
        int month = 11;
        int[] W1Array = {2,3,4,5,6};
        for (int day : W1Array){
            assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,month,day)));
        }
        int[] W2Array = {9,10,11,12,13};
        for (int day : W2Array){
            assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,month,day)));
        }
        int[] HArray = {7,8,14,15};
        for (int day : HArray){
            assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_H, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,month,day)));
        }

    }

    @Test
    @DisplayName("근무조+해당날짜 근무시간 조회")
    public void calHrs_test(LocalTime attendTime, LocalTime leaveTime, LocalTime restTime, Work.Time time){


    }
}