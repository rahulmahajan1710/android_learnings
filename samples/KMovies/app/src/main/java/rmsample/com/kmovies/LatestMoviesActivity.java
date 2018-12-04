package rmsample.com.kmovies;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmsample.com.kmovies.Models.Movie;
import rmsample.com.kmovies.Models.DataManager;


public class LatestMoviesActivity extends AppCompatActivity {

    private String TAG = LatestMoviesActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_movies);
        lv = findViewById(R.id.movies_list);
        new GetMovies().execute();
    }

    private class GetMovies extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LatestMoviesActivity.this);
            pDialog.setMessage("Fetching Movies...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String moviesURL = Utility.getMoviesURL();
            HttpHandler handler = new HttpHandler();

            String jsonString = handler.makeServiceCall(moviesURL);
            Log.v(TAG, "Response from url: "+ jsonString);
            if (jsonString != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray results = jsonObject.getJSONArray("results");
                    for (int j = 0 ; j < results.length(); j++){
                        JSONObject movieJSON = results.getJSONObject(j);
                        Movie movie = new Movie(movieJSON);
                        DataManager.getInstance().releasedMovies.add(movie);
                    }
                }
                catch (final JSONException ex){
                    Log.v("JSONException", "While movie details: " + ex.getLocalizedMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "JSON parsing error: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else{
                Log.v(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server.", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            //TODO: look for another way to refresh list after data download
            movieListAdapter = new MovieListAdapter(LatestMoviesActivity.this);
            lv.setAdapter(movieListAdapter);
        }
    }

}
