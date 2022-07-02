package com.example.weatherapp.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
    private static final String SPACE = " ";
    private static final String SLASH = "/";
    private static final String DATE_FORMAT_1 = "dd/MM/yyyy";
    private static final String DATE_FORMAT_2 = "EEEE";
    private static final String EMPTY_STRING = "";
    private static final String AM = "AM";
    private static final String PM = "PM";

    public static String jsonDateTimeToAppDateTime(String jsonDateTime) throws ParseException {
        String dayOfWeek = jsonDateToDayOfWeek(jsonDateTime.substring(0,10));
        String time = jsonTimeToSimplerTime(jsonDateTime.substring(11));
        String result = dayOfWeek + SPACE + time;
        return result;
    }

    private static String jsonDateToDayOfWeek(String jsonDate) throws ParseException {
        String year = jsonDate.substring(0, 4);
        String month = jsonDate.substring(5, 7);
        String date = jsonDate.substring(8, 10);
        String inputDateString = date + SLASH + month + SLASH + year;
        Date inputDate = (new SimpleDateFormat(DATE_FORMAT_1)).parse(inputDateString);
        String result = (new SimpleDateFormat(DATE_FORMAT_2)).format(inputDate);
        return result;
    }

    private static String jsonTimeToSimplerTime(String jsonTime) {
        Integer hour = Integer.parseInt(jsonTime.substring(0, 2));
        if (hour == 0) {
            return "12" + SPACE + AM;
        }
        if (hour == 12) {
            return "12" + SPACE + PM;
        }
        String result = EMPTY_STRING;
        if (hour < 12) {
            result = jsonTime.substring(1,2) + SPACE + AM;
        }
        else {
            result = EMPTY_STRING + (hour-12) + SPACE + PM;
        }
        return result;
    }

}
