package edu.sju;
import java.io.*;

public class SpellChecker {
    private Hashtable<String, String> table = new Hashtable<>();
    private int collisions = 0;
    private int total_ins_lookup = 0;
    private int total_chain_lengths = 0;
    private int max_chain_length = 0;

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
