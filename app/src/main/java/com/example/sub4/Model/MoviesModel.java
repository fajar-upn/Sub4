package com.example.sub4.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MoviesModel implements Parcelable {
    private int id;
    private String poster, title, description;

    public MoviesModel(JSONObject movies){
        try {
            this.id = movies.getInt("id");
            String poster1 = movies.getString("poster_path");
            this.poster = "https://image.tmdb.org/t/p/w342/"+poster1;
            this.title = movies.getString("title");
            this.description = movies.getString("overview");
        } catch (JSONException e) {
            Log.d("Exception", e.getMessage());
        }
    }

    protected MoviesModel(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<MoviesModel> CREATOR = new Creator<MoviesModel>() {
        @Override
        public MoviesModel createFromParcel(Parcel in) {
            return new MoviesModel(in);
        }

        @Override
        public MoviesModel[] newArray(int size) {
            return new MoviesModel[size];
        }
    };

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
