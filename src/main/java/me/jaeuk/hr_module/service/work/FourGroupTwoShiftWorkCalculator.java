package me.jaeuk.hr_module.service.work;

import lombok.AllArgsConstructor;
import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;
import me.jaeuk.hr_module.domain.work.WorkType;

import java.time.LocalDate;

import static me.jaeuk.hr_module.service.work.WorkCommon.getStdDay;

@AllArgsConstructor
public class FourGroupTwoShiftWorkCalculator implements WorkCalculator {
    private Employee emp;
    private LocalDate dutyDate;

    @Override
    public WorkTime getWorkTime(){
        WorkShift workShift = emp.getWorkShift();
        long stdDay = getStdDay(workShift, dutyDate);
        long shiftChkCnt = 0;

        switch(emp.getWorkShift()){
            case FOUR_GROUP_TWO_SHIFT_A:
                shiftChkCnt = stdDay%8;
                break;
            case FOUR_GROUP_TWO_SHIFT_B:
                shiftChkCnt = (stdDay+2)%8;
                break;
            case FOUR_GROUP_TWO_SHIFT_C:
                shiftChkCnt = (stdDay+4)%8;
                break;
            case FOUR_GROUP_TWO_SHIFT_D:
                shiftChkCnt = (stdDay+6)%8;
                break;
            default:
                throw new IllegalArgumentException("근무조가 없습니다." + workShift.getName());
        }

        if(shiftChkCnt < 2) {
            return WorkTime.FOUR_GROUP_TWO_SHIFT_W1;
        }else if(shiftChkCnt == 2 || shiftChkCnt == 3) {
            return WorkTime.FOUR_GROUP_TWO_SHIFT_H;
        }else if(shiftChkCnt < 6) {
            return WorkTime.FOUR_GROUP_TWO_SHIFT_W2;
        }else if(shiftChkCnt == 6 || shiftChkCnt == 7) {
            return WorkTime.FOUR_GROUP_TWO_SHIFT_H;
        }else {
            throw new IllegalArgumentException("잘못된 시간입니다." + workShift.getName());
        }
    }


}