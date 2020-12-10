package me.jaeuk.hr_module.service.work;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.work.Work;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,2)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,3)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,4)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,5)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,6)));

        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_H, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,7)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_H, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,8)));

        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,9)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,10)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,11)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,12)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,13)));

        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_H, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,14)));
        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_H, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,15)));

        assertEquals(Work.Time.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(Work.Shift.TWO_GROUP_TWO_SHIFT_A, LocalDate.of(2020,11,16)));



    }
}