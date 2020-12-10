package me.jaeuk.hr_module.service.work;

import me.jaeuk.hr_module.domain.work.Work;

import java.time.LocalDate;

public interface WorkCalculator {
    Work.Time getWorkShift();
}