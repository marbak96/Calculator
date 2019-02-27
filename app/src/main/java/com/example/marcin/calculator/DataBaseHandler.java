package com.example.marcin.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DataBaseHandler extends DataBase {

    public DataBaseHandler(Context context) {
        super(context);
    }

    public void setData(String textOperation) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, textOperation);
        database.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<String> getData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor resultCursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<String> listOfOperations = new ArrayList<>();
        while (resultCursor.moveToNext()) {
            listOfOperations.add(resultCursor.getString(1));
        }
        return listOfOperations;
    }

    public void cleanData() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(databaseRecordDelete);
        onCreate(database);
    }

}
