package com.example.vaiterapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

public class tab3 extends Fragment {
    private int currID = MainActivity.prf1.getInt("userID", 27);

    private ListView listView;

    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> meals = new ArrayList<>();
    private ArrayList<String> dateTime = new ArrayList<>();

    private ListAdapterTab3 listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab2_customer_main,container,false);

        listView = rootView.findViewById(R.id.listTab2);

        listAdapter = new ListAdapterTab3(this, meals, dateTime, mImages);
        listView.setAdapter(listAdapter);

        getOrders();

        return rootView;
    }

    public void getOrders(){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .showPastOrders(currID);
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
