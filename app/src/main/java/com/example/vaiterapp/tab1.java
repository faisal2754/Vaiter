package com.example.vaiterapp;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class tab1 extends Fragment {
    private View Tab1View;
    private ListView list_view;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab1_customer_main,container,false);

        /*TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        tv.setText("WHATSUP");
        tv.setTextColor(Color.RED);*/

        list_view = rootView.findViewById(R.id.list_view);

        listItems=new ArrayList<String>();

        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listItems);

        list_view.setAdapter(adapter);

        listItems.add("wtf");
        listItems.add("lol");

        return rootView;
    }



}
