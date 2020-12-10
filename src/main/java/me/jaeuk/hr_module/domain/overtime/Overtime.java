package me.jaeuk.hr_module.domain.overtime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter @Setter @ToString
public class Overtime {
    private Long overtimeId;
    private Long empId;
    private LocalDate dutyDate;
    private LocalTime attendTime;
    private LocalTime leaveTime;
    private LocalTime restTime = LocalTime.of(0,0);
    private LocalTime overHrs;
    private LocalTime holidayHrs;
    private LocalTime holidayOverHrs;
    private LocalTime nightHrs;
}
