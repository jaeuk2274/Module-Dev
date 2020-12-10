package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.work.Work;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkService {

    public Work.Time getWorkTime(Work.Shift shift, LocalDate dutydate){
        WorkCalculator workCalculator = createWorkCalculate(shift, dutydate);
        return workCalculator.getWorkShift();
    }

    private WorkCalculator createWorkCalculate(Work.Shift shift, LocalDate dutydate) {
        switch(shift.getType()){
            case TWO_GROUP_TWO_SHIFT:
                return new TwoGroupTwoShiftWorkCalculator(shift, dutydate);
            default:
                throw new IllegalArgumentException("근무조가 없습니다." + shift.getName());
        }
    }
}