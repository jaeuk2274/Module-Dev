package me.jaeuk.hr_module.service.work;

import lombok.AllArgsConstructor;
import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.WorkTime;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.*;

@AllArgsConstructor
public class ResidentDeformationWorkCalculator implements WorkCalculator {
    private Employee emp;
    private LocalDate dutyDate;

    @Override
    public WorkTime getWorkTime(){
        DayOfWeek dayOfWeek = dutyDate.getDayOfWeek();

        if (dayOfWeek.equals(MONDAY)) {
           return WorkTime.RESIDENT_DEFORMATION_W1;
        } else if (dayOfWeek.equals(TUESDAY) || dayOfWeek.equals(WEDNESDAY) || dayOfWeek.equals(THURSDAY)) {
            return WorkTime.RESIDENT_DEFORMATION_W2;
        } else if (dayOfWeek.equals(FRIDAY)) {
            return WorkTime.RESIDENT_DEFORMATION_W3;
        } else {
            return WorkTime.RESIDENT_DEFORMATION_H;
        }
    }
}