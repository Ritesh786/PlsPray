package plspray.infoservices.lue.plspray.databind;

/**
 * Created by lue on 06-07-2017.
 */

public class DayTime {
    private String day="";

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time="";

    public DayTime(String day,String time)
    {
        this.day=day;
        this.time=time;
    }
}
