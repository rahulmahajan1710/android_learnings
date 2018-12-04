package rmsample.com.kmovies.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class DataManager {
    private static final DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    public List<Movie> releasedMovies = new ArrayList<Movie>();

}
