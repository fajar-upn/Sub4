package com.example.sub4.HomeNavigation.TvShow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sub4.Model.TvShowModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private static final String API_KEY = "e5666570388a7a578ade19193ba44b13";
    private MutableLiveData <ArrayList<TvShowModel>> listTvShow = new MutableLiveData<>();

    public void setTvShow(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowModel> listItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i=0; i<list.length(); i++){
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShowModel tvShowItems = new TvShowModel(tvShow);
                        listItems.add(tvShowItems);
                    }
                    listTvShow.postValue(listItems);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure", error.getMessage());
            }
        });
    }

    public LiveData <ArrayList<TvShowModel>> getTvShow(){
        return listTvShow;
    }
}