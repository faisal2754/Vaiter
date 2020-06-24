package com.example.vaiterapp;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaiterapp.API.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tab1 extends Fragment implements View.OnClickListener {

    /*private List<Item> itemList = new ArrayList<Item>();
    private List<Item> itemListMenu = new ArrayList<Item>();
    private List<Item> List_item_menu = new ArrayList<Item>();*/


    TimePickerDialog picker;
    DatePickerDialog Datepicker;
    EditText TimeText;
    EditText DateText;

    private ListView list_view;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;
    private Button btnOrder;
    private boolean menuList = false;

    int[] rImages = {R.drawable.ob, R.drawable.mcdonalds};
    String[] rNames = {"Ocean Basket", "McDonalds"};
    ListAdapter listAdapter;



    /*private RecyclerView recyclerview, recyclerview_menu;
    private MyAdapter rAdapter,menuAdapter;
    private MyAdapterMenu menuAdapter2;*/

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {
                list_view.setVisibility(View.GONE);
                btnOrder.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "wtf lol", Toast.LENGTH_LONG).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_customer_main, container, false);
        /*TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        tv.setText("WHATSUP");
        tv.setTextColor(Color.RED);*/
        btnOrder = rootView.findViewById(R.id.btnOrder);


//        TimeText=(EditText) rootView.findViewById(R.id.in_time);
//        TimeText.setInputType(InputType.TYPE_NULL);
//        TimeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int hour = cldr.get(Calendar.HOUR_OF_DAY);
//                int minutes = cldr.get(Calendar.MINUTE);
//                // time picker dialog
//                picker = new TimePickerDialog(getContext(),
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
//                                TimeText.setText(sHour + ":" + sMinute);
//                            }
//                        }, hour, minutes, true);
//                picker.show();
//            }
//        });
//
//        DateText=(EditText) rootView.findViewById(R.id.in_date);
//        DateText.setInputType(InputType.TYPE_NULL);
//        DateText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
//                int day = cldr.get(Calendar.DAY_OF_MONTH);
//                int month = cldr.get(Calendar.MONTH);
//                int year = cldr.get(Calendar.YEAR);
//                // date picker dialog
//                Datepicker = new DatePickerDialog(getContext(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                DateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                Datepicker.show();
//            }
//        });



        /*recyclerview=(RecyclerView) rootView.findViewById(R.id.recycler_view);
        rAdapter = new MyAdapter(itemList);
        menuAdapter = new MyAdapter(itemListMenu);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(rAdapter);

        recyclerview_menu=(RecyclerView) rootView.findViewById(R.id.recycler_view_menu);
        menuAdapter2 = new MyAdapterMenu(itemListMenu);
        RecyclerView.LayoutManager menuLayoutManger = new LinearLayoutManager(getContext());
        recyclerview_menu.setLayoutManager(menuLayoutManger);
        recyclerview_menu.setItemAnimator(new DefaultItemAnimator());
        recyclerview_menu.setAdapter(menuAdapter2);

        itemList.clear();
        Item OB = new Item(R.drawable.ob,"Ocean Basket","Fancy Fish restaurant");
        Item McD = new Item(R.drawable.mcdonalds2,"McDonalds","Burgers");
        itemList.add(OB);
        itemList.add(McD);

        Item lol = new Item(R.drawable.blackblue,"Ocean Basket","Fancy Fish restaurant");
        itemListMenu.add(lol);
        menuAdapter.notifyDataSetChanged();*/

        /*recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Log.e("lol", "Short press on position :" + position);

                *//*recyclerview_menu.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);*//*
            }
        }));*/

        list_view = rootView.findViewById(R.id.list_view);
        listAdapter = new ListAdapter(this, rNames, rImages);
        list_view.setAdapter(listAdapter);

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!menuList){
                        Toast.makeText(getActivity(), ""+adapterView.getItemIdAtPosition(i), Toast.LENGTH_SHORT).show();
                        list_view.setAdapter(adapter);
                        getMeals(rNames[i]);
                        setMenuBool();
                    } else {
                        Toast.makeText(getActivity(), ""+adapterView.getItemIdAtPosition(i), Toast.LENGTH_SHORT).show();
                    }


                    //Toast.makeText(getActivity(), "" + menuList, Toast.LENGTH_SHORT).show();
                }
            });





        //list_view.setBackgroundResource(R.drawable.customshape);
        listItems = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                listItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the current item from ListView
                View view = super.getView(position, convertView, parent);

                // Get the Layout Parameters for ListView Current Item View
                ViewGroup.LayoutParams params = view.getLayoutParams();

                // Set the height of the Item View
                params.height = 250;
                view.setLayoutParams(params);

                return view;
            }
        };

        //list_view.setAdapter(adapter);


        /*btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_view.setVisibility(View.VISIBLE);
                btnOrder.setVisibility(View.GONE);
            }
        });*/

        /*if (menuList){
            list_view.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
            });
        }*/


        return rootView;
    }

    /*@Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOrder) {
            list_view.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.GONE);
        }
    }*/

    private void setMenuBool(){
        menuList = !menuList;
    }

    void getMeals(String rname){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getMeals(rname);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    jsonDecode(s);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void jsonDecode(String s) throws JSONException {
        JSONObject js = new JSONObject(s);
        if (!js.getBoolean("error")){
            JSONArray jArr = js.getJSONArray("message");

            for(int i=0;i<jArr.length();i++){
                String curr = jArr.getString(i);
                //Toast.makeText(getActivity(), curr, Toast.LENGTH_LONG).show();
                addMenuItem(curr);
            }
        }
    }

    private void addMenuItem(String item){
        listItems.add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
