package com.example.sub4.Interface;

import com.example.sub4.Entity.Favorite;
import com.example.sub4.Entity.Favorite1;

import java.util.ArrayList;

public interface LoadFavoriteCallback1 {
    void preExecute();
    void postExecute(ArrayList<Favorite1> favorites);
}
