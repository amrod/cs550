package mediadb;

public class CD extends Audio{
    public static final String type = "CD";
    private String[] bonusTracks;

    public String[] getBonusTracks() {
        return bonusTracks;
    }

    public void setBonusTracks(String[] bonusTracks) {
        this.bonusTracks = bonusTracks;
    }

    public CD() {}

    public CD(String title) {
        super(title);
    }

    public void playMedia(){

        this.incrementPlays();
        String fmt = "%-24s%-15s";
        String comma = ", ";
        
        String[] guestSingers = getGuestArtists();
        StringBuilder guestSingersStr = new StringBuilder("");
        StringBuilder bonusTracksStr = new StringBuilder("");


        for (String guestSinger : guestSingers) 
            guestSingersStr.append(guestSinger).append(comma);        

        if (guestSingersStr.length() == 0) guestSingersStr.append("None");

        for (String bt : getBonusTracks()) 
            bonusTracksStr.append(bt).append(comma);      

        if (bonusTracksStr.length() == 0) bonusTracksStr.append("None");

        String s = "Now playing:" +
                String.format(fmt, "\nMedia Type:", CD.type) +
                String.format(fmt, "\nTitle:", getTitle()) +
                String.format(fmt, "\nSinger:", getMajorArtist()) +
                String.format(fmt, "\nPlaying Time:", getPlayingTime()) +
                String.format(fmt, "\nNumber of Plays:", getNumPlays()) +
                String.format(fmt, "\nGuest Singers:", guestSingersStr.toString().replaceAll(",\\s$", "")) +
                String.format(fmt, "\nProducer:", getProducer()) +
                String.format(fmt, "\nFormat:", getFormat()) +
                String.format(fmt, "\nBonus Tracks:", bonusTracksStr.toString().replaceAll(",\\s$", ""));

        System.out.println(s); ;
    }

    /** Generates a string representation of the media object, suitable for writing to a file.
     * @return String representation of the object.
     */
    public String dump(){
        StringBuilder sb;
        sb = new StringBuilder(String.format("%1$s\n%2$s\n%3$s\n%4$s\n%5$s\n",
                CD.type,
                this.getTitle(),
                this.getMajorArtist(),
                this.getPlayingTime(),
                this.getNumPlays()));

        sb.append(this.getGuestArtists().length).append("\n");
        if (this.getGuestArtists().length > 0)  sb.append(Util.join(this.getGuestArtists()));
        sb.append(this.getProducer()).append("\n");
        sb.append(this.getFormat()).append("\n");
        sb.append(this.getBonusTracks().length).append("\n");
        if (this.getBonusTracks().length > 0)  sb.append(Util.join(this.getBonusTracks()));

        return sb.toString();
    }

    public String toString(){
        return String.format("<CD: %s>", getTitle());
    }
}
