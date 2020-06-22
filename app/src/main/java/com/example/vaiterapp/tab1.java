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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vaiterapp.API.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tab1 extends Fragment implements View.OnClickListener{
    //wtf
    private View Tab1View;
    private ListView list_view;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;
    private Button btnOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_customer_main, container, false);
        /*TextView tv = (TextView) rootView.findViewById(R.id.section_label);
        tv.setText("WHATSUP");
        tv.setTextColor(Color.RED);*/
        btnOrder = rootView.findViewById(R.id.btnOrder);

        list_view = rootView.findViewById(R.id.list_view);

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

        list_view.setAdapter(adapter);

        listItems.add("Ocean Basket");
        listItems.add("McDonalds");

        /*btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_view.setVisibility(View.VISIBLE);
                btnOrder.setVisibility(View.GONE);
            }
        });*/

        btnOrder.setOnClickListener(this);

        list_view.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            //Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
            getMeals(selectedItem);
            });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOrder) {
            list_view.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.GONE);
        }
    }

    private void getMeals(String rname){
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getMeals(rname);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(getActivity(), response.body().string(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
