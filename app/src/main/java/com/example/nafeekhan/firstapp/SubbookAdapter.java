package com.example.nafeekhan.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by nafeekhan on 2018-02-04.
 */

public class SubbookAdapter extends ArrayAdapter<Subscription>{
    static class Viewholder {
        TextView btnName;
        TextView dateTxt;
        TextView monthly;
    }

    //private Context con;
    private final int res;
    //private LayoutInflater mInflater;
    private static ArrayList<Subscription> roster;

    //public int getCount(){
        //return roster.size();
    //}
    public SubbookAdapter(Context context, int resource, ArrayList<Subscription> roster){
        super(context, resource, roster);
        //this.con = context;
        this.res = resource;
        this.roster = roster;
        //this.mInflater = (LayoutInflater) LayoutInflater.from(context);
    }
    //@Override
    //public Subscription getItem(int position){
        //return roster.get(position);
    //}
    //public long getItemId(int position) {
        //return position;
    //}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Viewholder holder;
        View v = convertView;
        Subscription s = roster.get(position);
        if (convertView == null){
            //convertView = mInflater.inflate(R.layout.subscription_item, parent,false);
            //convertView = mInflater.inflate(R.layout.subscription_item, null);
            //v = mInflater.inflate(R.layout.subscription_item, null);
            LayoutInflater li = (LayoutInflater) LayoutInflater.from(getContext());
            v = li.inflate(R.layout.subscription_item, parent, false);
            holder = new Viewholder();
            holder.btnName = (TextView)v.findViewById(R.id.btn_name);
            holder.dateTxt = (TextView)v.findViewById(R.id.tv_start_date);
            holder.monthly = (TextView)v.findViewById(R.id.tv_monthly);
            v.setTag(holder);
        }else{
            holder = (Viewholder) v.getTag();
        }

        holder.btnName.setText(s.getName());
        holder.dateTxt.setText(s.getDate());
        holder.monthly.setText(String.valueOf(s.getMonthly()));
        //return convertView;
        return v;
    }

    public void refreshRoster(ArrayList<Subscription> newRoster){
        this.roster.clear();
        this.roster.addAll(newRoster);
        notifyDataSetChanged();
    }

}
