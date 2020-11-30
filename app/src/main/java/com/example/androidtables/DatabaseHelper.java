package com.example.androidtables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_data";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_QUANTITY = "Quantity";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+TABLE_NAME+"("
                        +COLUMN_ID+" INTEGER PRIMARY KEY, "
                        +COLUMN_NAME+" TEXT NOT NULL, "+COLUMN_QUANTITY+" INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addData(String name, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_NAME, null, values);

        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

    public Cursor structuredQuery(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor x = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_QUANTITY}, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(x != null){
            x.moveToFirst();
        }
        return x;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return x;
    }

    public Cursor getLast(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        x.moveToLast();
        return x;
    }
}