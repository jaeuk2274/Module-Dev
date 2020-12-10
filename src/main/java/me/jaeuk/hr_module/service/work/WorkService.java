package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkService {

    public WorkTime getWorkTime(WorkShift workShift, LocalDate dutydate){
        WorkCalculator workCalculator = createWorkCalculate(workShift, dutydate);
        return workCalculator.getWorkShift();
    }

    private WorkCalculator createWorkCalculate(WorkShift workShift, LocalDate dutydate) {
        switch(workShift.getType()){
            case TWO_GROUP_TWO_SHIFT:
                return new TwoGroupTwoShiftWorkCalculator(workShift, dutydate);
            case THREE_GROUP_TWO_SHIFT:
                return new ThreeGroupTwoShiftWorkCalculator(workShift, dutydate);
            default:
                throw new IllegalArgumentException("근무조가 없습니다." + workShift.getName());
        }
    }
}