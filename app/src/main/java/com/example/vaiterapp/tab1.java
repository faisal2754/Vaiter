package com.example.vaiterapp;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.vaiterapp.API.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import android.widget.DatePicker;
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
    Button confirmOrder;

    private int currID = MainActivity.prf1.getInt("userID", 27);
    private String cussName = MainActivity.prf1.getString("fname", "");

    private String mealChosen;
    private String dateChosen;
    private String timeChosen;
    private String dateTime;


    private ListView list_view;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;
    private Button btnOrder;
    private boolean menuList = false;

    private TextView tvHeading;
    private TextView tvWelcome;
    private ImageView LogoCuss;


    int[] rImages = {R.drawable.ob, R.drawable.mcdonalds, R.drawable.pizzahut, R.drawable.lalqila, R.drawable.nandos};
    String[] rNames = {"Ocean Basket", "McDonalds", "Pizza Hut", "Lal Qila", "Nandos"};
    ListAdapterTab1 listAdapterTab1;

    private FloatingActionButton cancel;
    private ImageView ResLogo;

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

        //sp = getSharedPreferences("user_details",MODE_PRIVATE);
        /*TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        tv.setText("WHATSUP");
        tv.setTextColor(Color.RED);*/
        btnOrder = rootView.findViewById(R.id.btnOrder);

        tvHeading = (TextView) rootView.findViewById(R.id.heading_view);
        LogoCuss = (ImageView) rootView.findViewById(R.id.logo_cuss_main);
        tvWelcome = (TextView) rootView.findViewById(R.id.welcome_view);

        cancel = (FloatingActionButton) rootView.findViewById(R.id.cancelOrder);
        ResLogo = (ImageView) rootView.findViewById(R.id.res_logo);

        tvWelcome.setText("Welcome, " + cussName);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment();
                Toast.makeText(getActivity(), "Order cancelled", Toast.LENGTH_SHORT).show();
                cancel.setVisibility(View.GONE);
            }
        });

        confirmOrder = rootView.findViewById(R.id.btnConfirmOrder);
        confirmOrder.setVisibility(View.GONE);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateChosen == null || timeChosen == null){
                    Toast.makeText(getActivity(), "Please choose both a date and time", Toast.LENGTH_LONG).show();
                } else {
                    dateTime = dateChosen + " " + timeChosen;
                    createOrder(mealChosen, dateTime, currID);

                    OrderDialog orderDialog = new OrderDialog();
                    orderDialog.show(getChildFragmentManager(), "order dialog");
                    orderDialog.setCancelable(false);
                    }
            }
        });

        TimeText=(EditText) rootView.findViewById(R.id.in_time);
        TimeText.setInputType(InputType.TYPE_NULL);
        TimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String sH = Integer.toString(sHour);
                                String sM = Integer.toString(sMinute);
                                if(sH.length() == 1){
                                    sH = "0"+sH;
                                }
                                if(sM.length() == 1){
                                    sM = "0"+sM;
                                }
                                TimeText.setText(sH + ":" + sM);
                                timeChosen = sH + ":" + sM;
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        DateText=(EditText) rootView.findViewById(R.id.in_date);
        DateText.setInputType(InputType.TYPE_NULL);
        DateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                Datepicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String sMonth = Integer.toString(monthOfYear+1);
                                String sDay = Integer.toString(dayOfMonth);
                                String sYear = Integer.toString(year);
                                if(sMonth.length() == 1){
                                    sMonth = "0"+sMonth;
                                }
                                if(sDay.length() == 1){
                                    sDay = "0"+sDay;
                                }
                                DateText.setText(sYear + "/" + (sMonth) + "/" + sDay);
                                dateChosen = sYear + "/" + (sMonth) + "/" + sDay;
                            }
                        }, year, month, day);
                Datepicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                Datepicker.show();
            }
        });



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
        listAdapterTab1 = new ListAdapterTab1(this, rNames, rImages);
        list_view.setAdapter(listAdapterTab1);

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (!menuList){
                        Toast.makeText(getActivity(), ""+rNames[i], Toast.LENGTH_SHORT).show();
                        tvHeading.setText("What would you like to eat?");

                        list_view.setAdapter(adapter);
                        getMeals(rNames[i]);
                        if(rNames[i].equals("Ocean Basket")){
                            ResLogo.setImageResource(R.drawable.ob3);

                        }
                        else if(rNames[i].equals("McDonalds")){
                            ResLogo.setImageResource(R.drawable.mcdonalds3);

                        }
                        else if(rNames[i].equals("Pizza Hut")){
                            ResLogo.setImageResource(R.drawable.pizzahut);

                        }
                        else if(rNames[i].equals("Lal Qila")){
                            ResLogo.setImageResource(R.drawable.lalqila);

                        }
                        else if(rNames[i].equals("Nandos")){
                            ResLogo.setImageResource(R.drawable.nandos);

                        }

                        setMenuBool();
                    }
                    else {
                        setMenuBool();
                        mealChosen = (String) adapterView.getItemAtPosition(i);
                        Toast.makeText(getActivity(), mealChosen, Toast.LENGTH_SHORT).show();
                        tvHeading.setText("When would you like to eat?");
                        ResLogo.setVisibility(View.VISIBLE);



                        list_view.setVisibility(View.GONE);
                        TimeText.setVisibility(View.VISIBLE);
                        DateText.setVisibility(View.VISIBLE);
                        confirmOrder.setVisibility(View.VISIBLE);
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


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_view.setVisibility(View.VISIBLE);
                btnOrder.setVisibility(View.GONE);
                tvHeading.setVisibility(View.VISIBLE);
                tvHeading.setText("Where would you like to eat?");

                cancel.setVisibility(View.VISIBLE);
                tvWelcome.setVisibility(View.GONE);
                LogoCuss.setVisibility(View.GONE);


            }
        });

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

    private void resetFragment(){
        new Handler().post(new Runnable() {

            @Override
            public void run()
            {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

    }

    private void createOrder(String mealName, String dTime, int CusID){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .createOrder(mealName, dTime, CusID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String s = response.body().string();
                    JSONObject js = new JSONObject(s);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
                //Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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
