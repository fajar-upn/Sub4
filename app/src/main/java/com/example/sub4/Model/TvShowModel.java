package com.example.sub4.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShowModel implements Parcelable {
    private String poster, title, description;

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

    public TvShowModel(JSONObject tvShow){
        try{
            String poster1 = tvShow.getString("poster_path");
            this.poster = "https://image.tmdb.org/t/p/w342/"+poster1;
            this.title = tvShow.getString("name");
            this.description = tvShow.getString("overview");

        }catch (JSONException e){
            Log.d("Exception", e.getMessage());
        }
    }

    protected TvShowModel(Parcel in) {
        poster = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<TvShowModel> CREATOR = new Creator<TvShowModel>() {
        @Override
        public TvShowModel createFromParcel(Parcel in) {
            return new TvShowModel(in);
        }

        @Override
        public TvShowModel[] newArray(int size) {
            return new TvShowModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(title);
        dest.writeString(description);
    }
}
