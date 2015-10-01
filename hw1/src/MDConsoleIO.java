import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import mediadb.*;

/** This class is a possible user interface for the Media Database
 *   that uses the console to display the menu of command choices.
 */
public class MDConsoleIO {

    /** A reference to the MediaDatabase object to be processed.
     Globally available to the command-processing methods.
     */
    private MediaDatabase theDatabase = null;

    /** Scanner to read from input console. */
    private Scanner scIn = null;

    // Constructor
    /** Default constructor. */
    public MDConsoleIO() {
        scIn = new Scanner(System.in);
    }

    // Methods
    /** Method to display the command choices and process user
     commands.
     pre:  The database exists and has been loaded with data.
     post: The database is updated based on user commands.
     @param theMediaDatabase A reference to the MediaDatabase
     to be processed
     */
    public void processCommands(MediaDatabase theMediaDatabase) throws IOException{
        String[] commands = {
                "Add Entry",
                "Look Up By Title",
                "Remove Entry",
                "Save Directory",
                "Reload Directory",
                "Exit"};

        theDatabase = theMediaDatabase;
        int choice;
        do {
            for (int i = 0; i < commands.length; i++) {
                System.out.println("Select " + i + ": "
                        + commands[i]);
            }
            try {
                choice = scIn.nextInt(); // Read the next choice.
                scIn.nextLine(); // Skip trailing newline.
                switch (choice) {
                    case 0:
                        doAddEntry();
                        break;
                    case 1:
                        Media m = doLookupByTitle();
                        if (m != null){
                            m.playMedia();
                            System.out.print("\n");
                        }
                        break;
                    case 2:
                        doRemoveEntry();
                        break;
                    case 3:
                        doSave();
                        break;
                    case 4:
                        theDatabase = doLoad();
                        System.out.println("Database reloaded!");
                        break;
                    case 5:  // Exit
                        System.exit(0);
                        break;
                    default:
                        System.out.println("*** Invalid choice "
                                + choice
                                + " - try again!");
                }
            }
            catch (InputMismatchException ex) {
                System.out.println("*** Incorrect data entry - "
                        + "enter an integer between 0 and "
                        + (commands.length-1));
                scIn.nextLine(); // Discard bad input.
                choice = -1;
            }
        }
        while (choice != commands.length - 1);

    }

    /** Method to add an entry.
     pre:  The database exists and has been loaded with data.
     post: A new media is added.
     */
    private void doAddEntry() {
        // Request Media type
        System.out.println("Enter media type (DVD/CD/Cassette/VHS)");
        String type = scIn.nextLine().toUpperCase();
        if (type.equals(""))
            return;

        switch (type) {
            case "DVD":
                addDVD();
                break;
            case "VHS":
                addVHS();
                break;
            case "CD":
                addCD();
                break;
            case "CASSETTE":
                addCassette();
                break;
            default:
                System.out.println(String.format("Unknown type '%s'", type));
                return;
        }
        System.out.println("");
    }

    /**
     * Prompts user for the details of a new DVD and adds the data to the database.
     */
    private void addDVD() {
        String input;
        String[] inputArr;
        DVD dvd = new DVD();

        promptVideoDetails(dvd);

        System.out.println("Enter Special Features (comma-separated) or type NONE:");
        input = scIn.nextLine();
        if (input.equals("")) return;
        inputArr = Util.parseCSV(input, "NONE");
        dvd.setSpecialFeatures(inputArr);

        String[] yn = {"Y", "N"};

        while (true) {
            System.out.println("Is it Wide Screen (Y/N)?");
            input = scIn.nextLine();
            if (input.equals("")) return;
            if (!Arrays.asList(yn).contains(input)) continue;
            input = input.replace("Y", "true").replace("N", "false");
            dvd.setWideScreenFormat(Boolean.parseBoolean(input));
            break;
        }

        while (true) {
            System.out.println("Is it TV format (Y/N)?");
            input = scIn.nextLine();
            if (input.equals("")) return;
            if (!Arrays.asList(yn).contains(input)) continue;
            input = input.replace("Y", "true").replace("N", "false");
            dvd.setTVFormat(Boolean.parseBoolean(input));
            break;
        }

        System.out.println("Enter Sound options (comma-separated), or type NONE:");
        input = scIn.nextLine();
        if (input.equals("")) return;
        inputArr = Util.parseCSV(input, "NONE");
        dvd.setSoundOptions(inputArr);

        theDatabase.addEntry(dvd);
    }

    /**
     * Prompts user for the details of a new VHS and adds the data to the database.
     */
    private void addVHS() {
        String input;
        String[] inputArr;
        VHS vhs;
        vhs = new VHS();

        promptVideoDetails(vhs);

        System.out.println("Enter Trailers (comma-separated) or type NONE:");
        input = scIn.nextLine();
        if (input.equals("")) return;
        inputArr = Util.parseCSV(input, "NONE");
        vhs.setTrailers(inputArr);

        theDatabase.addEntry(vhs);
    }

    /**
     * Prompts user for the details of a new CD and adds the data to the database.
     */
    private void addCD(){
        String input;
        String[] inputArr;
        CD cd;
        cd = new CD();

        promptAudioDetails(cd);

        while (true) {
            System.out.println("Enter Format (1) Audio CD, (2) Super Audio CD:");
            input = scIn.nextLine();
            String[] opts = {"1", "2"};
            if (input.equals("")) return;
            if (!Arrays.asList(opts).contains(input)) continue;
            input = input.replace("1", "Audio CD").replace("2", "Super Audio CD");
            cd.setFormat(input);
            break;
        }

        System.out.println("Enter Bonus Tracks (comma-separated) or type NONE:");
        input = scIn.nextLine();
        if (input.equals("")) return;
        inputArr = Util.parseCSV(input, "NONE");
        cd.setBonusTracks(inputArr);

        theDatabase.addEntry(cd);
    }

    /**
     * Prompts user for the details of a new Cassette and adds the data to the database.
     */
    private void addCassette(){
        String input;
        Cassette cassette = new Cassette();

        promptAudioDetails(cassette);

        while (true) {
            System.out.println("Enter Format (C60/C90):");
            input = scIn.nextLine();
            String[] opts = {"C60", "C90"};
            if (input.equals("")) return;
            if (!Arrays.asList(opts).contains(input)) continue;
            cassette.setFormat(input);
            break;
        }

        while (true) {
            System.out.println("Is it Two-Sided (Y/N)?");
            input = scIn.nextLine();
            String[] opts = {"Y", "N"};
            if (input.equals("")) return;
            if (!Arrays.asList(opts).contains(input)) continue;
            input = input.replace("Y", "true").replace("N", "false");
            cassette.setTwoSided(Boolean.parseBoolean(input));
            break;
        }

        theDatabase.addEntry(cassette);
    }

    /**
     * Prompts user for the details of a Video object and updates the provided
     * Video object with said details.
     * @param v An instance of the class mediadb.Video
     * @return The same Video instance received as a parameter, after being
     * updated.
     */
    private Video promptVideoDetails(Video v){
        String input;
        String[] inputArr;

        System.out.println("Enter Title:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        v.setTitle(input);

        System.out.println("Enter Lead Actor:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        v.setMajorArtist(input);

        System.out.println("Enter Playing Time:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        v.setPlayingTime(Integer.parseInt(input));

        System.out.println("Enter Supporting Actors (comma-separated) or type NONE:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        inputArr = Util.parseCSV(input, "NONE");
        v.setSupportingActors(inputArr);

        System.out.println("Enter Director:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        v.setDirector(input);

        while (true) {
            System.out.println("Enter Format (HD/SD):");
            input = scIn.nextLine();
            String[] opts = {"HD", "SD"};
            if (input.equals("")) return null;
            if (!Arrays.asList(opts).contains(input)) continue;
            v.setFormat(input);
            break;
        }

        return v;
    }

    /**
     * Prompts user for the details of a Audio object and updates the provided
     * Audio object with said details.
     * @param a An instance of the class mediadb.Audio
     * @return The same Audio instance received as a parameter, after being
     * updated.
     */
    private Audio promptAudioDetails(Audio a){
        String input;
        String[] inputArr;

        System.out.println("Enter Title:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        a.setTitle(input);

        System.out.println("Enter Main Artist:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        a.setMajorArtist(input);

        System.out.println("Enter Playing Time:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        a.setPlayingTime(Integer.parseInt(input));

        System.out.println("Enter Guest Artists (comma-separated) or type NONE:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        inputArr = Util.parseCSV(input, "NONE");
        a.setGuestArtists(inputArr);

        System.out.println("Enter Producer:");
        input = scIn.nextLine();
        if (input.equals("")) return null;
        a.setProducer(input);

        return a;
    }

    /** Method to look up by title.
     * pre:  The database has been loaded with data.
     * post: No changes made to the database.
     * @return The instance of Media found. null if none found.
     */
    private Media doLookupByTitle() {
        // Request the title.
        String theName = promptForTitle();
        if (theName == null) {
            return null; // Dialog was cancelled.
        }

        // Look up the name.
        Media m = theDatabase.lookupByTitle(theName);
        if (m != null) { // Title was found.
            return m;
        } else {
            // Display error.
            System.out.println(theName + " is not listed in the database");
            return null;
        }
    }

    private String promptForTitle(){
        System.out.println("Enter title:");
        String theName = scIn.nextLine();
        if (theName.equals("")) {
            return null; // Dialog was cancelled.
        }
        return theName;
    }

    /**
     * Removes an entry from the database. Prompts the user for the tile of
     * entry to be removed.
     */
    private void doRemoveEntry() {
        String title = promptForTitle();
        if (title != null){
            theDatabase.removeEntry(title);
        }
    }

    /** Method to save the database to the data file.
     pre:  The database has been loaded with data.
     post: The current contents of the database have been saved
     to the data file.
     */
    private void doSave() {
        theDatabase.save();
    }

    /**
     * Reads the database file and loads it into a MediaDatabase object, which
     * is then returned.
     * @return A MediaDatabase instance created from the current contents of
     * the database.txt file.
     * @throws IOException if the file database.txt is not found in the current
     * directory.
     */
    private static MediaDatabase doLoad() throws IOException{
        MediaDatabase md = new LinkedListMediaDatabase();
        md.loadData("database.txt");
        return md;
    }

    public static void main(String args[]) {
        MDConsoleIO ui = new MDConsoleIO();
        MediaDatabase md;

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        try{
            md = doLoad();
            ui.processCommands(md);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            System.out.println("Make sure the database.txt file is in: " + s);
        }
    }
}

