package com.example.weatherapp.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {

    public static String jsonDateTimeToAppDateTime(String jsonDateTime) throws ParseException {
        String dayOfWeek = jsonDateToDayOfWeek(jsonDateTime.substring(0,10));
        String time = jsonTimeToSimplerTime(jsonDateTime.substring(11));
        String result = dayOfWeek + " " + time;
        return result;
    }

    private static String jsonDateToDayOfWeek(String jsonDate) throws ParseException {
        String year = jsonDate.substring(0, 4);
        String month = jsonDate.substring(5, 7);
        String date = jsonDate.substring(8, 10);
        String inputDateString = date + "/" + month + "/" + year;
        Date inputDate = (new SimpleDateFormat("dd/MM/yyyy")).parse(inputDateString);
        String result = (new SimpleDateFormat("EEEE")).format(inputDate);
        return result;
    }

    private static String jsonTimeToSimplerTime(String jsonTime) {
        String hour = jsonTime.substring(0, 2);
        if (hour.equals("00")) {
            return "12AM";
        }
        String firstNumOfHour = jsonTime.substring(0,1);
        String result = "";
        if (firstNumOfHour.equals("0")) {
            result = jsonTime.substring(1,2) + " AM";
        }
        else {
            result = "" + (Integer.parseInt(hour)-12) + " PM";
        }
        return result;
    }

}
