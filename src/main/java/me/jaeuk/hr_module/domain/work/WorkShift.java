package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

@Getter
public enum WorkShift {
    TWO_GROUP_TWO_SHIFT_A("2조2교대 A조", WorkType.TWO_GROUP_TWO_SHIFT)
    , TWO_GROUP_TWO_SHIFT_B("2조2교대 B조", WorkType.TWO_GROUP_TWO_SHIFT)
    , THREE_GROUP_TWO_SHIFT_A("3조2교대 A조", WorkType.THREE_GROUP_TWO_SHIFT)
    , THREE_GROUP_TWO_SHIFT_B("3조2교대 B조", WorkType.THREE_GROUP_TWO_SHIFT)
    , THREE_GROUP_TWO_SHIFT_C("3조2교대 C조", WorkType.THREE_GROUP_TWO_SHIFT)
    ;

    final private String name;
    final private WorkType type;

    private WorkShift(String name, WorkType type) {
        this.name = name;
        this.type = type;
    }

}
