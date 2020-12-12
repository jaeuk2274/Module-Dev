package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

@Getter
public enum AttendType {
    W("근무"), H("휴");

    final private String name;

    AttendType(String name) {
        this.name = name;
    }
}
