package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

@Getter
public enum DutyType {
    RESIDENT("상주"), SHIFT("교대");

    final private String name;

    DutyType(String name) {
        this.name = name;
    }
}
