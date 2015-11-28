package edu.sju;
import java.io.*;

public class SpellChecker {
    private Hashtable<String, String> table = new Hashtable<>();

    public void loadDictionary(String path) throws IOException {
        String line;

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null){

            table.insert(line, "");
        }

        fileReader.close();
    }
}
