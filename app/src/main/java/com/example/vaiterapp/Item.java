package com.example.vaiterapp;

public class Item {

    private int imageId;
    private String title;

    public Item(Integer imageId,String title,String subtitle){
        this.imageId=imageId;
        this.title =title;
    }

    public int getImageId(){return imageId;}
    public void setImageId(int imageId){
        this.imageId=imageId;
    }
    public  String getTitle(){return title;}
    public void setTitle(String title){
        this.title=title;
    }
}
