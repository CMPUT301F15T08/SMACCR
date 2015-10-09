package com.example.richard.myapplication;

/**
 * Created by Richard on 2015-10-08.
 */
public class Photo {
    private double size;
    private Boolean beingViewed;


    public Boolean getBeingViewed() {
        return beingViewed;
    }

    public double getSize() {
        return size;
    }

    public void setBeingViewed(Boolean beingViewed) {
        this.beingViewed = beingViewed;
    }

    public void setSize(double size) {
        this.size = size;
    }
    public Boolean checkUnder65536bytes(){
        return (this.size < 65536);
    }

}
