package com.example.richard.myapplication;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by Richard on 2015-10-08.
 */
public class GiftCardItemTest extends ActivityInstrumentationTestCase2 {

    public GiftCardItemTest() {
        super(com.example.richard.myapplication.MainActivity.class);
    }

    //Ensure we can attach photos and ensuring it is under 65536 bytes
    /*
    test for photo size, adding photo and if they exist
     */
    public void testAddPhoto() throws Exception {
        Photo p = new Photo();
        p.setSize(1);
        assertTrue(p.checkUnder65536bytes());

        GiftCardItem card = new GiftCardItem();
        card.addPhoto(p);

        assertTrue(card.containsPhoto());

    }

    //ensure that we are able to delete photos that are already in inventory photos
    /*
    checking for deleting photos and if they are removed are not
     */
    public void testDeletePhoto() throws Exception {
        GiftCardItem card = new GiftCardItem();
        Photo p = new Photo();
        card.addPhoto(p);
        assertTrue(card.containsPhoto());
        card.deletePhoto(p);
        assertFalse(card.containsPhoto());

    }

    //Test if any attached photo(s) exist
    //we cannot view/delete a inventory photos is no photos exist in the inventory
    public void testContainsPhoto() {
        GiftCardItem card = new GiftCardItem();
        assertFalse(card.containsPhoto());

    }

    //Test if we can manually download, we can only if photo downloads is disable(photo status is false)
    public void testManualDownload(){
        GiftCardItem card = new GiftCardItem();
        card.setPhotodownloadstatus(Boolean.FALSE);

        assertTrue(card.manualDownload());

        card.setPhotodownloadstatus(Boolean.TRUE);

        assertFalse(card.manualDownload());
    }


}