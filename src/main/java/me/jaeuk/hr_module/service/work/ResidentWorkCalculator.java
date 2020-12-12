package me.jaeuk.hr_module.service.work;

import lombok.AllArgsConstructor;
import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkTime;
import me.jaeuk.hr_module.domain.work.WorkType;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@AllArgsConstructor
public class ResidentWorkCalculator implements WorkCalculator {
    private Employee emp;
    private LocalDate dutyDate;

    @Override
    public WorkTime getWorkTime(){
        DayOfWeek dayOfWeek = dutyDate.getDayOfWeek();
        String attendType;

        if (dayOfWeek.equals(SATURDAY) || dayOfWeek.equals(SUNDAY)) {
            return WorkTime.RESIDENT_H;
        } else{
            return WorkTime.RESIDENT_W;
        }
    }

}