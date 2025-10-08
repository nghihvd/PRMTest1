package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final DBHelper dbHelper;

    public ProductRepository(Context context) {
        this.dbHelper = new DBHelper(context.getApplicationContext());
    }

    public long insert(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, product.getName());
        cv.put(DBHelper.COLUMN_DESCRIPTION, product.getDescription());
        cv.put(DBHelper.COLUMN_PRICE, product.getPrice());
        cv.put(DBHelper.COLUMN_QUANTITY, product.getQuantity());
        long id = db.insert(DBHelper.TABLE_PRODUCT, null, cv);
        db.close();
        return id;
    }

    public Product getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] cols = {DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_DESCRIPTION, DBHelper.COLUMN_PRICE, DBHelper.COLUMN_QUANTITY};
        String selection = DBHelper.COLUMN_ID + "=?";
        String[] args = {String.valueOf(id)};
        Cursor c = db.query(DBHelper.TABLE_PRODUCT, cols, selection, args, null, null, null);
        Product p = null;
        if (c != null) {
            if (c.moveToFirst()) {
                p = cursorToProduct(c);
            }
            c.close();
        }
        db.close();
        return p;
    }

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] cols = {DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_DESCRIPTION, DBHelper.COLUMN_PRICE, DBHelper.COLUMN_QUANTITY};
        Cursor c = db.query(DBHelper.TABLE_PRODUCT, cols, null, null, null, null, DBHelper.COLUMN_NAME + " ASC");
        if (c != null) {
            while (c.moveToNext()) {
                list.add(cursorToProduct(c));
            }
            c.close();
        }
        db.close();
        return list;
    }

    public int update(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, product.getName());
        cv.put(DBHelper.COLUMN_DESCRIPTION, product.getDescription());
        cv.put(DBHelper.COLUMN_PRICE, product.getPrice());
        cv.put(DBHelper.COLUMN_QUANTITY, product.getQuantity());
        String where = DBHelper.COLUMN_ID + "=?";
        String[] args = {String.valueOf(product.getId())};
        int rows = db.update(DBHelper.TABLE_PRODUCT, cv, where, args);
        db.close();
        return rows;
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = DBHelper.COLUMN_ID + "=?";
        String[] args = {String.valueOf(id)};
        int rows = db.delete(DBHelper.TABLE_PRODUCT, where, args);
        db.close();
        return rows;
    }

    private Product cursorToProduct(Cursor c) {
        long id = c.getLong(c.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
        String name = c.getString(c.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
        String desc = c.getString(c.getColumnIndexOrThrow(DBHelper.COLUMN_DESCRIPTION));
        double price = c.getDouble(c.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE));
        int qty = c.getInt(c.getColumnIndexOrThrow(DBHelper.COLUMN_QUANTITY));
        return new Product(id, name, desc, price, qty);
    }
}
