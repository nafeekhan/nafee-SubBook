package com.example.nafeekhan.firstapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.Object;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by nafeekhan on 2018-02-04.
 */

public class Subscription implements Serializable {
    private String name;
    private String dateobj;
    private double monthly;
    private String comment;


    public Subscription(String name, String dateobj, double monthly, @Nullable String comment) {
        this.name = name;
        this.dateobj = dateobj;
        this.monthly = monthly;
        this.name = name;
        this.comment = comment;

    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        } else {
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return dateobj;
    }

    public void setDate(String dateobj) {
        this.dateobj = dateobj;
    }

    public double getMonthly() {
        return this.monthly;
    }

    public void setMonthly(double monthly) {
        this.monthly = monthly;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //@TargetApi(Build.VERSION_CODES.KITKAT)
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Subscription) {
            Subscription s = (Subscription) obj;
            if (s.getName() == null) {
                return false;
            }
            String n = this.name;
            String sn = s.getName();
            return sn.equals(n);
        } else {
            return false;
        }

    }
}

    //public boolean isNull(){

        // try with && this.comment = 'Add'
//        if (this.getName().equals(new String("")) && this.getDate()==0 && this.getMonthly()==0 && this.getComment().equals(new String("null"))) {
  //          return true;
    //    }
      //  return false;
    //}
    //public void nullObject(){
        //Subscription sub = new Subscription(name, dateobj, monthly, comment);
        //this.name = "";
        //this.dateobj = 0;
        //this.monthly = 0;
        //this.comment = "null";

    //}
    //public static ArrayList<Subscription> jsonToSub(String jsonStr){

        /*
        takes a json string(as String object) as argument
        uses gson library to convert string to arraylist of subscrption objects
        return ArrayList of Subscriptions: subbook
        Subscription subscription = new Subscription.nullObject
        ArrayList<Subscription> exampleArray = subscription.jsonToSub(yourString)
        */

        //Gson gson = new Gson();
        //Type subtype = new TypeToken<ArrayList<Subscription>>(){}.getType();
        //ArrayList<Subscription> subbook = gson.fromJson(jsonStr, subtype);
        //return subbook;
    //}
    //public static String subToJson(ArrayList<Subscription> subbook){
        //Subscription subscription = new Subscription.nullObject
        // String exampleString = Subscription.subToJson(yourList)
        //Gson gson = new Gson();
        //Type subtype = new TypeToken<ArrayList<Subscription>>(){}.getType();
        //String jsonStr = gson.toJson(subbook, subtype);
        //return jsonStr;
    //}
    //public String getSummary(){
      //  Date jDate = this.dateobj;
        //Date jDate = new Date(jDate);
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //String reportDate = df.format(jDate);

//        double monthly = this.monthly;
  //      String reportFee;
    //    reportFee = monthly.toString();

      //  String summary = this.name + "|" + reportDate + "|" + reportDate + "|" + this.comment;
        //return summary;

    //}
   // public String getDateStr(){
     //   Date jDate = this.dateobj;
       // Date jDate = new Date(jDate);
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //String DateStr = df.format(jDate);
        //return DateStr;
    //}
//}
