package rmsample.com.pokedex;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String pokemonName = getActivity().getIntent().getStringExtra("pokemon_name");
        if (pokemonName == null){
            pokemonName = "pikachu";
        }
        setPokemonName(pokemonName);
    }

    public void setPokemonName(String pokemonName){
        int imageId = getResources().getIdentifier(pokemonName.toLowerCase(), "drawable",getActivity().getPackageName());
        Drawable drawable = getResources().getDrawable(imageId);
        int fileId = getActivity().getResources().getIdentifier(pokemonName.toLowerCase(),"raw",getActivity().getPackageName());
        Scanner scanner = new Scanner(getActivity().getResources().openRawResource(fileId));
        String pokemonDetails = Utility.readFileText(scanner);
        ((TextView)getActivity().findViewById(R.id.pokemon_details)).setText(pokemonDetails);
        ((ImageView)getActivity().findViewById(R.id.pokemon_image)).setImageDrawable(drawable);
        ((TextView)getActivity().findViewById(R.id.pokemon_name)).setText(pokemonName);
    }
}
