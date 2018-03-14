package com.example.sanov.simpledictionary.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.sanov.simpledictionary.model.IndToEngModel;
import com.example.sanov.simpledictionary.ui.activity.MainActivity;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.sanov.simpledictionary.db.DatabaseContract.IndToEngColumns.ISI;
import static com.example.sanov.simpledictionary.db.DatabaseContract.IndToEngColumns.KATAKUNCI;
import static com.example.sanov.simpledictionary.db.DatabaseContract.TABLE_IND_TO_ENG;

/**
 * Created by sanov on 3/12/2018.
 */

public class IndToEngHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public IndToEngHelper(Context context) {
        this.context = context;
    }

    public IndToEngHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<IndToEngModel> getByKatakunci(String katakunci) {
        String result = "";
        Cursor cursor = db.query(TABLE_IND_TO_ENG, null, KATAKUNCI + " LIKE ?", new String[]{katakunci + "%"}, null, null, KATAKUNCI + " ASC", "20");
        cursor.moveToFirst();
        ArrayList<IndToEngModel> arrayList = new ArrayList<>();
        IndToEngModel indToEngModel;
        if (cursor.getCount() > 0) {
            do {
                indToEngModel = new IndToEngModel();
                indToEngModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                indToEngModel.setKatakunci(cursor.getString(cursor.getColumnIndexOrThrow(KATAKUNCI)));
                indToEngModel.setIsi(cursor.getString(cursor.getColumnIndexOrThrow(ISI)));

                arrayList.add(indToEngModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;
    }

    public long insert(IndToEngModel indToEngModel) {
        ContentValues initVal = new ContentValues();
        initVal.put(KATAKUNCI, indToEngModel.getKatakunci());
        initVal.put(ISI, indToEngModel.getIsi());
        return db.insert(TABLE_IND_TO_ENG, null, initVal);
    }

    public void beginTransaction() {
        db.beginTransaction();;
    }

    public void setTransactionSuccess() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public void insertTransaction(IndToEngModel indToEngModel) {
        String sql = "INSERT INTO " + TABLE_IND_TO_ENG + " (" + KATAKUNCI + ", " + ISI + ") VALUES (?, ?)";
        SQLiteStatement query = db.compileStatement(sql);
        query.bindString(1, indToEngModel.getKatakunci());
        query.bindString(2, indToEngModel.getIsi());
        query.execute();
        query.clearBindings();
     }

}
