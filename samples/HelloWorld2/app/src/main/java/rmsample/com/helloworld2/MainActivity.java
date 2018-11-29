package rmsample.com.helloworld2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

public class MainActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void playGameClick(View view) {

        Intent intent = new Intent(this, ChooseNamesActivity.class);
        startActivityForResult(intent, 100);
    }

    public void addNameClick(View view) {
        Intent intent = new Intent(this, AddPersonActivity.class);
        startActivityForResult(intent, 200);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            String name = data.getStringExtra("name");
            String surname = data.getStringExtra("surname");
            String msg = "Added Name: " + name + " Surname: " + surname ;
            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
        }
    }
}
