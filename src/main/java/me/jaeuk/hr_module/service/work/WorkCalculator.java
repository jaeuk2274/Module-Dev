package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.work.WorkTime;

public interface WorkCalculator {
    WorkTime getWorkShift();
}