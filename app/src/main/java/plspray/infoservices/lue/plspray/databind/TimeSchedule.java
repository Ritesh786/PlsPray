package plspray.infoservices.lue.plspray.databind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lue on 07-07-2017.
 */

public class TimeSchedule implements Parcelable {
    protected TimeSchedule(Parcel in) {
        mondayTime = in.readString();
        tuesDayTime = in.readString();
        wednesDayTime = in.readString();
        thursDayTime = in.readString();
        friDayTime = in.readString();
        saturDayTime = in.readString();
        sunDayTime = in.readString();
    }

    public TimeSchedule(){}

    public static final Creator<TimeSchedule> CREATOR = new Creator<TimeSchedule>() {
        @Override
        public TimeSchedule createFromParcel(Parcel in) {
            return new TimeSchedule(in);
        }

        @Override
        public TimeSchedule[] newArray(int size) {
            return new TimeSchedule[size];
        }
    };

    public String getMondayTime() {
        return mondayTime;
    }

    public void setMondayTime(String mondayTime) {
        this.mondayTime = mondayTime;
    }

    public String getTuesDayTime() {
        return tuesDayTime;
    }

    public void setTuesDayTime(String tuesDayTime) {
        this.tuesDayTime = tuesDayTime;
    }

    public String getWednesDayTime() {
        return wednesDayTime;
    }

    public void setWednesDayTime(String wednesDayTime) {
        this.wednesDayTime = wednesDayTime;
    }

    public String getThursDayTime() {
        return thursDayTime;
    }

    public void setThursDayTime(String thursDayTime) {
        this.thursDayTime = thursDayTime;
    }

    public String getFriDayTime() {
        return friDayTime;
    }

    public void setFriDayTime(String friDayTime) {
        this.friDayTime = friDayTime;
    }

    public String getSaturDayTime() {
        return saturDayTime;
    }

    public void setSaturDayTime(String saturDayTime) {
        this.saturDayTime = saturDayTime;
    }

    public String getSunDayTime() {
        return sunDayTime;
    }

    public void setSunDayTime(String sunDayTime) {
        this.sunDayTime = sunDayTime;
    }

    String mondayTime="";
    String tuesDayTime="";
    String wednesDayTime="";
    String thursDayTime="";
    String friDayTime="";
    String saturDayTime="";
    String sunDayTime="";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mondayTime);
        dest.writeString(tuesDayTime);
        dest.writeString(wednesDayTime);
        dest.writeString(thursDayTime);
        dest.writeString(friDayTime);
        dest.writeString(saturDayTime);
        dest.writeString(sunDayTime);
    }
}
