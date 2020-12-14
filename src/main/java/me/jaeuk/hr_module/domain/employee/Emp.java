package me.jaeuk.hr_module.domain.employee;

import lombok.*;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkType;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Emp {
    private Long empId;
    @NonNull private String empName;
    @NonNull private WorkType workType;
    @NonNull private WorkShift workShift;
    @NonNull private EmpType empType;

    public Emp(@NonNull String empName) {
        this.empName = empName;
    }
}
