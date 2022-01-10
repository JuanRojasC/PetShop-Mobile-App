package com.petshop.persistence.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;
import java.io.IOException;


public class DBHelper extends SQLiteOpenHelper {

    private static final String dbName = "petshop.sqlite";
    public static final String FAVORITES_TABLE = "favorites";
    public static final String PRODUCTS_TABLE = "products";
    public static final String SERVICES_TABLE = "services";
    public static final String STORES_TABLE = "stores";

    private final SQLiteDatabase db;
    private final Context context;

    public DBHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
        this.context = context;
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        try {
//            for(String query : DBQueries.getInstance().getAllCreatesTables()) db.execSQL(query);
//            for(SQLiteStatement statement : DBQueries.getInstance().getAllInserts(this.context, db)) statement.executeInsert();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public Cursor getData(String table){
        return db.rawQuery("SELECT * FROM "+table, null);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @SuppressLint("Range")
    public void printResultQuery(Cursor cursor){
        StringBuilder result = new StringBuilder("RESULTADO DE LA QUERY: { ");
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            do {
                for (String name: columnNames) {
                    if(!name.equals("image"))
                        result.append(String.format("%s: %s    ", name, cursor.getString(cursor.getColumnIndex(name))));
                    else
                        result.append(String.format("%s: %s ", name, cursor.getBlob(cursor.getColumnIndex(name)) != null ? "byte[]" : "null"));
                }
            } while (cursor.moveToNext());
            result.append("}");
            Log.i("DATA", result.toString());
        }
    }
}
