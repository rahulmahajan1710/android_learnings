package rmsample.com.pokedex;

import java.util.Scanner;

/**
 * Created by rahul.mahajan on 29/11/18.
 */

public class Utility extends Object {

    public static String readFileText(Scanner scanner){
        String fileText = "";
        while(scanner.hasNextLine()){
            fileText += scanner.nextLine();
        }
        return fileText;
    }
}
