package com.example.moneytreeapp.db;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
        @TypeConverter
        public static Date toDate(Long dateLong){
            return dateLong == null ? null: new Date(dateLong);
        }

        @TypeConverter
        public static Long fromDate(Date date){
            return date == null ? null : date.getTime();
        }
}
