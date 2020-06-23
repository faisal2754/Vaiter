package com.example.vaiterapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class MyAdapterMenu extends RecyclerView.Adapter<MyAdapterMenu.MyViewHolderMenu>{
    private List<Item> itemList;
    public  tab1 Tab1;

    public class MyViewHolderMenu extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView icon;
        public LinearLayout main;

        public MyViewHolderMenu(final View parent) {
            super(parent);
            title = (TextView) parent.findViewById(R.id.title_menu);
            subtitle = (TextView) parent.findViewById(R.id.subtitle_menu);
            icon = (ImageView) parent.findViewById(R.id.icon_menu);
            main = (LinearLayout) parent.findViewById(R.id.main_menu);

        }

    }
    public MyAdapterMenu(List<Item>itemList){
        this.itemList=itemList;
    }


    @Override
    public MyAdapterMenu.MyViewHolderMenu onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        //itemView.setVisibility(View.GONE);

        return new MyViewHolderMenu(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolderMenu holder, int position) {
        Item row=itemList.get(position);
        holder.title.setText(row.getTitle());
        holder.icon.setImageResource(row.getImageId());
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

interface ClickListener2{
    void onClick(View view, int position);
}


class RecyclerTouchListener2 implements RecyclerView.OnItemTouchListener{

    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener2(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

        this.clicklistener=clicklistener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
