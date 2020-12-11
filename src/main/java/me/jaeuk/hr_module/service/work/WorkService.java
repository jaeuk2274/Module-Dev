package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.DutyType;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkService {

    public WorkTime getWorkTime(Employee emp, LocalDate dutydate){
        WorkCalculator workCalculator = createWorkCalculate(emp, dutydate);
        return workCalculator.getWorkShift();
    }

    private WorkCalculator createWorkCalculate(Employee emp, LocalDate dutydate) {
        DutyType dutyType = emp.getWorkType().getDutyType();

        if(DutyType.RESIDENT.equals(dutyType)){
            return new ResidentWorkCalculator(emp, dutydate);
        }
        else if(DutyType.SHIFT.equals(dutyType)){
            WorkShift workShift = emp.getWorkShift();
            switch(workShift.getType()){
                //TODO
                //case RESIDENT_DEFORMATION

                case TWO_GROUP_TWO_SHIFT:
                    return new TwoGroupTwoShiftWorkCalculator(emp, dutydate);
                case THREE_GROUP_TWO_SHIFT:
                    return new ThreeGroupTwoShiftWorkCalculator(emp, dutydate);
                case FOUR_GROUP_TWO_SHIFT:
                    return new FourGroupTwoShiftWorkCalculator(emp, dutydate);
                //TODO
                //case DRIVER:

                default:
                    throw new IllegalArgumentException("근무조가 없습니다." + emp);
            }
        }
        return new ResidentWorkCalculator(emp, dutydate);
    }
}