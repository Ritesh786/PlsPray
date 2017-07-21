package plspray.infoservices.lue.plspray.databind;

import java.util.List;

/**
 * Created by lue on 06-07-2017.
 */

public class Schedule {
    public List<DayTime> getDayTimeList() {
        return dayTimeList;
    }

    public void setDayTimeList(List<DayTime> dayTimeList,String time)
    {
        this.dayTimeList = dayTimeList;
        this.time=time;
    }

    private List<DayTime> dayTimeList;

    public void setDayTimeList(List<DayTime> dayTimeList) {
        this.dayTimeList = dayTimeList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time="";

}
