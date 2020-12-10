package me.jaeuk.hr_module.service.overtime;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.overtime.Overtime;
import me.jaeuk.hr_module.domain.work.WorkTime;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Slf4j
@Service
public class OvertimeService {

    public boolean validateOverTimeReq(Overtime reqOvertime, WorkTime workTime){
        // TODO
        return true;
    }

    public Overtime calOverTimeHrs(Overtime reqOvertime, WorkTime workTime){
        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();
        LocalTime reqRestTime = reqOvertime.getRestTime();
        log.info(reqAttendTime + " ~ " + reqLeaveTime +" / "+reqRestTime);
        LocalTime calTime;

        int overMidnight = reqAttendTime.compareTo(reqLeaveTime);
        if(overMidnight == -1){
            log.info("주간야근");
            // TODO 시간 계산(+,-) 함수화
            calTime = reqLeaveTime.minusHours(reqAttendTime.getHour()).minusMinutes(reqAttendTime.getMinute());
        } else if(overMidnight == 1){
            log.info("밤샘");
            calTime = LocalTime.of(24-reqAttendTime.getHour(),0).minusMinutes(reqAttendTime.getMinute());
            calTime = calTime.plusHours(reqLeaveTime.getHour()).plusMinutes(reqLeaveTime.getMinute());
        } else{
            throw new IllegalArgumentException("연장근무 시간을 확인해 주세요." + reqOvertime.getAttendTime() + "~" + reqOvertime.getLeaveTime());
        }
        if(reqRestTime != null){
            calTime = calTime.minusHours(reqRestTime.getHour()).minusMinutes(reqRestTime.getMinute());
        }

        if ("H".equals(workTime.getWorkYn())){
            int holidayOver = LocalTime.of(8,0).compareTo(calTime);
            if(holidayOver == -1){
                reqOvertime.setHolidayOverHrs(calTime.minusHours(8));
                reqOvertime.setHolidayHrs(LocalTime.of(8,0));
            } else{
                reqOvertime.setHolidayHrs(calTime);
            }
        }else {
            reqOvertime.setOverHrs(calTime);
        }
        //TODO nightHrs

        log.info("calTime : " + calTime);
        return reqOvertime;
    }


}
