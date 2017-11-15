package com.phedev.app.travelista.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phedev in 2017.
 */

public class Data implements Parcelable {

    //place name
    public final String name;
    //description
    public final String description;
    //image path
    public final String image;
    //province
    public final String province;
    //location detail
    public final String location;
    //author content text
    public final String contentText;
    //image content
    public final String contentImage;
    //key id
    private int KeyId;

    /*create data from discrete values*/
    public Data(String name, String description, String image, String province, String location, String contentText, String contentImage, int keyId) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.province = province;
        this.location = location;
        this.contentText = contentText;
        this.contentImage = contentImage;
        KeyId = keyId;
    }

    /*create new data from cursor*/
    public Data(Cursor cursor){
        this.name = DatabaseManager.getColumnString(cursor, DbHelper.NAME);
        this.description = DatabaseManager.getColumnString(cursor, DbHelper.DESCRIPTION);
        this.image = DatabaseManager.getColumnString(cursor, DbHelper.IMAGE_PATH);
        this.province = DatabaseManager.getColumnString(cursor, DbHelper.PROVINCE);
        this.location = DatabaseManager.getColumnString(cursor, DbHelper.LOCATION);
        this.contentText = DatabaseManager.getColumnString(cursor, DbHelper.CONTENT_TEXT_BY);
        this.contentImage = DatabaseManager.getColumnString(cursor, DbHelper.CONTENT_IMAGE_BY);
    }

    /*create new data from parcelable*/
    protected Data(Parcel in) {
        name = in.readString();
        description = in.readString();
        image = in.readString();
        province = in.readString();
        location = in.readString();
        contentText = in.readString();
        contentImage = in.readString();
        KeyId = in.readInt();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public int getKeyId() {
        return KeyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(province);
        dest.writeString(location);
        dest.writeString(contentText);
        dest.writeString(contentImage);
        dest.writeInt(KeyId);
    }
}
