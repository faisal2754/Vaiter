package com.example.vaiterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapterStaffTab2 extends BaseAdapter{
    StaffTab2 context;
    private final ArrayList<String> meals;
    private final ArrayList<String> names;
    private final ArrayList<String> dateTime;

    public ListAdapterStaffTab2(StaffTab2 context, ArrayList<String> meals, ArrayList<String> names, ArrayList<String> dateTime){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.meals = meals;
        this.names = names;
        this.dateTime = dateTime;
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.staff_list_item, parent, false);
            viewHolder.meal = (TextView) convertView.findViewById(R.id.tvMeal);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvCus);
            viewHolder.dTime = (TextView) convertView.findViewById(R.id.tvDateTime);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.meal.setText(meals.get(position));
        viewHolder.name.setText(names.get(position));
        viewHolder.dTime.setText(dateTime.get(position));



        return result;
    }

    private static class ViewHolder {

        TextView meal;
        TextView name;
        TextView dTime;

    }
}

