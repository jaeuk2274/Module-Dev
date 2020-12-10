package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum WorkTime {
    TWO_GROUP_TWO_SHIFT_W1("2조2교대 1근", LocalTime.of(07, 00), LocalTime.of(15, 00))
    , TWO_GROUP_TWO_SHIFT_W2("2조2교대 2근", LocalTime.of(15, 00), LocalTime.of(23, 00))
    , TWO_GROUP_TWO_SHIFT_H("2조2교대 휴무")
    , THREE_GROUP_TWO_SHIFT_W1("3조2교대 1근", LocalTime.of(07, 00), LocalTime.of(19, 00))
    , THREE_GROUP_TWO_SHIFT_W2("3조2교대 2근", LocalTime.of(19, 00), LocalTime.of(07, 00))
    , THREE_GROUP_TWO_SHIFT_H("3조2교대 휴무")
    ;

    final private String name;
    private LocalTime attendTime;
    private LocalTime laeveTime;

    private WorkTime(String name) {
        this.name = name;
        System.out.println("name : " + name);
    }

    private WorkTime(String name, LocalTime attendTime, LocalTime laeveTime) {
        this.name = name;
        this.attendTime = attendTime;
        this.laeveTime = laeveTime;
    }
}
