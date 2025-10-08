package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "products_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCT = "product";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";

    private static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE " + TABLE_PRODUCT + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT NOT NULL,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_PRICE + " REAL NOT NULL DEFAULT 0,"
                    + COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0"
                    + ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // simple strategy: drop and recreate. For production, migrate properly.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }
}
