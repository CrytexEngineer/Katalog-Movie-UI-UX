package com.example.aqil.katalogfilmuiux;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    String thumbnailPath;
    String posterPath;
    String title;
    String overview;
    String release_date;


    public Movie(String thumbnailPath, String title, String overview, String release_date) {
        this.thumbnailPath = thumbnailPath;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
    }


    public String getThumbnailPath() {
        String baseUrl = "http://image.tmdb.org/t/p/w185";
        return baseUrl + thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {


        int index = this.overview.indexOf(" ");
        ArrayList<Integer>indexes = new ArrayList<>();
        while (index >= 0) {
            System.out.println(index);
            index = overview.indexOf(" ", index + 1);
            indexes.add(index);

        }
        try {
            String overview =   this.overview.substring(0,indexes.get(10))+"...";
            return   overview;
        }
        catch (IndexOutOfBoundsException e){
            return this.overview;
        }

    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getRelease_date() {

        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPosterPath() {
        String baseUrl = "http://image.tmdb.org/t/p/original";
        return baseUrl + thumbnailPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDescription() {
        return overview;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnailPath);
        dest.writeString(this.posterPath);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.description);
    }

    protected Movie(Parcel in) {
        this.thumbnailPath = in.readString();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.description = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
