package mediadb;

public class VHS extends Video{
    public static final String type = "VHS";
    private String[] trailers;

    public VHS() {}

    public VHS(String title) {
        super(title);
    }

    public String[] getTrailers(){
        return trailers;
    }

    public void setTrailers(String[] trailers) {
        this.trailers = trailers;
    }

    public void playMedia(){

        this.incrementPlays();
        String fmt = "%-24s%-15s";
        String comma = ", ";

        String[] supActors = getSupportingActors();
        StringBuilder supActorsStr = new StringBuilder("");
        String[] trailers = getTrailers();
        StringBuilder trailersStr = new StringBuilder("");

        for (String supActor : supActors)
            supActorsStr.append(supActor).append(comma);

        if (supActorsStr.length() == 0) supActorsStr.append("None");

        for (String trailer : trailers)
            trailersStr.append(trailer).append(comma);

        if (trailersStr.length() == 0) trailersStr.append("None");

        String s = "Now playing:" +
                String.format(fmt, "\nMedia Type:", VHS.type) +
                String.format(fmt ,"\nTitle:", getTitle()) +
                String.format(fmt, "\nLead Actor:", getMajorArtist()) +
                String.format(fmt, "\nPlaying Time:", getPlayingTime()) +
                String.format(fmt, "\nNumber of Plays:", getNumPlays()) +
                String.format(fmt, "\nSupporting Actors:", supActorsStr.toString().replaceAll(",\\s$", "")) +
                String.format(fmt, "\nDirector:", getDirector()) +
                String.format(fmt, "\nFormat:", getFormat()) +
                String.format(fmt, "\nTrailers:", trailersStr.toString().replaceAll(",\\s$", ""));

        System.out.println(s);
    }

    /** Generates a string representation of the media object, suitable for writing to a file.
     * @return String representation of the object.
     */
    public String dump(){
        StringBuilder sb;
        sb = new StringBuilder(String.format("%1$s\n%2$s\n%3$s\n%4$s\n%5$s\n",
                                  VHS.type,
                                  this.getTitle(),
                                  this.getMajorArtist(),
                                  this.getPlayingTime(),
                                  this.getNumPlays()));

        sb.append(this.getSupportingActors().length).append("\n");
        if (this.getSupportingActors().length > 0) sb.append(Util.join(this.getSupportingActors()));
        sb.append(this.getDirector()).append("\n");
        sb.append(this.getFormat()).append("\n");
        sb.append(this.getTrailers().length).append("\n");
        if (this.getTrailers().length > 0) sb.append(Util.join(this.getTrailers()));

        return sb.toString();
    }

    public String toString(){
        return String.format("<VHS: %s>", getTitle());
    }

}
