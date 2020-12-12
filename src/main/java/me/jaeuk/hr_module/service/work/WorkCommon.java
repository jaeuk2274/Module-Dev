package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkType;

import java.time.LocalDate;

public class WorkCommon {

    static long getStdDay(WorkShift workShift, LocalDate dutyDate) {
        WorkType type = workShift.getType();
        long stdDay = dutyDate.toEpochDay() - type.getStartDate().toEpochDay();
        return stdDay;
    }
}
