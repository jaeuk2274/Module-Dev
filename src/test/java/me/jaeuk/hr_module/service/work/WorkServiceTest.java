package me.jaeuk.hr_module.service.work;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.employee.Emp;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;
import me.jaeuk.hr_module.domain.work.WorkType;
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
    @DisplayName("상주계열 근무시 조회")
    public void getResidentWorkTime() {
        Emp emp = new Emp("최이조");
        emp.setWorkType(WorkType.RESIDENT);

        assertEquals(WorkTime.RESIDENT_W, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,2)).getWorkTime());
        assertEquals(WorkTime.RESIDENT_W, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,3)).getWorkTime());
        assertEquals(WorkTime.RESIDENT_W, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,4)).getWorkTime());
        assertEquals(WorkTime.RESIDENT_W, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,5)).getWorkTime());
        assertEquals(WorkTime.RESIDENT_W, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,6)).getWorkTime());
        assertEquals(WorkTime.RESIDENT_H, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,7)).getWorkTime());
        assertEquals(WorkTime.RESIDENT_H, new ResidentWorkCalculator(emp, LocalDate.of(2020,11,8)).getWorkTime());
    }


    @Test
    @DisplayName("교대 근무시간 조회")
    public void getShiftWorkTime(){
        Emp emp = new Emp("최이조");
        emp.setWorkType(WorkType.TWO_GROUP_TWO_SHIFT);
        emp.setWorkShift(WorkShift.TWO_GROUP_TWO_SHIFT_A);

        // 2조2교대 - 주간5 / 휴일2 / 야간5 / 휴일2
        int month = 11;
        int[] W1Array = {2,3,4,5,6};
        for (int day : W1Array){
            assertEquals(WorkTime.TWO_GROUP_TWO_SHIFT_W1, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        int[] W2Array = {9,10,11,12,13};
        for (int day : W2Array){
            assertEquals(WorkTime.TWO_GROUP_TWO_SHIFT_W2, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        int[] HArray = {7,8,14,15};
        for (int day : HArray){
            assertEquals(WorkTime.TWO_GROUP_TWO_SHIFT_H, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        
        // 3조2교대 - 주간4 / 휴일2 / 야간4 / 휴일2
        emp.setWorkType(WorkType.THREE_GROUP_TWO_SHIFT);
        emp.setWorkShift(WorkShift.THREE_GROUP_TWO_SHIFT_A);
        month = 11;
        W1Array = new int[]{2,3,4,5};
        for (int day : W1Array){
            assertEquals(WorkTime.THREE_GROUP_TWO_SHIFT_W1, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        W2Array = new int[]{8,9,10,11};
        for (int day : W2Array){
            assertEquals(WorkTime.THREE_GROUP_TWO_SHIFT_W2, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        HArray = new int[]{6,7};
        for (int day : HArray){
            assertEquals(WorkTime.THREE_GROUP_TWO_SHIFT_H, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        
        // 4조2교대 - 주간2 / 휴일2 / 야간2 / 휴일2
        emp.setWorkType(WorkType.FOUR_GROUP_TWO_SHIFT);
        emp.setWorkShift(WorkShift.FOUR_GROUP_TWO_SHIFT_A);
        month = 11;
        W1Array = new int[]{2,3};
        for (int day : W1Array){
            assertEquals(WorkTime.FOUR_GROUP_TWO_SHIFT_W1, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        W2Array = new int[]{6,7};
        for (int day : W2Array){
            assertEquals(WorkTime.FOUR_GROUP_TWO_SHIFT_W2, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
        HArray = new int[]{4,5};
        for (int day : HArray){
            assertEquals(WorkTime.FOUR_GROUP_TWO_SHIFT_H, workService.getWorkTime(emp, LocalDate.of(2020,month,day)));
        }
    }




}