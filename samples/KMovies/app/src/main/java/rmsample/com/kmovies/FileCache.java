package rmsample.com.kmovies;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by rahul.mahajan on 04/12/18.
 */

public class FileCache {
    private File cacheDir;

    public FileCache(Context context){

//        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
//        }
//        else{
//            cacheDir = context.getCacheDir();
//        }

        cacheDir = new File(context.getCacheDir(),"LazyList");
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url){
        String fileName = String.valueOf(url.hashCode());
        File f = new File(cacheDir,fileName);
        return f;
    }

    public void clear(){
        File[] files = cacheDir.listFiles();
        if (files == null){
            return;
        }
        for (File f : files){
            f.delete();
        }
    }
}
