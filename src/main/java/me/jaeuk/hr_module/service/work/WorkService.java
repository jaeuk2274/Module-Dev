package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WorkService {

    public WorkTime getWorkTime(Employee emp, LocalDate dutydate){
        WorkCalculator workCalculator = createWorkCalculate(emp, dutydate);
        return workCalculator.getWorkTime();
    }

    private WorkCalculator createWorkCalculate(Employee emp, LocalDate dutydate) {
        WorkShift workShift = emp.getWorkShift();
        switch(workShift.getType()){
            case OLD_RESIDENT:
                return new OldResidentWorkCalculator(emp, dutydate);
            case RESIDENT:
                return new ResidentWorkCalculator(emp, dutydate);
            case RESIDENT_DEFORMATION:
                return new ResidentDeformationWorkCalculator(emp, dutydate);
            case TWO_GROUP_TWO_SHIFT:
                return new TwoGroupTwoShiftWorkCalculator(emp, dutydate);
            case THREE_GROUP_TWO_SHIFT:
                return new ThreeGroupTwoShiftWorkCalculator(emp, dutydate);
            case FOUR_GROUP_TWO_SHIFT:
                return new FourGroupTwoShiftWorkCalculator(emp, dutydate);
            case DRIVER:
                return new DriverWorkCalculator(emp, dutydate);
            default:
                throw new IllegalArgumentException("근무조가 없습니다." + emp);
        }

    }
}