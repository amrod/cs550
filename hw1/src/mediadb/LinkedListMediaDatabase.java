package mediadb;
import java.io.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Iterator;

/**
 * This is an implementation of the MediaDatabase Interface
 * that uses a Single Linked List to store the data.
 */

public class LinkedListMediaDatabase implements MediaDatabase{

    // Data fields

    /** Known types of media */
    private final String[] MEDIA_TYPES = {"CASSETTE", "CD", "VHS", "DVD"};

    /** The Linked List to contain the media data */
    private SingleLinkedList<Media> theDatabase =
            new SingleLinkedList<>();


    /** The data file that contains the database data */
    private String sourceName = null;
    
    /** Boolean flag to indicate whether the database was
     modified since it was either loaded or saved. */
    private boolean modified = false;
    
    /** Method to load the data file.
     pre:  The database storage has been created and it is empty.
     If the file exists, it contains each field on a separate line.
     post: The data from the file is loaded into the database.
     @param sourceName The name of the data file
     */
    public void loadData(String sourceName) throws IOException {
        String line;
        ArrayList<String> lines = new ArrayList<>();
        FileReader fileReader = new FileReader(sourceName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        this.sourceName = sourceName;

        while ((line = bufferedReader.readLine()) != null){

            // Found a header line. Save lines read so far.
            if (isHeader(line) != null){
                // Create a Media object if lines has elements
                addEntry(lines);
                // Clear buffer for next entry
                lines.clear();
            }
            lines.add(line);
        }

        // Add last entry read
        addEntry(lines);

        fileReader.close();
        modified = false;
    }

    /** Add an entry.
     @param newMedia The Media object to be added
     */
    public void addEntry(Media newMedia){
        theDatabase.add(newMedia);
        modified = true;
    }

    /** If the ArrayList lines is not empty, creates a new instance of Media
     * and adds it to the internal database.
     * @param lines A list of lines of text from a flat database text file,
     *              corresponding to a single media item.
     */
    private void addEntry(ArrayList<String> lines){
        if (!lines.isEmpty()) {
            Media m;
            m = createMedia(lines);
            theDatabase.add(m);
        }
        modified = true;
    }

    /** Selects the Media subclass corresponding to the block of lines and
     * returns the appropriate Media object.
     * @param lines A list of lines of text from a flat database text file,
     *              corresponding to a single media item.
     * @return An object of type Media representing the block of text from
     * lines.
     */
    private Media createMedia(ArrayList<String> lines){
        String type = lines.get(0).toUpperCase(); // Case-insensitive

        if (type.equals(MEDIA_TYPES[0]))
            return parseCassette(lines);
        else if (type.equals(MEDIA_TYPES[1]))
            return parseCD(lines);
        else if (type.equals(MEDIA_TYPES[2]))
            return parseVHS(lines);
        else if (type.equals(MEDIA_TYPES[3]))
            return parseDVD(lines);
        return null;
    }

    private Cassette parseCassette(ArrayList<String> lines){
        int j;
        ArrayList<String> strArr;

        Cassette c = new Cassette(lines.get(1));
        c.setMajorArtist(lines.get(2));
        c.setPlayingTime(Integer.parseInt(lines.get(3)));
        c.setNumPlays(Integer.parseInt(lines.get(4)));

        j = 5;  // Index for number of guest singers
        strArr = getMultipleLineField(lines, j++);
        j = j + strArr.size(); // Move index forward the number of lines read.

        c.setGuestArtists(strArr.toArray(new String[strArr.size()]));
        c.setProducer(lines.get(j++));
        c.setFormat(lines.get(j++));
        c.setTwoSided(Boolean.parseBoolean(lines.get(j)));

        return c;
    }

    private CD parseCD(ArrayList<String> lines){
        int j;
        ArrayList<String> strArr;

        CD c = new CD(lines.get(1));
        c.setMajorArtist(lines.get(2));
        c.setPlayingTime(Integer.parseInt(lines.get(3)));
        c.setNumPlays(Integer.parseInt(lines.get(4)));

        j = 5;  // Index for number of guest singers
        strArr = getMultipleLineField(lines, j++);
        j = j + strArr.size(); // Move index forward the number of lines read.

        c.setGuestArtists(strArr.toArray(new String[strArr.size()]));
        c.setProducer(lines.get(j++));
        c.setFormat(lines.get(j++));

        strArr = getMultipleLineField(lines, j);
        c.setBonusTracks(strArr.toArray(new String[strArr.size()]));

        return c;
    }

    private VHS parseVHS(ArrayList<String> lines){
        int j;
        ArrayList<String> strArr;

        VHS v = new VHS(lines.get(1));
        v.setMajorArtist(lines.get(2));
        v.setPlayingTime(Integer.parseInt(lines.get(3)));
        v.setNumPlays(Integer.parseInt(lines.get(4)));

        j = 5;  // Index for number of supporting actors
        strArr = getMultipleLineField(lines, j++);
        j = j + strArr.size(); // Move index forward the number of lines read.

        v.setSupportingActors(strArr.toArray(new String[strArr.size()]));
        v.setDirector(lines.get(j++));
        v.setFormat(lines.get(j++));

        strArr = getMultipleLineField(lines, j);

        v.setTrailers(strArr.toArray(new String[strArr.size()]));
        return v;
    }

    /**
     * Parses the contents of a database file corresponding to a DVD which
     * have been provided in the form of an ArrayList
     * @param lines
     * @return
     */
    private DVD parseDVD(ArrayList<String> lines){
        int j;
        ArrayList<String> strArr;

        DVD d = new DVD(lines.get(1));
        d.setMajorArtist(lines.get(2));
        d.setPlayingTime(Integer.parseInt(lines.get(3)));
        d.setNumPlays(Integer.parseInt(lines.get(4)));

        j = 5;  // Index for number of supporting actors
        strArr = getMultipleLineField(lines, j++);
        j = j + strArr.size(); // Move index forward the number of lines read.

        d.setSupportingActors(strArr.toArray(new String[strArr.size()]));
        d.setDirector(lines.get(j++));
        d.setFormat(lines.get(j++));

        // After Format comes Special Features, possibly multiple lines
        strArr = getMultipleLineField(lines, j++);
        j = j + strArr.size(); // Move index forward the number of lines read.

        d.setSpecialFeatures((strArr.toArray(new String[strArr.size()])));
        d.setWideScreenFormat(Boolean.parseBoolean(lines.get(j++)));
        d.setTVFormat(Boolean.parseBoolean(lines.get(j++)));

        // After Format comes Sound Options, possibly multiple lines
        strArr = getMultipleLineField(lines, j);
        d.setSoundOptions(strArr.toArray(new String[strArr.size()]));

        return d;
    }

    /** Method to save the database.
     pre:  The database has been loaded with data.
     post: Contents of database written back to the file in the
     form of the source file,
     modified is reset to false.
     */
    public void save(){
        StringBuilder sb = new StringBuilder();

        for (Media m: theDatabase)
            sb.append(m.dump());

        try {
            FileWriter fw = new FileWriter("database.txt");
            fw.write(sb.toString());
            fw.close();

        } catch (IOException e){
            System.out.println("Error writing file database.txt");
            return;
        }

        modified = false;
        System.out.println("Saved!");
    }

    /** Look up an entry by title.
     @param title The title to look up
     @return The Media object if found, null otherwise
     */
    public Media lookupByTitle(String title){
        for (Media m: theDatabase){
            if (m.getTitle().toUpperCase().equals(title.toUpperCase()))
                return m;
        }
        return null;
    }


    /** Remove an entry from the database.
     @param title The title of the media to be removed
     @return The object that is removed. If not in database, null is
     returned
     */
    public Media removeEntry(String title){
        Media m;
        Iterator<Media> mediaIter = theDatabase.iterator();

        while (mediaIter.hasNext()){
            m = mediaIter.next();
            // Case-insensitive check
            if (m.getTitle().toUpperCase().equals(title.toUpperCase())) {
                mediaIter.remove();
                this.modified = true;
                return m;
            }
        }
        return null;
    }

    /** Parses a field with multiple elements from the ArrayList representation
     * of the database. nextPos must be the index of the target field's count
     * line.
     * @param lines An ArrayList of Strings representing lines in the database file.
     * @param nextPos The index of the target field's count line.
     * @return An ArrayList of strings containing all elements of the target field.
     */
    private ArrayList<String> getMultipleLineField(ArrayList<String> lines, int nextPos){

        ArrayList<String> strArrLst = new ArrayList<>();
        int offset;
        int startIndex;

        offset = Integer.parseInt(lines.get(nextPos));
        startIndex = nextPos;

        for (++nextPos; nextPos <= startIndex + offset && nextPos < lines.size(); nextPos++){
            strArrLst.add(lines.get(nextPos));
        }

        return strArrLst;
    }

    /** Determines whether the given string is a know Media type.
     *
     * @param s A string to compare against known Media types.
     * @return The same string passed in if it is found to be a Media type,
     * null otherwise.
     */
    private String isHeader(String s){
        for (String type : MEDIA_TYPES){
            if (s.toUpperCase().equals(type)) return s;
        }
        return null;
    }
}
