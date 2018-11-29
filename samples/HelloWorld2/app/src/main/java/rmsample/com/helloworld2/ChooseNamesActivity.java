package rmsample.com.helloworld2;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ChooseNamesActivity extends SimpleActivity {

    Map<String,String> dict = new HashMap<String, String>();
    String correctSurname , currentWord;
    MediaPlayer mediaPlayer;
    private  int points, highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_names);
        points = 0;
        mediaPlayer = MediaPlayer.create(this, R.raw.jeopardy);
    }

    private void saveHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("scores", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt("high_score", highScore);
        prefEditor.commit();
    }

    private void getHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("scores", MODE_PRIVATE);
        highScore = sharedPreferences.getInt("high_score", 0);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("points", points);
        outState.putString("word", ((TextView)findViewById(R.id.name)).getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        points = savedInstanceState.getInt("points",0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readFileData();
        chooseSurnames();

        mediaPlayer.start();
        getHighScore();
    }

    private void updateList(final List<String> list){

        ListView listView = (ListView)findViewById(R.id.list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedSurname = list.get(i);
                if (selectedSurname.equals(correctSurname)){
                    points++;
                    if (points > highScore){
                        highScore = points;
                        saveHighScore();
                    }
                    Toast.makeText(ChooseNamesActivity.this,"Awesome. Points: "+ points + "High= "+ highScore, Toast.LENGTH_SHORT).show();
                }
                else{
                    points--;
                    Toast.makeText(ChooseNamesActivity.this,"Uh oh. Points: " + points + "High= "+ highScore, Toast.LENGTH_SHORT).show();
                }
                chooseSurnames();

            }
        });


    }

    private void readFileData(){
        Scanner scanner1 = new Scanner(getResources().openRawResource(R.raw.persons));
        readFileHelper(scanner1);

        try {
            Scanner scanner2 = new Scanner(openFileInput("added_persons.txt"));
            readFileHelper(scanner2);
        }
        catch (FileNotFoundException ex){
            Log.d("ChooseNameActivity: ", ex.getLocalizedMessage());
        }

    }

    private void readFileHelper(Scanner scanner){
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length<2) continue;
            dict.put(parts[0],parts[1]);
        }
    }


    private void chooseSurnames(){
        Random randy = new Random();
        List<String> names = new ArrayList<String>(dict.keySet());
        int randInt = randy.nextInt(names.size());

        String name = names.get(randInt);
        correctSurname = dict.get(name);

        names = new ArrayList<String>(dict.values());
        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText(name);
        Collections.shuffle(names);
        updateList(names);
    }

    public void addWordPairClick(View view) {
        Intent intent = new Intent(this,AddPersonActivity.class);
        startActivity(intent);
    }
}
