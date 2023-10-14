package com.example.cursach.models;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Validation {
//    public final boolean isValidDate(String pass){
//        private static List<SimpleDateFormat>
//                dateFormats = new ArrayList<SimpleDateFormat>(){
//            {
//                add(new SimpleDateFormat("dd.mm.yyyy"));
//                add(new SimpleDateFormat("dd-mm-yyyy"));
//                add(new SimpleDateFormat("dd.M.yyyy"));
//            }
//        };
//        public static Date stringToDate (String dateInput){
//            if(dateInput == null ) return null;
//
//            Date date = null;
//            for(SimpleDateFormat format : dateFormats){
//                try{
//                    format.setLenient(false);
//                    date = format.parse(dateInput);
//                } catch (ParseException e){}
//                if (date!=null){
//                    break;
//                }
//            }
//
//        }
//        return date;
//    }
    public final static boolean isValidEmail (CharSequence target){
        return TextUtils.isEmpty(target);
    }

    public final static boolean isValidPassword (String pass, String confPass){
        if (!(pass.equals(confPass) )) return true;
        else return false;
    }
}
