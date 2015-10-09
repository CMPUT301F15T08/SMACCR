package com.example.richard.myapplication;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-08.
 */
public class GiftCardItem {
    private ArrayList<Photo> inventoryphotos = new ArrayList<Photo>();

    private Boolean photodownloadstatus = Boolean.TRUE;

    public GiftCardItem(ArrayList<Photo> inventoryphotos) {
        this.inventoryphotos = inventoryphotos;
    }

    public GiftCardItem() {
    }

    public void addPhoto(Photo photo) {
        inventoryphotos.add(photo);
    }

    public void deletePhoto(Photo photo) {
        inventoryphotos.remove(photo);
    }

    public Boolean containsPhoto() {
        return inventoryphotos.size() > 0;
    }

    public void setPhotodownloadstatus(Boolean photodownloadstatus) {
        this.photodownloadstatus = photodownloadstatus;
    }

    public Boolean manualDownload() {
        if (this.photodownloadstatus == Boolean.FALSE) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
