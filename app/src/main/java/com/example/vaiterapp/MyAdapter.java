package com.example.vaiterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vaiterapp.API.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<Item> itemList;
    public  tab1 Tab1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView icon;
        public LinearLayout main;

        public MyViewHolder(final View parent) {
            super(parent);
            title = (TextView) parent.findViewById(R.id.title);
            subtitle = (TextView) parent.findViewById(R.id.subtitle);
            icon = (ImageView) parent.findViewById(R.id.icon);
            main = (LinearLayout) parent.findViewById(R.id.main);

            Tab1 = new tab1();

            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    if (getPosition() == 0){

                        Tab1.getMeals("Ocean Basket");
                    }
                    if (getPosition() == 1){
                        Tab1.getMeals("McDonalds");
                    }
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
                        JSONObject js = new JSONObject(s);
                        if (!js.getBoolean("error")){
                            JSONArray jArr = js.getJSONArray("message");
                            for(int i=0;i<jArr.length();i++){
                                String curr = jArr.getString(i);
                                //Toast.makeText(getActivity(), curr, Toast.LENGTH_LONG).show();
                                //listItems.add(curr);
                            }
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(itemView.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public MyAdapter(List<Item>itemList){
        this.itemList=itemList;
    }



    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item row=itemList.get(position);
        holder.title.setText(row.getTitle());
        holder.subtitle.setText(row.getSubtitle());
        holder.icon.setImageResource(row.getImageId());
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }



}
