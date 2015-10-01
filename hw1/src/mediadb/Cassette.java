package mediadb;

public class Cassette extends Audio{

    public static final String type = "Cassette";
    private boolean twoSided;

    public Cassette() {}

    public Cassette(String title) {
        super(title);
    }

    public boolean isTwoSided() {
        return twoSided;
    }

    public void setTwoSided(boolean twoSided) {
        this.twoSided = twoSided;
    }

    public void playMedia(){

        this.incrementPlays();
        String fmt = "%-24s%-15s";
        String comma = ", ";
        
        String[] guestSingers = getGuestArtists();
        StringBuilder guestSingersStr = new StringBuilder("");

        for (String guestSinger : guestSingers)
            guestSingersStr.append(guestSinger).append(comma);

        if (guestSingersStr.length() == 0) guestSingersStr.append("None");


        String s = "Now playing:" +
                    String.format(fmt, "\nMedia Type:", Cassette.type) +
                    String.format(fmt, "\nTitle:", getTitle()) +
                    String.format(fmt, "\nSinger:", getMajorArtist()) +
                    String.format(fmt, "\nPlaying Time:", getPlayingTime()) +
                    String.format(fmt, "\nNumber of Plays:", getNumPlays()) +
                    String.format(fmt, "\nGuest Singers:", guestSingersStr.toString().replaceAll(",\\s$", "")) +
                    String.format(fmt, "\nProducer:", getProducer()) +
                    String.format(fmt, "\nFormat:", getFormat()) +
                    String.format(fmt, "\nTwo Sides:", String.valueOf(isTwoSided()));

        System.out.println(s);
    }

    /** Generates a string representation of the media object, suitable for writing to a file.
     * @return String representation of the object.
     */
    public String dump(){
        StringBuilder sb;
        sb = new StringBuilder(String.format("%1$s\n%2$s\n%3$s\n%4$s\n%5$s\n",
                                Cassette.type,
                                this.getTitle(),
                                this.getMajorArtist(),
                                this.getPlayingTime(),
                                this.getNumPlays()));

        sb.append(this.getGuestArtists().length).append("\n");
        if (this.getGuestArtists().length > 0)  sb.append(Util.join(this.getGuestArtists()));
        sb.append(this.getProducer()).append("\n");
        sb.append(this.getFormat()).append("\n");
        sb.append(this.isTwoSided()).append("\n");

        return sb.toString();
    }

    public String toString(){
        return String.format("<Cassette: %s>", getTitle());
    }
}
