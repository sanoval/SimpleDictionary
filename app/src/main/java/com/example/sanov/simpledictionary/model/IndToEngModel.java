package com.example.sanov.simpledictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sanov on 3/12/2018.
 */

public class IndToEngModel implements Parcelable {

    private int id;
    private String katakunci;

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    private String isi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKatakunci() {
        return katakunci;
    }

    public void setKatakunci(String katakunci) {
        this.katakunci = katakunci;
    }

    public static Creator<IndToEngModel> getCREATOR() {
        return CREATOR;
    }

    public IndToEngModel() {

    }

    public IndToEngModel(String katakunci, String isi) {
        this.katakunci = katakunci;
        this.isi = isi;
    }

    public IndToEngModel(int id, String katakunci, String isi) {
        this.id = id;
        this.katakunci = katakunci;
        this.isi = isi;
    }

    protected IndToEngModel(Parcel in) {
        this.id = in.readInt();
        this.katakunci = in.readString();
        this.isi = in.readString();
    }

    public static final Creator<IndToEngModel> CREATOR = new Creator<IndToEngModel>() {
        @Override
        public IndToEngModel createFromParcel(Parcel in) {
            return new IndToEngModel(in);
        }

        @Override
        public IndToEngModel[] newArray(int size) {
            return new IndToEngModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.katakunci);
        parcel.writeString(this.isi);
    }
}
