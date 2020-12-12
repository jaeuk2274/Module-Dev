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
        LocalTime calTime = getMinusTime(reqLeaveTime, reqAttendTime);
        if(isBigger(calTime, MAX_OVERTIME_HRS)){
            throw new OverTimeValidateException(MAX_OVERTIME_HRS + " 이상 연장근로 하실 수 없습니다." + getMsg(reqOvertime, workTime));
        }

        AttendType attendType = workTime.getAttendType();
        if(isHoliday(attendType)){
            return true;
        }

        if(isSmaller(reqAttendTime, workTime.getAttendTime())){
            if(isBigger(reqLeaveTime, workTime.getAttendTime())){
                throw new OverTimeValidateException("연장근로 종료시간이 근무 시작시간보다 늦습니다."+ getMsg(reqOvertime, workTime));
            }
        } else{
            if(isSmaller(reqAttendTime, workTime.getLaeveTime())){
                throw new OverTimeValidateException("연장근로 시작시간이 근무 종료시간보다 빠릅니다."+ getMsg(reqOvertime, workTime));
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

        calTime = getMinusTime(reqLeaveTime, reqAttendTime);

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

        if(notNightAttendSection(reqAttendTime) || notNightLeaveSection(reqLeaveTime)){
            return LocalTime.of(0,0);
        }

        if(isEqualOrBigger(reqAttendTime, MIDNIGHT)) {
            if(isSmaller(reqAttendTime, END_NIGHT_HRS)){
                LocalTime applyLeaveTime = getApplyLeaveTime(reqLeaveTime);
                return getMinusTime(applyLeaveTime, reqAttendTime);
            }
            if(isBigger(reqLeaveTime, START_NIGHT_HRS) || isSmaller(reqLeaveTime, END_NIGHT_HRS)){
                LocalTime applyAttendTime = getApplyAttendTime(reqAttendTime);
                return getMinusTime(reqLeaveTime, applyAttendTime);
            }
            if(isEqualOrBigger(reqAttendTime, getMinusTime(reqLeaveTime,MAX_OVERTIME_HRS)) && isEqualOrBigger(reqLeaveTime, END_NIGHT_HRS)){
                LocalTime applyAttendTime = getApplyAttendTime(reqAttendTime);
                LocalTime applyLeaveTime = getApplyLeaveTime(reqLeaveTime);
                return getMinusTime(applyLeaveTime, applyAttendTime);
            }
        }

        if(isEqualOrBigger(reqLeaveTime, MIDNIGHT)){
            if(isEqualOrSmaller(reqLeaveTime, END_NIGHT_HRS)){
                LocalTime applyAttendTime = getApplyAttendTime(reqAttendTime);
                return getMinusTime(reqLeaveTime, applyAttendTime);
            }
        }
        return LocalTime.of(0,0);
    }

    private LocalTime getApplyAttendTime(LocalTime reqAttendTime) {
        return isBigger(reqAttendTime, START_NIGHT_HRS) ? reqAttendTime : START_NIGHT_HRS;
    }
    private LocalTime getApplyLeaveTime(LocalTime reqLeaveTime) {
        return isSmaller(reqLeaveTime, END_NIGHT_HRS) ? reqLeaveTime : END_NIGHT_HRS;
    }
    private boolean notNightAttendSection(LocalTime reqAttendTime) {
        return isEqualOrBigger(END_NIGHT_HRS, reqAttendTime) && isEqualOrSmaller(getMinusTime(START_NIGHT_HRS, MAX_OVERTIME_HRS), reqAttendTime);
    }
    private boolean notNightLeaveSection(LocalTime reqLeaveTime) {
        return isEqualOrBigger(reqLeaveTime,getPlusTime(END_NIGHT_HRS, MAX_OVERTIME_HRS)) && isEqualOrSmaller(reqLeaveTime, START_NIGHT_HRS);
    }
    private String getMsg(Overtime reqOvertime) {
        return " 신청(" + reqOvertime.getAttendTime() + "~" + reqOvertime.getLeaveTime() + ",휴게: " + reqOvertime.getRestTime() + ")";
    }
    private String getMsg(Overtime reqOvertime, WorkTime workTime) {
        return " 신청(" + reqOvertime.getAttendTime() + "~" + reqOvertime.getLeaveTime() + ")/근무(" + workTime.getAttendTime() + "~" + workTime.getLaeveTime() + ")";
    }
}
