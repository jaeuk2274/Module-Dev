package me.jaeuk.hr_module.service.work;

import lombok.AllArgsConstructor;
import me.jaeuk.hr_module.domain.employee.Emp;
import me.jaeuk.hr_module.domain.work.WorkTime;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@AllArgsConstructor
public class OldResidentWorkCalculator implements WorkCalculator {
    private Emp emp;
    private LocalDate dutyDate;

    @Override
    public WorkTime getWorkTime(){
        DayOfWeek dayOfWeek = dutyDate.getDayOfWeek();
        String attendType;

        if (dayOfWeek.equals(SATURDAY) || dayOfWeek.equals(SUNDAY)) {
            return WorkTime.OLD_RESIDENT_H;
        } else{
            return WorkTime.OLD_RESIDENT_W;
        }
    }

}