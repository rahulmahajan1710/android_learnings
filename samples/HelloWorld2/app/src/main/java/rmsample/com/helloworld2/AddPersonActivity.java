package rmsample.com.helloworld2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AddPersonActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
    }

    public void addPersonClick(View view) {
        String name = ((EditText)findViewById(R.id.name_text)).getText().toString();
        String surname = ((EditText)findViewById(R.id.surname_text)).getText().toString();

        try {
            PrintStream printStream = new PrintStream(openFileOutput("added_persons.txt", MODE_PRIVATE | MODE_APPEND));
            printStream.println(name + " " + surname);
            printStream.close();
            Intent goBackIntent = new Intent();
            goBackIntent.putExtra("name", name);
            goBackIntent.putExtra("surname", surname);
            setResult(RESULT_OK, goBackIntent);
            finish();
        }
        catch (FileNotFoundException ex){
            Log.d("Error: ", ex.getLocalizedMessage());
        }
    }
}
