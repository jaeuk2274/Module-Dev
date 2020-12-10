package me.jaeuk.hr_module.domain.work;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class Work {
    @Getter
    public enum Type {
        OLD_RESIDENT("구상주", LocalDate.of(2020, 11, 2))
        , RESIDENT("상주", LocalDate.of(2020, 11, 2))
        , RESIDENT_DEFORMATION("상주(변형)", LocalDate.of(2020, 11, 2))
        , DRIVER("운전직", LocalDate.of(2020, 11, 2))
        , TWO_GROUP_TWO_SHIFT("2조2교대", LocalDate.of(2020, 11, 2)) // 주5,휴2,야5,휴2
        , THREE_GROUP_TWO_SHIFT("3조2교대", LocalDate.of(2020, 11, 2))
        , FOUR_GROUP_TWO_SHIFT("4조2교대", LocalDate.of(2020, 11, 2))
        ;

        final private String name;
        private LocalDate startDate;
        private LocalDate endDate;

        private Type(String name, LocalDate startDate){
            this.name = name;
            this.startDate = startDate;
        }
        private Type(String name, LocalDate startDate, LocalDate endDate){
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Getter
    public enum Time {
        TWO_GROUP_TWO_SHIFT_W1("2조2교대 1근", LocalTime.of(07,00), LocalTime.of(15,00))
        , TWO_GROUP_TWO_SHIFT_W2("2조2교대 2근", LocalTime.of(15,00), LocalTime.of(23,00))
        , TWO_GROUP_TWO_SHIFT_H("2조2교대 휴무")
        ;

        final private String name;
        private LocalTime attendTime;
        private LocalTime laeveTime;

        private Time(String name){
            this.name = name;
            System.out.println("name : " + name);
        }
        private Time(String name, LocalTime attendTime, LocalTime laeveTime){
            this.name = name;
            this.attendTime = attendTime;
            this.laeveTime = laeveTime;
        }
    }

    @Getter
    public enum Shift {
        TWO_GROUP_TWO_SHIFT_A("2조2교대 A조", Work.Type.TWO_GROUP_TWO_SHIFT)
        , TWO_GROUP_TWO_SHIFT_B("2조2교대 B조", Work.Type.TWO_GROUP_TWO_SHIFT)
        ;

        final private String name;
        final private Type type;

        private Shift(String name, Type type){
            this.name = name;
            this.type = type;
        }
    }
}