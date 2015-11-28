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
