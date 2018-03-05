package com.ican.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Date date;
    private static Calendar calendar = Calendar.getInstance();

    static {
        date = new Date();

    }

    public static String getCurrentYM() {
        String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4);
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        return year + month;
    }

    /*public static void main(String[] args) {
        //System.out.println(String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4) + String.valueOf(calendar.get(Calendar.MONTH) + 1));
        //DateUtil dateUtil = new DateUtil();
        //System.out.println(dateUtil.getCurrentYM());
    }*/

}
