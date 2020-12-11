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
    public WorkTime getWorkShift(){
        DayOfWeek dayOfWeek = dutyDate.getDayOfWeek();
        String attendType;

        if (dayOfWeek.equals(SATURDAY) || dayOfWeek.equals(SUNDAY)) {
            attendType = "H";
        } else{
            attendType = "W";
        }

        for(WorkTime workTime : WorkTime.values()) {
            if(attendType.equals(workTime.getAttendType()) && workTime.getWorkType().equals(emp.getWorkType())){
                return workTime;
            }
        }
        throw new IllegalArgumentException("근무조가 없습니다." + emp);
    }

}