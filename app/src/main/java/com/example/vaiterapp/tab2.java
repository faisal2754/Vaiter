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

public class tab2 extends Fragment {

    private int currID = MainActivity.prf1.getInt("userID", 27);

    private ListView listView;
    /*private int[] mImages;
    private String[] meals;
    private String[] dateTime;*/

    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> meals = new ArrayList<>();
    private ArrayList<String> dateTime = new ArrayList<>();

    private ListAdapterTab2 listAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab2_customer_main,container,false);

        mSwipeRefreshLayout = rootView.findViewById(R.id.swiperefresh_items);

        listView = rootView.findViewById(R.id.listTab2);

        listAdapter = new ListAdapterTab2(this, meals, dateTime, mImages);
        listView.setAdapter(listAdapter);


        getOrders();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to make your refresh action
                // CallYourRefreshingMethod();


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

    public void getOrders(){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .showCurrentOrders(currID);
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
            for(int i=0;i<jArr.length();i = i+3){
                String meal = jArr.getString(i);
                String dTime = jArr.getString(i+1);
                String resName = jArr.getString(i+2);
                meals.add(meal);
                dateTime.add(dTime);
                if (resName.equals("Ocean Basket")){
                    mImages.add(R.drawable.ob);
                } else if (resName.equals("McDonalds")){
                    mImages.add(R.drawable.mcdonalds);
                }
            }
            listAdapter.notifyDataSetChanged();
        }
    }
}
