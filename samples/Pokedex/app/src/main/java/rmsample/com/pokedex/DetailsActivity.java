package rmsample.com.pokedex;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Scanner;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String pokemonName = intent.getStringExtra("pokemon_name");
        if (pokemonName == null){
            pokemonName = "pikachu";
        }
        setPokemonName(pokemonName);
    }

    private void setPokemonName(String pokemonName){
        int imageId = getResources().getIdentifier(pokemonName.toLowerCase(), "drawable",getPackageName());
        Drawable drawable = getResources().getDrawable(imageId);



        int fileId = getResources().getIdentifier(pokemonName.toLowerCase(),"raw",getPackageName());
        Scanner scanner = new Scanner(getResources().openRawResource(fileId));
        String pokemonDetails = Utility.readFileText(scanner);
        ((TextView)findViewById(R.id.pokemon_details)).setText(pokemonDetails);
        ((ImageView)findViewById(R.id.pokemon_image)).setImageDrawable(drawable);
        ((TextView)findViewById(R.id.pokemon_name)).setText(pokemonName);
    }
}
