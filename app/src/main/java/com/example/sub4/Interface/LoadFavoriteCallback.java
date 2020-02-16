package com.example.sub4.Interface;

import com.example.sub4.Entity.Favorite;

import java.util.ArrayList;

public interface LoadFavoriteCallback {
        void preExecute();
        void postExecute(ArrayList<Favorite> favorites);
}
