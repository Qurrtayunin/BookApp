package com.example.bookapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemData implements Parcelable {
    public String title;
    public String subtitle;
    public String itemImage;
    public String itemDescription;

    public ItemData(){

    }

    protected ItemData(Parcel in) {
        title = in.readString();
        subtitle = in.readString();
        itemImage = in.readString();
        itemDescription = in.readString();
    }

    public static final Creator<ItemData> CREATOR = new Creator<ItemData>() {
        @Override
        public ItemData createFromParcel(Parcel in) {
            return new ItemData(in);
        }

        @Override
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(itemImage);
        dest.writeString(itemDescription);
    }
}
