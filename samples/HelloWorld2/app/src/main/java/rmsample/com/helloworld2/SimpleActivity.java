package rmsample.com.helloworld2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SimpleActivity extends AppCompatActivity {


    public static  boolean traceActivityLifecycle = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onCreate");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onStart");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onRestart");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onResume");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onPause");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onStop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onDestroy");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onRestoreInstanceState");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (traceActivityLifecycle){
            Log.d("Lifecycle: "+this.getClass().getSimpleName(),"onSaveInstanceState");
        }
    }




}
