package com.example.aqil.katalogfilmuiux;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

public class QueryUtils extends AsyncTaskLoader<ArrayList<Movie>> {


    private ArrayList<Movie> mData = new ArrayList<>();

    private boolean hasResult = false;
    private String mNamaMovie = null;


    public QueryUtils(Context context, String namaMovie) {
        super(context);
        onContentChanged();
        this.mNamaMovie = namaMovie;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        this.mData = data;
        hasResult = true;
        super.deliverResult(data);

    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    private final String API_KEY = getContext().getString(R.string.API_KEY);

    @Override
    public ArrayList<Movie> loadInBackground() {
        String url = null;
        Log.d("TAG", "loadInBackground: " + getId());
        switch (getId()) {
            case 0:
                url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US";
                break;
            case 1:
                url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";
                break;
            case 2:

                if (TextUtils.isEmpty(mNamaMovie)) {
                    url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en";
                } else {
                    url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + mNamaMovie + "&include_adult=true";
                }
                break;
        }
        Log.d(TAG, "loadInBackground: " + url);
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> listMovie = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {

            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    Log.d(TAG, "onSuccess: Test");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String title = jsonArray.getJSONObject(i).getString("title");
                        Log.d(TAG, "onSuccess: " + title);
                        String overview = jsonArray.getJSONObject(i).getString("overview");
                        Log.d(TAG, "onSuccess: " + overview);
                        String releaseDate = jsonArray.getJSONObject(i).getString("release_date");
                        Log.d(TAG, "onSuccess: " + releaseDate);
                        String poster_path = jsonArray.getJSONObject(i).getString("poster_path");
                        Log.d(TAG, "onSuccess: " + poster_path);
                        Movie movie = new Movie(poster_path, title, overview, releaseDate);
                        listMovie.add(movie);
                        Log.d(TAG, "onSuccess: " + listMovie.size());

                        Log.d("TAG", "onSuccess: " + title + overview + releaseDate + releaseDate + poster_path);

                    }


                } catch (Exception e) {
                    Log.d(TAG, "onSuccess: " + 404);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


        return listMovie;
    }

    protected void onReleaseResources(ArrayList<Movie> data) {
        //nothing to do.
    }
}
