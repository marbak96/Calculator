package com.example.marcin.calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "calculations.db";
    protected static final String TABLE_NAME = "userdata";
    private static final String COLUMN_ID = "id";
    protected static final String COLUMN_NAME = "name";

    private static final String databaseRecord = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT ) ";
    protected static final String databaseRecordDelete = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(databaseRecord);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(databaseRecordDelete);
        onCreate(db);
    }
}
