package me.jaeuk.hr_module.service.work;

import lombok.AllArgsConstructor;
import me.jaeuk.hr_module.domain.employee.Emp;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;

import java.time.LocalDate;

import static me.jaeuk.hr_module.service.work.WorkCommon.getStdDay;

@AllArgsConstructor
public class TwoGroupTwoShiftWorkCalculator implements WorkCalculator {
    private Emp emp;
    private LocalDate dutyDate;

    @Override
    public WorkTime getWorkTime(){
        WorkShift workShift = emp.getWorkShift();
        long stdDay = getStdDay(workShift, dutyDate);
        long shiftChkCnt = 0;

        switch(workShift){
            case TWO_GROUP_TWO_SHIFT_A:
                shiftChkCnt = stdDay%14;
                break;
            case TWO_GROUP_TWO_SHIFT_B:
                shiftChkCnt = (stdDay+7)%14;
                break;
            default:
                throw new IllegalArgumentException("근무조가 없습니다." + workShift.getName());
        }

        if(shiftChkCnt < 5) {
            return WorkTime.TWO_GROUP_TWO_SHIFT_W1;
        }else if(shiftChkCnt == 5 || shiftChkCnt == 6) {
            return WorkTime.TWO_GROUP_TWO_SHIFT_H;
        }else if(shiftChkCnt < 12) {
            return WorkTime.TWO_GROUP_TWO_SHIFT_W2;
        }else if(shiftChkCnt == 12 || shiftChkCnt == 13){
            return WorkTime.TWO_GROUP_TWO_SHIFT_H;
        }else {
            throw new IllegalArgumentException("잘못된 시간입니다." + workShift.getName());
        }
    }

}