package rmsample.com.kmovies;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class HttpHandler {

    public static String TAG = HttpHandler.class.getSimpleName();
    public HttpHandler(){

    }

    public String makeServiceCall(String stringUrl){
        String respose = null;
        try{
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = new BufferedInputStream(conn.getInputStream());
            respose = convertStreamToString(is);
        }
        catch (MalformedURLException ex){
            Log.v(TAG,"MalformedURLException: "+ ex.getMessage());
        }
        catch (ProtocolException ex){
            Log.v(TAG,"ProtocolException: "+ ex.getMessage());
        }
        catch (IOException ex){
            Log.v(TAG,"IOException: "+ ex.getMessage());
        }
        catch (Exception ex){
            Log.v(TAG,"Exception: "+ ex.getMessage());
        }
        return respose;
    }


    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while((line = reader.readLine())!= null){
                sb.append(line).append('\n');
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        finally {
            try{
                is.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
