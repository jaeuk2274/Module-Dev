package me.jaeuk.hr_module.domain.employee;

import lombok.*;
import me.jaeuk.hr_module.domain.work.WorkShift;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Employee {
    private Long empId;
    @NonNull private String empName;
    @NonNull private WorkShift workShift;

    public Employee(@NonNull String empName, @NonNull WorkShift workShift) {
        this.empName = empName;
        this.workShift = workShift;
    }
}
