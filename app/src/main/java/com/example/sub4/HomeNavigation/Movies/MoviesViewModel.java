package com.example.sub4.HomeNavigation.Movies;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sub4.Model.MoviesModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesViewModel extends ViewModel {

    private static final String API_KEY = "e5666570388a7a578ade19193ba44b13";
    private MutableLiveData<ArrayList<MoviesModel>> listMovies = new MutableLiveData<>();


    public void setMovies(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MoviesModel> listItems= new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i=0; i<list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        MoviesModel moviesItems =new MoviesModel(movies);
                        listItems.add(moviesItems);
                    }
                    listMovies.postValue(listItems);
                }
                catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Failure",error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MoviesModel>> getMovies(){
        return listMovies;
    }
}