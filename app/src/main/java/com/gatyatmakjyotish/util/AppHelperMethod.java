package com.gatyatmakjyotish.util;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.ResolverStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppHelperMethod {

    public static boolean isTimeValid(String inputTimeString){
        String regex = "\\b((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(inputTimeString);
        return matcher.matches();
    }


    public static boolean isDateValid(String inputDateString){
        String regex = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(inputDateString);
        return matcher.matches();
    }

    public static String digitPdding(int digit){
        if (digit<=9 && digit >= 0){
            return "0"+digit;
        }
        return ""+digit;
    }




}
