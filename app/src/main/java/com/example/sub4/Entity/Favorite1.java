package com.example.sub4.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite1 implements Parcelable {
    private int id;
    private String poster, title, description;


    public Favorite1(int id, String poster, String title, String description) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.description = description;
    }

    protected Favorite1(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Favorite1> CREATOR = new Creator<Favorite1>() {
        @Override
        public Favorite1 createFromParcel(Parcel in) {
            return new Favorite1(in);
        }

        @Override
        public Favorite1[] newArray(int size) {
            return new Favorite1[size];
        }
    };

    public Favorite1() {

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
