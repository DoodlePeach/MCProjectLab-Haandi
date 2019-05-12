package com.example.beginneractivity;

import java.util.LinkedList;

public class ItemCategoryList
{
    private String mCat;
    private LinkedList<SearchItem> mItemsInCat;

    public ItemCategoryList(String mCat, LinkedList<SearchItem> mItemsInCat) {
        this.mCat = mCat;
        this.mItemsInCat = mItemsInCat;
    }

    public String getmCat() {
        return mCat;
    }

    public void setmCat(String mCat) {
        this.mCat = mCat;
    }

    public LinkedList<SearchItem> getmItemsInCat() {
        return mItemsInCat;
    }

    public void setmItemsInCat(LinkedList<SearchItem> mItemsInCat) {
        this.mItemsInCat = mItemsInCat;
    }
}
