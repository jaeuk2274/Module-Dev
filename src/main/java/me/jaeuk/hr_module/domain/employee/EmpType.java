package me.jaeuk.hr_module.domain.employee;

import lombok.Getter;

@Getter
public enum EmpType {
    P("P직군"), E("E직군"), D("운전직");
    // 계약직,파견직,인턴,임시직, 등....
    final private String name;

    EmpType(String name) {
        this.name = name;
    }
}
