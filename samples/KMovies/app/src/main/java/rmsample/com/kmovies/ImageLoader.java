package rmsample.com.kmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;
import android.os.Handler;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class ImageLoader {
    MemoryCache memoryCache =  new MemoryCache();
    FileCache fileCache;

    private Map<ImageView,String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;

    Handler handler = new Handler();

    public ImageLoader(Context context){
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    final int stub_id = R.drawable.movie_placeholder;

    public void DisplayImage(String url , ImageView imageView){
        imageViews.put(imageView,url);

        Bitmap bitmap = memoryCache.get(url);

        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
        else{
            queuePhoto(url,imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url , ImageView imageView){
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url)){
            return true;
        }
        return false;
    }

    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u;
            imageView=i;
        }
    }

    class PhotosLoader implements Runnable{

        PhotoToLoad photoToLoad;
        PhotosLoader (PhotoToLoad photoToLoad){
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
                try{
                    //Check if image already downloaded
                    if(imageViewReused(photoToLoad))
                        return;
                    // download image from web url
                    Bitmap bmp = getBitmap(photoToLoad.url);

                    // set image data in Memory Cache
                    memoryCache.put(photoToLoad.url, bmp);

                    if(imageViewReused(photoToLoad))
                        return;

                    // Get bitmap to display
                    BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);

                    // Causes the Runnable bd (BitmapDisplayer) to be added to the message queue.
                    // The runnable will be run on the thread to which this handler is attached.
                    // BitmapDisplayer run method will call
                    handler.post(bd);

                }catch(Throwable th){
                    th.printStackTrace();
                }
        }

        private Bitmap getBitmap(String url){
            File f = fileCache.getFile(url);

            //from SD cache
            //CHECK : if trying to decode file which not exist in cache return null
            Bitmap b = null;
            if (f.exists()){
                b = decodeFile(f);
            }
            if (b!=null){
                return b;
            }
            try{
                Bitmap bitmap = null;
                URL imageURL = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setInstanceFollowRedirects(true);
                conn.connect();
                InputStream is = conn.getInputStream();

                // Constructs a new FileOutputStream that writes to file
                // if file not exist then it will create file
                OutputStream os = new FileOutputStream(f);

                // See Utils class CopyStream method
                // It will each pixel from input stream and
                // write pixels to output stream (file)
                Utility.copyStream(is, os);

                os.close();
                conn.disconnect();

                //Now file created and going to resize file with defined height
                // Decodes image and scales it to reduce memory consumption
                bitmap = decodeFile(f);

                return bitmap;
            }
            catch (MalformedURLException ex){
                ex.printStackTrace();
            }
            catch (Throwable ex){
                ex.printStackTrace();
                Log.v("FileNotFoundException", ex.getLocalizedMessage());
                if(ex instanceof OutOfMemoryError)
                    memoryCache.clear();
                return null;
            }
            return null;
        }


        private Bitmap decodeFile(File f){
            try{
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                FileInputStream ip = new FileInputStream(f);
                BitmapFactory.decodeStream(ip,null,o);
                ip.close();

                final int REQUIRED_SIZE = 85;

                int width_tmp =o.outWidth;
                int height_tmp = o.outHeight;
                int scale = 1;

                while(true){
                    if (width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE){
                        break;
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }

                //decode with current scale values
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                FileInputStream fis = new FileInputStream(f);
                Bitmap bitmap = BitmapFactory.decodeStream(fis, null, options);
                fis.close();
                return bitmap;
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }

    class BitmapDisplayer implements Runnable{
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap bm, PhotoToLoad p){
            bitmap = bm;
            photoToLoad = p;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad)){
                return;
            }
            if (bitmap!=null){
                photoToLoad.imageView.setImageBitmap(bitmap);
            }
            else{
                photoToLoad.imageView.setImageResource(stub_id);
            }
        }
    }


    public void clearCache(){
        memoryCache.clear();
        fileCache.clear();
    }

}


