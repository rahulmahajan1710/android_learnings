package rmsample.com.kmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import rmsample.com.kmovies.Models.DataManager;
import rmsample.com.kmovies.Models.Movie;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class MovieListAdapter extends BaseAdapter {

    private LatestMoviesActivity latestMoviesActivity;
    public ImageLoader imageLoader;
    private static LayoutInflater layoutInflater=null;


    public MovieListAdapter(LatestMoviesActivity activity){
        this.latestMoviesActivity = activity;
        layoutInflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Create ImageLoader object to download and show image in list
        // Call ImageLoader constructor to initialize FileCache
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return DataManager.getInstance().releasedMovies.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolderItem {
        TextView title;
        TextView langauage;
        TextView releaseDate;
        public ImageView image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderItem viewHolderItem = new ViewHolderItem();
        if (view == null){
            view = layoutInflater.inflate(R.layout.list_item,null);
            viewHolderItem.title = (TextView) view.findViewById(R.id.movie_title);
            viewHolderItem.langauage = (TextView) view.findViewById(R.id.movie_language);
            viewHolderItem.releaseDate = (TextView) view.findViewById(R.id.movie_release_date);
            viewHolderItem.image = (ImageView) view.findViewById(R.id.movie_poster);
            view.setTag(viewHolderItem);
        }
        else{
            viewHolderItem = (ViewHolderItem)view.getTag();
        }
        Movie movie = DataManager.getInstance().releasedMovies.get(i);
        viewHolderItem.title.setText(movie.getTitle());
        viewHolderItem.releaseDate.setText(movie.getRelease_date());
        viewHolderItem.langauage.setText(movie.getOriginal_language());
        ImageView imageView = viewHolderItem.image;

        //DisplayImage function from ImageLoader Class
        if (movie.getPosterPath()!= null){
            imageLoader.DisplayImage(Utility.moviePosterURL(movie.getPosterPath()), imageView);
        }

        return view;
    }
}
