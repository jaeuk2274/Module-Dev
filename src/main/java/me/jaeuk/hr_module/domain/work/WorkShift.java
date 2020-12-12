package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public enum WorkShift {
    OLD_RESIDENT_A("구상주", WorkType.OLD_RESIDENT)
    , RESIDENT_A("상주", WorkType.RESIDENT)
    , RESIDENT_DEFORMATION_A("상주(변형)", WorkType.RESIDENT_DEFORMATION)
    , DRIVER_A("운전직", WorkType.DRIVER)
    , TWO_GROUP_TWO_SHIFT_A("2조2교대 A조", WorkType.TWO_GROUP_TWO_SHIFT)
    , TWO_GROUP_TWO_SHIFT_B("2조2교대 B조", WorkType.TWO_GROUP_TWO_SHIFT)
    , THREE_GROUP_TWO_SHIFT_A("3조2교대 A조", WorkType.THREE_GROUP_TWO_SHIFT)
    , THREE_GROUP_TWO_SHIFT_B("3조2교대 B조", WorkType.THREE_GROUP_TWO_SHIFT)
    , THREE_GROUP_TWO_SHIFT_C("3조2교대 C조", WorkType.THREE_GROUP_TWO_SHIFT)
    , FOUR_GROUP_TWO_SHIFT_A("4조2교대 A조", WorkType.FOUR_GROUP_TWO_SHIFT)
    , FOUR_GROUP_TWO_SHIFT_B("4조2교대 B조", WorkType.FOUR_GROUP_TWO_SHIFT)
    , FOUR_GROUP_TWO_SHIFT_C("4조2교대 C조", WorkType.FOUR_GROUP_TWO_SHIFT)
    , FOUR_GROUP_TWO_SHIFT_D("4조2교대 D조", WorkType.FOUR_GROUP_TWO_SHIFT)
    ;

    final private String name;
    final private WorkType type;

    private WorkShift(String name, WorkType type) {
        this.name = name;
        this.type = type;
    }

}
