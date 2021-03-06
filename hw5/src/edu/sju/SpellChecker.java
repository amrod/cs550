package edu.sju;
import java.io.*;
import java.util.ArrayList;

/** Spellchecker class */
public class SpellChecker {
    private Hashtable<String, String> table = new Hashtable<>();
    final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Reads a dictionary file into a hashtable.
     * @param path path of the dictionary file to load.
     * @throws IOException if file is not readable.
     */
    public void loadDictionary(String path) throws IOException {
        String line;

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null){
            table.insert(line, "");
        }

        fileReader.close();
    }

    /**
     * Reads entries from an input file and checks spelling against dictionary.
     * @param path path to the input file.
     * @return A string with possible corrections to the words not found in
     * the dictionary.
     * @throws IOException if input file is not readable.
     */
    public String loadInputText(String path) throws IOException {
        ArrayList<String> mispelled = new ArrayList<>();
        String word;
        StringBuilder result = new StringBuilder();

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int i = 1; // the line number
        while ((word = bufferedReader.readLine()) != null){
            if (word.length() == 0)
                continue;

            word = word.toLowerCase();
            if (table.get(word) == null) {
                result.append(word).append(", ");
                result.append(i).append(": ");
                result.append(getCorrections(word));
                result.append("\n");
            }
            i++;
        }

        fileReader.close();
        return result.toString();
    }

    /**
     * Attempts to find a correction by changing one letter and looking up
     * the resulting word in the dictionary.
     * @param word Word to find a correction for.
     * @return A list of corrections separated by comma.
     */
    private String getCorrections(String word){
        StringBuilder result = new StringBuilder();
        Hashtable<String, Boolean> corrections = new Hashtable<>();
        char[] wordChars = word.toCharArray();
        String correctWord;

        // Loop over every character of word
        for(int i = 0 ; i < wordChars.length ; i++) {
            char origChar = wordChars[i];
            // Try every letter of the alphabet on position i
            for(char c: ALPHABET){
                wordChars[i] = c;
                correctWord = new String(wordChars);
                if(table.get(correctWord) != null &&
                        corrections.get(correctWord) == null &&
                        word.compareTo(correctWord) != 0){

                    result.append(correctWord).append(", ");
                    corrections.insert(correctWord, true);
                }
            }
            wordChars[i] = origChar;
        }
        // Remove trailing comma and space
        if (result.length() > 1)
            result.delete(result.length() - 2, result.length() - 1);

        return result.toString();
    }

    /**
     * Get the current load factor.
     * @return the current load factor.
     */
    public double getLoadFactor(){
        return table.getLoadFactor();
    }

    /**
     * Total number of collisions found.
     * @return Total number of collisions found.
     */
    public int getTotalCollisions(){
        return table.getTotalCollisions();
    }

    /**
     * Get the average chain length found during insertions/lookups.
     * @return average chain length found during insertions/lookups.
     */
    public double getAverageChainLength(){
        return table.getAverageChainLength();
    }

    public int getMaxChainLength(){
        return table.getMaxChainLength();
    }

}
