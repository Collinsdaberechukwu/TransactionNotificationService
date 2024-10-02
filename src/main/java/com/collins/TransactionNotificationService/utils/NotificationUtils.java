package com.collins.TransactionNotificationService.utils;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationUtils {

    public static Date dateFormatter(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateStr);
    }

    public static Gson getgson(){
        return new Gson();
    }
}
