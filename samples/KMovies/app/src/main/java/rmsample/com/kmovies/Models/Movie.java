package rmsample.com.kmovies.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class Movie {
    private int id;
    private String title;
    private String release_date;
    private String overview;
    private String posterPath;
    private String original_language;

    public Movie(JSONObject jsonObject){
        try{
            id = jsonObject.getInt("id");
            title = jsonObject.getString("original_title");
            release_date = jsonObject.getString("release_date");
            posterPath = jsonObject.getString("poster_path");
            original_language = jsonObject.getString("original_language");
        }
        catch (JSONException ex){
            Log.v("JSONException: ", "unable to iniatialize Movie object: " + ex.getLocalizedMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }
}
