package rmsample.com.kmovies;

import android.graphics.Bitmap;
import android.media.Image;

import rmsample.com.kmovies.Models.ImageInfo;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class BitmapDisplayerRunnable implements Runnable {

    Bitmap bitmap;
    ImageInfo photoToLoad;

    public BitmapDisplayerRunnable(ImageInfo photoToLoad, Bitmap bitmap){
        this.bitmap = bitmap;
        this.photoToLoad = photoToLoad;
    }


    @Override
    public void run() {

    }
}
