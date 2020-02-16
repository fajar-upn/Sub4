package com.example.sub4.Database;

import android.provider.BaseColumns;

public class DatabaseContract1 {

    public static String TABLE_NAME1 = "dbFavoriteTvShow";

    public static final class FavoriteColoums1 implements BaseColumns {
        public static String POSTER1 = "poster";
        public static String TITLE1 = "title";
        public static String DESCRIPTION1 = "description";
    }
}
