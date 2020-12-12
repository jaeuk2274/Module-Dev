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
        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();

        if(isEqual(reqAttendTime, reqLeaveTime)){
            throw new OverTimeValidateException("연장근로 시작시간과 종료시간이 같습니다." + getMsg(reqOvertime, workTime));
        }

        AttendType attendType = workTime.getAttendType();
        if(isHoliday(attendType)){
            return true;
        }
        else {
            if(isSmaller(reqAttendTime,NOON)){
                if(isBigger(reqLeaveTime, workTime.getAttendTime())){
                    throw new OverTimeValidateException("연장근로 종료시간이 근무 시작시간보다 늦습니다."+ getMsg(reqOvertime, workTime));
                }
            } else{
                if(isSmaller(reqAttendTime, workTime.getLaeveTime())){
                    throw new OverTimeValidateException("연장근로 시작시간이 근무 종료시간보다 빠릅니다."+ getMsg(reqOvertime, workTime));
                }
            }
        }
        return true;
    }

    public Overtime calOverTimeHrs(Overtime reqOvertime, WorkTime workTime){
        validateOverTimeReq(reqOvertime, workTime);

        LocalTime calTime = getCalOverTimeHrs(reqOvertime);
        AttendType attendType = workTime.getAttendType();

        LocalTime overHrs = getOverHrs(attendType, calTime);
        LocalTime holidayHrs = getHolidayHrs(attendType, calTime);
        LocalTime holidayOverHrs = getHolidayOverHrs(attendType, calTime);
        LocalTime nightHrs = getNightHrs(reqOvertime);

        reqOvertime.setHolidayHrs(holidayHrs);
        reqOvertime.setHolidayOverHrs(holidayOverHrs);
        reqOvertime.setOverHrs(overHrs);
        reqOvertime.setNightHrs(nightHrs);

        return reqOvertime;
    }

    private LocalTime getCalOverTimeHrs(Overtime reqOvertime) {
        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();
        LocalTime reqRestTime = reqOvertime.getRestTime();
        LocalTime calTime;
        if (isOverMidnight(reqLeaveTime)) {
            calTime = getMinusTime(reqLeaveTime, reqAttendTime);
        } else {
            calTime = LocalTime.of(24 - reqAttendTime.getHour(), 0).minusMinutes(reqAttendTime.getMinute());
            calTime = getPlusTime(calTime, reqLeaveTime);
        }
        if (isBigger(reqRestTime, calTime)){
            throw new OverTimeValidateException("휴게시간이 연장근로시간보다 많습니다." + getMsg(reqOvertime));
        }
        calTime = getMinusTime(calTime, reqRestTime);
        return calTime;
    }

    private LocalTime getOverHrs(AttendType attendType, LocalTime calTime) {
        if (isHoliday(attendType)){
            return LocalTime.of(0,0);
        } else {
            return  calTime;
        }
    }

    private LocalTime getHolidayHrs(AttendType attendType, LocalTime calTime) {
        if (isHoliday(attendType)){
            return isBigger(calTime, MAX_HOLIDAY_HRS) ? MAX_HOLIDAY_HRS : calTime;
        } else {
            return  LocalTime.of(0,0);
        }
    }

    private LocalTime getHolidayOverHrs(AttendType attendType, LocalTime calTime) {
        if (isHoliday(attendType)){
            return isBigger(calTime, MAX_HOLIDAY_HRS) ? getMinusTime(calTime,MAX_HOLIDAY_HRS) : LocalTime.of(0,0);
        } else {
            return  LocalTime.of(0,0);
        }
    }

    private LocalTime getNightHrs(Overtime reqOvertime) {
        LocalTime reqAttendTime = reqOvertime.getAttendTime();
        LocalTime reqLeaveTime = reqOvertime.getLeaveTime();

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

        LocalTime nightHrs = getMinusTime(applyEndTime, applyStartTime);
        return nightHrs;
    }

    private boolean isOverMidnight(LocalTime reqLeaveTime) {
        int overMidnight = reqLeaveTime.compareTo(MIDNIGHT);
        if(overMidnight <= 0){
            return false;
        } else{
            return true;
        }
    }

    private String getMsg(Overtime reqOvertime) {
        return " 신청(" + reqOvertime.getAttendTime() + "~" + reqOvertime.getLeaveTime() + ",휴게: " + reqOvertime.getRestTime() + ")";
    }
    private String getMsg(Overtime reqOvertime, WorkTime workTime) {
        return " 신청(" + reqOvertime.getAttendTime() + "~" + reqOvertime.getLeaveTime() + ")/근무(" + workTime.getAttendTime() + "~" + workTime.getLaeveTime() + ")";
    }
}
