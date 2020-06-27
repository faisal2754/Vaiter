package com.example.vaiterapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.vaiterapp.API.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffTab2 extends Fragment {

    private int currID = MainActivity.prf1.getInt("userID", 27);

    private ListView listView;

    private ArrayList<String> meals = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> dateTime = new ArrayList<>();

    private ListAdapterStaffTab2 listAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab2_staff_main,container,false);

        mSwipeRefreshLayout = rootView.findViewById(R.id.swiperefreshstaff2_items);

        listView = rootView.findViewById(R.id.list_view);

        listAdapter = new ListAdapterStaffTab2(this, meals, names, dateTime);
        listView.setAdapter(listAdapter);


        getStaffOrders();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listAdapter.clearAdapter();
                getStaffOrders();
                listView.setAdapter(listAdapter);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        return rootView;
    }

    public void getStaffOrders(){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .showStaffPastOrders(currID);
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
            for(int i=0;i<jArr.length();i = i+4){
                String fn = jArr.getString(i);
                String ln = jArr.getString(i+1);
                String meal = jArr.getString(i+2);
                String dTime = jArr.getString(i+3);
                fn = "Name: " + fn;
                meal = "Meal: " + meal;
                dTime = "Reservation: " + dTime;
                names.add(fn+" "+ln);
                meals.add(meal);
                dateTime.add(dTime);
            }
            listAdapter.notifyDataSetChanged();
        }
    }
}

