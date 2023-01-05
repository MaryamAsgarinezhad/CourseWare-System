package Logic;

import java.time.DayOfWeek;

public class date {
    public int year;
    public int mounth;
    public int dayinMounth;
    public int hour;
    public int minute;
    public DayOfWeek day;
    public DayOfWeek[] days;


    public date(){

    }

    public String priority() {
        String s1 = "";
        String s2 = "" + hour;
        String s3 = "" + minute;

        if (day.toString().equals("Saturday")) {
            s1 = "" + 0;
        }
        if (day.toString().equals("Sunday")) {
            s1 = "" + 1;
        }
        if (day.toString().equals("Monday")) {
            s1 = "" + 2;
        }
        if (day.toString().equals("Tuesday")) {
            s1 = "" + 3;
        }
        if (day.toString().equals("Wensday")) {
            s1 = "" + 4;
        }
        if (day.toString().equals("Thursday")) {
            s1 = "" + 5;
        }
        if (day.toString().equals("Friday")) {
            s1 = "" + 6;
        }
        return s1+s2+s3;
    }

    public String toString1(){
        if(minute==0){
            return day.toString() +","+ year+ "/" + mounth + "/" + dayinMounth + " at " + hour + ":" + "00";
        }
      return day.toString() +","+ year+ "/" + mounth + "/" + dayinMounth+  " at " + hour + ":" + minute;
    }

    public String toString2(){
        if(minute==0){
            return days[0].toString() + " and " +days[1].toString() + " at " + hour + ":" + "00";
        }
        return days[0].toString() + " and " +days[1].toString() + " at " + hour + ":" + minute;
    }

    public date(int hour, int minute, DayOfWeek[] days) {
        this.hour = hour;
        this.minute = minute;
        this.days = days;
    }

    public date(int year, int mounth, int dayinMounth, int hour, int minute, DayOfWeek dayOfWeek) {
        this.year=year;
        this.mounth=mounth;
        this.dayinMounth=dayinMounth;
        this.hour = hour;
        this.minute = minute;
        this.day = dayOfWeek;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMounth(int mounth) {
        this.mounth = mounth;
    }

    public void setDayinMounth(int dayinMounth) {
        this.dayinMounth = dayinMounth;
    }

    public int getYear() {
        return year;
    }

    public int getMounth() {
        return mounth;
    }

    public int getDayinMounth() {
        return dayinMounth;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public void setDays(DayOfWeek[] days) {
        this.days = days;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public DayOfWeek[] getDays() {
        return days;
    }
}
