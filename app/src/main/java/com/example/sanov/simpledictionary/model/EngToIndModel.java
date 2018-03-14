package com.example.sanov.simpledictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sanov on 3/12/2018.
 */

public class EngToIndModel implements Parcelable {

    private int id;
    private String keywords;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public EngToIndModel() {

    }

    public EngToIndModel(String keywords, String body) {
        this.keywords = keywords;
        this.body = body;
    }

    public EngToIndModel(int id, String keywords, String body) {
        this.id = id;
        this.keywords = keywords;
        this.body = body;
    }

    private EngToIndModel(Parcel in) {
        this.id = in.readInt();
        this.keywords = in.readString();
        this.body = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(keywords);
        parcel.writeString(body);
    }

    public static final Parcelable.Creator<EngToIndModel> CREATOR = new Parcelable.Creator<EngToIndModel>() {

        @Override
        public EngToIndModel createFromParcel(Parcel parcel) {
            return new EngToIndModel(parcel);
        }

        @Override
        public EngToIndModel[] newArray(int i) {
            return new EngToIndModel[i];
        }
    };
}
