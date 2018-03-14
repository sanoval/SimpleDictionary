package com.example.sanov.simpledictionary.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.sanov.simpledictionary.model.EngToIndModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.sanov.simpledictionary.db.DatabaseContract.EngToIndColumns.BODY;
import static com.example.sanov.simpledictionary.db.DatabaseContract.EngToIndColumns.KEYWORDS;
import static com.example.sanov.simpledictionary.db.DatabaseContract.TABLE_ENG_TO_IND;

/**
 * Created by sanov on 3/12/2018.
 */

public class EngToIndHelper {

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase db;

    public EngToIndHelper(Context context) {
        this.context = context;
    }

    public EngToIndHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<EngToIndModel> getDataByKeywords(String keywords) {
        String result = "";
        Cursor cursor = db.query(TABLE_ENG_TO_IND, null, KEYWORDS + " LIKE ?", new String[]{keywords + "%"}, null, null, KEYWORDS +" ASC", "20");
        cursor.moveToFirst();
        ArrayList<EngToIndModel> arrayList =  new ArrayList<>();
        EngToIndModel engToIndModel;
        if (cursor.getCount() > 0) {
            do {
                engToIndModel = new EngToIndModel();
                engToIndModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                engToIndModel.setKeywords(cursor.getString(cursor.getColumnIndexOrThrow(KEYWORDS)));
                engToIndModel.setBody(cursor.getString(cursor.getColumnIndexOrThrow(BODY)));

                arrayList.add(engToIndModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(EngToIndModel engToIndModel) {
        ContentValues initVal = new ContentValues();
        initVal.put(KEYWORDS, engToIndModel.getKeywords());
        initVal.put(BODY, engToIndModel.getBody());
        return db.insert(TABLE_ENG_TO_IND, null, initVal);
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccess() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public void insertTransaction(EngToIndModel engToIndModel) {
        String sql = "INSERT INTO " + TABLE_ENG_TO_IND + " (" + KEYWORDS + ", " + BODY + ") VALUES (?, ?)";
        SQLiteStatement query = db.compileStatement(sql);
        query.bindString(1, engToIndModel.getKeywords());
        query.bindString(2, engToIndModel.getBody());
        query.execute();
        query.clearBindings();
    }
}
