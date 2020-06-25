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

public class ListAdapterTab2 extends BaseAdapter{
    tab2 context;
    private final ArrayList<String> meals;
    private final ArrayList<String> dateTime;
    private final ArrayList<Integer> images;

    public ListAdapterTab2(tab2 context, ArrayList<String> meals, ArrayList<String> dateTime, ArrayList<Integer> images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.meals = meals;
        this.dateTime = dateTime;
        this.images = images;
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
            convertView = inflater.inflate(R.layout.double_list_item, parent, false);
            viewHolder.meal = (TextView) convertView.findViewById(R.id.txtMeal);
            viewHolder.dtime = (TextView) convertView.findViewById(R.id.txtDateTime);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.tab2icon);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.meal.setText(meals.get(position));
        viewHolder.dtime.setText(dateTime.get(position));
        viewHolder.icon.setImageResource(images.get(position));



        return result;
    }

    private static class ViewHolder {

        TextView meal;
        TextView dtime;
        ImageView icon;

    }
}
