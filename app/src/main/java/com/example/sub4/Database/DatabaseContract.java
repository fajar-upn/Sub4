package com.example.sub4.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "dbFavoriteMovies";

    public static final class FavoriteColoums implements BaseColumns{
        public static String POSTER = "poster";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
    }
}
