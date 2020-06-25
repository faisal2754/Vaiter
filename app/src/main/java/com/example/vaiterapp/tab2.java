package com.example.vaiterapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class tab2 extends Fragment {

    private ListView list_view;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab2_customer_main,container,false);





        return rootView;
    }
}
