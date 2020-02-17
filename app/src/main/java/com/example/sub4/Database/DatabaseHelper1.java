package com.example.sub4.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper1 extends SQLiteOpenHelper {
    public static String DATABASE_NAME1 = "dbFavoriteTvShow";
    private static final int DATABASE_VERSION1 = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE_1 = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY,"
                    +"%s TEXT NOT NULL,"
                    +"%s TEXT NOT NULL,"
                    +"%s TEXT NOT NULL)"
            ,DatabaseContract1.TABLE_NAME1
            ,DatabaseContract1.FavoriteColoums1.ID
            ,DatabaseContract1.FavoriteColoums1.POSTER1
            ,DatabaseContract1.FavoriteColoums1.TITLE1
            ,DatabaseContract1.FavoriteColoums1.DESCRIPTION1);

    DatabaseHelper1(Context context){
        super(context,DATABASE_NAME1,null,DATABASE_VERSION1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract1.TABLE_NAME1);
        onCreate(db);
    }

}
