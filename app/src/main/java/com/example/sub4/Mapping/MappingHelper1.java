package com.example.sub4.Mapping;

import android.database.Cursor;

import com.example.sub4.Database.DatabaseContract1;
import com.example.sub4.Entity.Favorite1;

import java.util.ArrayList;

public class MappingHelper1 {
    public static ArrayList<Favorite1> mapCursorToArrayList(Cursor favoriteCursor){
        ArrayList<Favorite1> favoriteList = new ArrayList<>();

        while (favoriteCursor.moveToNext()){
            int id = favoriteCursor.getInt(favoriteCursor.getColumnIndexOrThrow(DatabaseContract1.FavoriteColoums1._ID));
            String poster = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract1.FavoriteColoums1.POSTER1));
            String title = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract1.FavoriteColoums1.TITLE1));
            String description = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract1.FavoriteColoums1.DESCRIPTION1));
            favoriteList.add(new Favorite1(id,poster,title,description));
        }
        return favoriteList;
    }
}
