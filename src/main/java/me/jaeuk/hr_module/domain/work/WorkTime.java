package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum WorkTime {
    OLD_RESIDENT_W("구상주 출근", WorkType.OLD_RESIDENT, "W", LocalTime.of(9, 0), LocalTime.of(18, 0),LocalTime.of(1,0))
    , OLD_RESIDENT_H("구상주 휴무", WorkType.OLD_RESIDENT,  "H")
    , RESIDENT_W("상주 출근", WorkType.RESIDENT,  "W", LocalTime.of(10, 0), LocalTime.of(18, 0),LocalTime.of(1,0))
    , RESIDENT_H("상주 휴무", WorkType.RESIDENT,  "H")
    , RESIDENT_DEFORMATION_W1("상주(변형) 월", WorkType.RESIDENT_DEFORMATION,  "W", LocalTime.of(10, 0), LocalTime.of(18, 0),LocalTime.of(1,0))
    , RESIDENT_DEFORMATION_W2("상주(변형) 화수목", WorkType.RESIDENT_DEFORMATION,  "W", LocalTime.of(8, 0), LocalTime.of(18, 0),LocalTime.of(1,0))
    , RESIDENT_DEFORMATION_W3("상주(변형) 금", WorkType.RESIDENT_DEFORMATION,  "W", LocalTime.of(8, 0), LocalTime.of(15, 0),LocalTime.of(1,0))
    , RESIDENT_DEFORMATION_H("상주(변형) 휴무", WorkType.RESIDENT_DEFORMATION,  "H")
    , TWO_GROUP_TWO_SHIFT_W1("2조2교대 1근", WorkType.TWO_GROUP_TWO_SHIFT, "W", LocalTime.of(07, 0), LocalTime.of(15, 0))
    , TWO_GROUP_TWO_SHIFT_W2("2조2교대 2근", WorkType.TWO_GROUP_TWO_SHIFT, "W", LocalTime.of(15, 0), LocalTime.of(23, 0))
    , TWO_GROUP_TWO_SHIFT_H("2조2교대 휴무", WorkType.TWO_GROUP_TWO_SHIFT,  "H")
    , THREE_GROUP_TWO_SHIFT_W1("3조2교대 1근", WorkType.THREE_GROUP_TWO_SHIFT,  "W",LocalTime.of(07, 0), LocalTime.of(19, 0))
    , THREE_GROUP_TWO_SHIFT_W2("3조2교대 2근", WorkType.THREE_GROUP_TWO_SHIFT,  "W",LocalTime.of(19, 0), LocalTime.of(07, 0))
    , THREE_GROUP_TWO_SHIFT_H("3조2교대 휴무", WorkType.THREE_GROUP_TWO_SHIFT, "H")
    , FOUR_GROUP_TWO_SHIFT_W1("4조2교대 1근", WorkType.FOUR_GROUP_TWO_SHIFT,  "W",LocalTime.of(07, 0), LocalTime.of(19, 0))
    , FOUR_GROUP_TWO_SHIFT_W2("4조2교대 2근", WorkType.FOUR_GROUP_TWO_SHIFT,  "W",LocalTime.of(19, 0), LocalTime.of(07, 0))
    , FOUR_GROUP_TWO_SHIFT_H("4조2교대 휴무", WorkType.FOUR_GROUP_TWO_SHIFT,  "H")
    , DRIVER_W1("운전직 평일", WorkType.DRIVER,  "W", LocalTime.of(6, 30), LocalTime.of(21, 0),LocalTime.of(3,0))
    , DRIVER_W2("운전직 주말", WorkType.DRIVER,  "W", LocalTime.of(8, 0), LocalTime.of(19, 0),LocalTime.of(3,0))
    ;

    final private String name;
    private WorkType workType;
    private String attendType;
    private LocalTime attendTime;
    private LocalTime laeveTime;
    private LocalTime restTime;

    private WorkTime(String name, WorkType workType, String attendType) {
        this.name = name;
        this.workType = workType;
        this.attendType = attendType;
    }
    private WorkTime(String name, WorkType workType, String attendType, LocalTime attendTime, LocalTime laeveTime) {
        this.name = name;
        this.workType = workType;
        this.attendType = attendType;
        this.attendTime = attendTime;
        this.laeveTime = laeveTime;
    }
    private WorkTime(String name, WorkType workType, String attendType, LocalTime attendTime, LocalTime laeveTime, LocalTime restTime) {
        this.name = name;
        this.workType = workType;
        this.attendType = attendType;
        this.attendTime = attendTime;
        this.laeveTime = laeveTime;
        this.restTime = restTime;
    }
}
