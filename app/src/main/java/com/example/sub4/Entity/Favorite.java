package com.example.sub4.Entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Favorite implements Parcelable {

    private int id;
    private String poster, title, description;

    public Favorite(){

    }

    public Favorite(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public Favorite(int id, String poster, String title, String description) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(poster);
        dest.writeString(title);
        dest.writeString(description);
    }
}
