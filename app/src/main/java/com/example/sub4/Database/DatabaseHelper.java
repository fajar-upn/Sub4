package com.example.sub4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbfavorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
            +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"%s TEXT NOT NULL,"
            +"%s TEXT NOT NULL,"
            +"%s TEXT NOT NULL)"
        ,DatabaseContract.TABLE_NAME
        ,DatabaseContract.FavoriteColoums._ID
        ,DatabaseContract.FavoriteColoums.POSTER
        ,DatabaseContract.FavoriteColoums.TITLE
        ,DatabaseContract.FavoriteColoums.DESCRIPTION);

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_NAME);
        onCreate(db);
    }

}
