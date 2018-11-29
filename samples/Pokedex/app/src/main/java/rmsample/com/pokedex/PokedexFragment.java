package rmsample.com.pokedex;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokedexFragment extends Fragment{


    public PokedexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokedex, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        TableLayout tableLayout = (TableLayout)activity.findViewById(R.id.table_layout);
        for(int i=0;i< tableLayout.getChildCount(); i++){
            TableRow tableRow  = (TableRow)tableLayout.getChildAt(i);
            for(int j=0; j< tableRow.getChildCount(); j++){
                ImageButton imageButton = (ImageButton)tableRow.getChildAt(j);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pokemonClick(view);
                    }
                });
            }
        }

    }


    public void pokemonClick(View view) {
        ImageButton button = (ImageButton)view;
        String tag = button.getTag().toString();
        Activity activity = getActivity();
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(activity,DetailsActivity.class);
            intent.putExtra("pokemon_name", tag);
            activity.startActivity(intent);
        }
        else{
            DetailsFragment detailsFragment = (DetailsFragment)activity.getFragmentManager().findFragmentById(R.id.frag_details);
            detailsFragment.setPokemonName(tag);
        }

    }
}
