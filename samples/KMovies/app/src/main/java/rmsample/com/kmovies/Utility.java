package rmsample.com.kmovies;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rahul.mahajan on 03/12/18.
 */

public class Utility {
    public static String TAG = "Calendar: ";
    public static String dateFormat = "yyyy-mm-dd";
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public static String API_KEY = "4f5b62d9760290df08d52acd3c08fc8d";
    public static String[] weekdays = { "SUN", "MON", "TUE","WED", "THU", "FRI", "SAT"};


    public static String getWekDayFrom(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        return simpleDateFormat.format(date).toUpperCase();
    }

    public static String getMoviesURL(){
        Date lastFriday,lastToLastFriday;
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.FRIDAY){
            lastFriday = calendar.getTime();
        }
        else if(dayOfWeek == Calendar.SATURDAY){
            calendar.add(Calendar.DATE, -1);
            lastFriday = calendar.getTime();
        }
        else{
            calendar.add(Calendar.DATE, -(dayOfWeek +1));
            lastFriday =  calendar.getTime();
        }
        calendar.add(Calendar.DATE, -7);
        lastToLastFriday = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String firstDateString = simpleDateFormat.format(lastToLastFriday);
        String lastDateString = simpleDateFormat.format(lastFriday);
        return BASE_URL +  "discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" +firstDateString+ "&primary_release_date.lte=" + lastDateString + "&region=IN&language=hi-IN";
    }

    public static String moviePosterURL(String path){
        return "http://image.tmdb.org/t/p/w185" + path;
    }

    public static void copyStream(InputStream is, OutputStream os){
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

}
