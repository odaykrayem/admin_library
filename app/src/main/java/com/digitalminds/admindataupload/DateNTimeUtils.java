package com.digitalminds.admindataupload;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateNTimeUtils {

    public static String getTodayDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("M/dd/yy");
        String formattedDate = df.format(c);
        return formattedDate;
    }
    public static String getYesterdayDate(){
        String yesterdayDate=null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        //format date
        SimpleDateFormat df = new SimpleDateFormat("M/dd/yy");
        yesterdayDate = df.format(cal.getTime());

        return yesterdayDate;
    }

}
