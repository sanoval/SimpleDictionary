package com.example.sanov.simpledictionary.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.sanov.simpledictionary.db.DatabaseContract.EngToIndColumns.BODY;
import static com.example.sanov.simpledictionary.db.DatabaseContract.EngToIndColumns.KEYWORDS;
import static com.example.sanov.simpledictionary.db.DatabaseContract.IndToEngColumns.ISI;
import static com.example.sanov.simpledictionary.db.DatabaseContract.IndToEngColumns.KATAKUNCI;
import static com.example.sanov.simpledictionary.db.DatabaseContract.TABLE_ENG_TO_IND;
import static com.example.sanov.simpledictionary.db.DatabaseContract.TABLE_IND_TO_ENG;

/**
 * Created by sanov on 3/12/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbdictionary";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_ENG_TO_IND = "create table " + TABLE_ENG_TO_IND +
            " (" + _ID + " integer primary key autoincrement, " +
            KEYWORDS + " text not null," +
            BODY + " text not null);";

    private static String CREATE_TABLE_IND_TO_ENG = "create table " + TABLE_IND_TO_ENG +
            " (" + _ID + " integer primary key autoincrement, " +
    KATAKUNCI + " text not null," +
            ISI + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENG_TO_IND);
        db.execSQL(CREATE_TABLE_IND_TO_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENG_TO_IND);
        onCreate(db);
    }
}
