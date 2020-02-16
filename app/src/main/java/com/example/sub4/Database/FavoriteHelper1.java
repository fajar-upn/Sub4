package com.example.sub4.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.sub4.Database.DatabaseContract1.TABLE_NAME1;

public class FavoriteHelper1 {
    private static final String DATABASE_TABLE_1 = TABLE_NAME1;
    private static DatabaseHelper1 databaseHelper1;
    private static FavoriteHelper1 INSTANCE1;

    private static SQLiteDatabase database1;

    public FavoriteHelper1(Context context){
        databaseHelper1 = new DatabaseHelper1(context);
    }

    public static FavoriteHelper1 getInstance(Context context){
        if (INSTANCE1 == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE1 == null){
                    INSTANCE1 = new FavoriteHelper1(context);
                }
            }
        }
        return INSTANCE1;
    }

    public void open1() throws SQLException {
        database1 = databaseHelper1.getWritableDatabase();
    }

    public void close1(){
        databaseHelper1.close();

        if (database1.isOpen()){
            database1.close();
        }
    }

    public Cursor queryAll1(){
        return database1.query(DATABASE_TABLE_1,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null
        );
    }

    public long insert1(ContentValues values){
        return database1.insert(DATABASE_TABLE_1,null, values);
    }

    public int update1(String id, ContentValues values){
        return database1.update(DATABASE_TABLE_1,values,_ID+ " = ?", new String[]{id});
    }

    public int deleteById1(String id){
        return database1.delete(DATABASE_TABLE_1,_ID + " = ?", new String[]{id});
    }
}
