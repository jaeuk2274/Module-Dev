package me.jaeuk.hr_module.service.overtime;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.common.exception.OverTimeValidateException;
import me.jaeuk.hr_module.domain.overtime.Overtime;
import me.jaeuk.hr_module.domain.work.AttendType;
import me.jaeuk.hr_module.domain.work.WorkTime;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import static me.jaeuk.hr_module.service.overtime.OverTimeCommon.*;
import static me.jaeuk.hr_module.service.overtime.OverTimeCommon.getMinusTime;

@Slf4j
@Service
public class OvertimeService {

    public boolean validateOverTimeReq(Overtime reqOvertime, WorkTime workTime){
        // TODO refactoring
        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();
        LocalTime reqRestTime = reqOvertime.getRestTime();
        String msg = " 신청(" + reqAttendTime + "~" + reqLeaveTime + ")/근무(" + workTime.getAttendTime() + "~" + workTime.getLaeveTime() + ")";

        if(isEqual(reqAttendTime, reqLeaveTime)){
            throw new OverTimeValidateException("연장근로 시작시간과 종료시간이 같습니다." + msg);
        }
        LocalTime calTime = getMinusTime(reqLeaveTime, reqAttendTime);
        LocalTime restTime = reqOvertime.getRestTime();
        if (isBigger(restTime, calTime)){
            throw new OverTimeValidateException("휴게시간이 연장근로시간보다 많습니다."+ msg);
        }

        AttendType attendType = workTime.getAttendType();
        if(isHoliday(attendType)){
            return true;
        }
        else {
            if(isSmaller(reqAttendTime,NOON)){
                if(isBigger(reqLeaveTime, workTime.getAttendTime())){
                    throw new OverTimeValidateException("연장근로 종료시간이 근무 시작시간보다 늦습니다."+ msg);
                }
            } else{
                if(isSmaller(reqAttendTime, workTime.getLaeveTime())){
                    throw new OverTimeValidateException("연장근로 시작시간이 근무 종료시간보다 빠릅니다."+ msg);
                }
            }
        }
        return true;
    }



    public Overtime calOverTimeHrs(Overtime reqOvertime, WorkTime workTime){
        // TODO refactoring
        validateOverTimeReq(reqOvertime, workTime);

        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();
        LocalTime reqRestTime = reqOvertime.getRestTime();
        LocalTime calTime;

        if(isOverMidnight(reqLeaveTime)){
            calTime = getMinusTime(reqLeaveTime, reqAttendTime);
        } else{
            calTime = LocalTime.of(24-reqAttendTime.getHour(),0).minusMinutes(reqAttendTime.getMinute());
            calTime = getPlusTime(calTime, reqLeaveTime);
        }
        calTime = reqRestTime == null ? calTime : getMinusTime(calTime, reqRestTime);

        LocalTime overHrs = LocalTime.of(0,0);
        LocalTime holidayHrs = LocalTime.of(0,0);
        LocalTime holidayOverHrs = LocalTime.of(0,0);
        LocalTime nightHrs = LocalTime.of(0,0);

        AttendType attendType = workTime.getAttendType();
        if (isHoliday(attendType)){
            holidayHrs = isBigger(calTime, MAX_HOLIDAY_HRS) ? MAX_HOLIDAY_HRS : calTime;
            holidayOverHrs = isBigger(calTime, MAX_HOLIDAY_HRS) ? getMinusTime(calTime,MAX_HOLIDAY_HRS) : LocalTime.of(0,0);
        }else {
            overHrs = calTime;
        }

        LocalTime applyStartTime;
        if(isSmaller(reqAttendTime, NOON)){
            applyStartTime = reqAttendTime;
        } else{
            applyStartTime = isBigger(reqAttendTime, START_NIGHT_HRS) ? reqAttendTime : START_NIGHT_HRS;
        }

        LocalTime applyEndTime;
        if(isBigger(reqLeaveTime, NOON)){
            applyEndTime = reqLeaveTime;
        } else{
            applyEndTime = isSmaller(reqLeaveTime, END_NIGHT_HRS) ? reqLeaveTime : END_NIGHT_HRS;
        }
        nightHrs = getMinusTime(applyEndTime, applyStartTime);

        reqOvertime.setHolidayHrs(holidayHrs);
        reqOvertime.setHolidayOverHrs(holidayOverHrs);
        reqOvertime.setOverHrs(overHrs);
        reqOvertime.setNightHrs(nightHrs);

        return reqOvertime;
    }

    private boolean isOverMidnight(LocalTime reqLeaveTime) {
        int overMidnight = reqLeaveTime.compareTo(MIDNIGHT);
        if(overMidnight <= 0){
            return false;
        } else{
            return true;
        }
    }


}
