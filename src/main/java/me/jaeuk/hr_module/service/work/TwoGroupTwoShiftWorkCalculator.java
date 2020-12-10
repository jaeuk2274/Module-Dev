package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.work.Work;

import java.time.LocalDate;

public class TwoGroupTwoShiftWorkCalculator implements WorkCalculator {
    private Work.Shift shift;
    private LocalDate dutydate;

    public TwoGroupTwoShiftWorkCalculator(Work.Shift shift, LocalDate dutydate) {
        this.shift = shift;
        this.dutydate = dutydate;
    }

    @Override
    public Work.Time getWorkShift(){
        Work.Type type = shift.getType();
        long stdDay = dutydate.toEpochDay() - type.getStartDate().toEpochDay();
        long shiftChkCnt = 0;

        switch(shift){
            case TWO_GROUP_TWO_SHIFT_A:
                shiftChkCnt = stdDay%14;
                break;
            case TWO_GROUP_TWO_SHIFT_B:
                shiftChkCnt = (stdDay+7)%14;
                break;
            default:
                throw new IllegalArgumentException("근무조가 없습니다." + shift.getName());
        }

        if(shiftChkCnt < 5) {
            return Work.Time.TWO_GROUP_TWO_SHIFT_W1;
        }else if(shiftChkCnt == 5 || shiftChkCnt == 6) {
            return Work.Time.TWO_GROUP_TWO_SHIFT_H;
        }else if(shiftChkCnt < 12) {
            return Work.Time.TWO_GROUP_TWO_SHIFT_W2;
        }else if(shiftChkCnt == 12 || shiftChkCnt == 13){
            return Work.Time.TWO_GROUP_TWO_SHIFT_H;
        }else {
            throw new IllegalArgumentException("잘못된 시간입니다." + shift.getName());
        }
    }

}