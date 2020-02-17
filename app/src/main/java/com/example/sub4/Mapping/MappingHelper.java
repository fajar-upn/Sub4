package com.example.sub4.Mapping;

import android.database.Cursor;
import android.util.Log;

import com.example.sub4.Database.DatabaseContract;
import com.example.sub4.Entity.Favorite;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Favorite> mapCursorToArrayList(Cursor favoriteCursor){
        ArrayList<Favorite> favoriteList = new ArrayList<>();

        while (favoriteCursor.moveToNext()){
            int id = favoriteCursor.getInt(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColoums.ID));
            String poster = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColoums.POSTER));
            String title = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColoums.TITLE));
            String description = favoriteCursor.getString(favoriteCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColoums.DESCRIPTION));
            favoriteList.add(new Favorite(id,poster,title,description));
        }
        return favoriteList;
    }
}
