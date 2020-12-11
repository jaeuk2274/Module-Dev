package me.jaeuk.hr_module.domain.employee;

import lombok.*;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkType;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Employee {
    private Long empId;
    @NonNull private String empName;
    @NonNull private WorkType workType;
    @NonNull private WorkShift workShift;

    public Employee(@NonNull String empName) {
        this.empName = empName;
    }
}
