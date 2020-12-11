package me.jaeuk.hr_module.service.overtime;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.overtime.Overtime;
import me.jaeuk.hr_module.domain.work.WorkTime;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

import static me.jaeuk.hr_module.service.overtime.OvertimeCommon.*;
import static me.jaeuk.hr_module.service.overtime.OvertimeCommon.getMinusTime;

@Slf4j
@Service
public class OvertimeService {

    public boolean validateOverTimeReq(Overtime reqOvertime, WorkTime workTime){
        // TODO
        return true;
    }

    public Overtime calOverTimeHrs(Overtime reqOvertime, WorkTime workTime){
        // TODO refactoring
        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();
        LocalTime reqRestTime = reqOvertime.getRestTime();
        log.info(reqAttendTime + " ~ " + reqLeaveTime +" / "+reqRestTime);
        LocalTime calTime;

        int overMidnight = reqAttendTime.compareTo(reqLeaveTime);
        if(overMidnight == -1){
            log.info("주간야근");
            calTime = getMinusTime(reqLeaveTime, reqAttendTime);
        } else if(overMidnight == 1){
            log.info("밤샘");
            calTime = LocalTime.of(24-reqAttendTime.getHour(),0).minusMinutes(reqAttendTime.getMinute());
            calTime = getPlusTime(calTime, reqLeaveTime);
        } else{
            throw new IllegalArgumentException("연장근무 시간을 확인해 주세요." + reqOvertime.getAttendTime() + "~" + reqOvertime.getLeaveTime());
        }
        if(reqRestTime != null){
            calTime = getMinusTime(calTime, reqRestTime);
        }

        if ("H".equals(workTime.getAttendType())){
            if(isSmaller(MAX_HOLIDAY_HRS, calTime)){
                reqOvertime.setHolidayOverHrs(getMinusTime(calTime, MAX_HOLIDAY_HRS));
                reqOvertime.setHolidayHrs(MAX_HOLIDAY_HRS);
            } else{
                reqOvertime.setHolidayHrs(calTime);
            }
        }else {
            reqOvertime.setOverHrs(calTime);
        }

        LocalTime exceStartTime = reqAttendTime;
        LocalTime exceEndTime = reqLeaveTime;

        if(isBigger(START_NIGHT_HRS, reqAttendTime)){
            exceStartTime = START_NIGHT_HRS;
        }
        if(isSmaller(END_NIGHT_HRS, reqLeaveTime)){
            exceEndTime = END_NIGHT_HRS;
        };

        reqOvertime.setNightHrs(getMinusTime(exceEndTime, exceStartTime));

        log.info("reqOvertime : " + reqOvertime);
        return reqOvertime;
    }


}
