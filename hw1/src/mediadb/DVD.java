package mediadb;

public class DVD extends Video{
    public static final String type = "DVD";
    private String[] specialFeatures;
    private boolean wideScreenFormat;
    private boolean TVFormat;
    private String[] soundOptions;

    public DVD() {}

    public DVD(String title) {
        super(title);
    }

    public String[] getSpecialFeatures(){
        return specialFeatures;
    }

    public void setSpecialFeatures(String[] specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public boolean isWideScreenFormat() {
        return wideScreenFormat;
    }

    public void setWideScreenFormat(boolean wideScreenFormat) {
        this.wideScreenFormat = wideScreenFormat;
    }

    public boolean isTVFormat() {
        return TVFormat;
    }

    public void setTVFormat(boolean TVFormat) {
        this.TVFormat = TVFormat;
    }

    public String[] getSoundOptions(){
        return soundOptions;
    }

    public void setSoundOptions(String[] soundOptions) {
        this.soundOptions = soundOptions;
    }

    public void playMedia(){

        this.incrementPlays();
        String fmt = "%-24s%-15s";
        String comma = ", ";

        String[] supActors = getSupportingActors();
        StringBuilder supActorsStr = new StringBuilder();
        String[] spcFeats = getSpecialFeatures();
        StringBuilder spcFeatStr = new StringBuilder();
        String[] soundOpts = getSoundOptions();
        StringBuilder soundOptStr = new StringBuilder();

        for (String supActor : supActors)
            supActorsStr.append(supActor).append(comma);

        if (supActorsStr.length() == 0) supActorsStr.append("None");

        for (String spcFeat : spcFeats)
            spcFeatStr.append(spcFeat).append(comma);

        if (spcFeatStr.length() == 0) spcFeatStr.append("None");

        for (String soundOpt : soundOpts)
            soundOptStr.append(soundOpt).append(comma);

        if (soundOptStr.length() == 0) soundOptStr.append("None");

        String s = "Now playing:" +
                    String.format(fmt, "\nMedia Type:", DVD.type) +
                    String.format(fmt, "\nTitle:", getTitle()) +
                    String.format(fmt, "\nLead Actor:", getMajorArtist()) +
                    String.format(fmt, "\nPlaying Tim1e:", getPlayingTime()) +
                    String.format(fmt, "\nNumber of Plays:", getNumPlays()) +
                    String.format(fmt, "\nSupporting Actors:", supActorsStr.toString().replaceAll(",\\s$", "")) +
                    String.format(fmt, "\nDirector:", getDirector()) +
                    String.format(fmt, "\nFormat:", getFormat()) +
                    String.format(fmt, "\nSpecial Features:", spcFeatStr.toString().replaceAll(",\\s$", "")) +
                    String.format(fmt, "\nWide Screen Format:", String.valueOf(isWideScreenFormat())) +
                    String.format(fmt, "\nTV Format:", String.valueOf(isTVFormat())) +
                    String.format(fmt, "\nSound Options:", soundOptStr.toString().replaceAll(",\\s$", ""));

        System.out.println(s);
    }

    /** Generates a string representation of the media object, suitable for writing to a file.
     * @return String representation of the object.
     */
    public String dump(){
        StringBuilder sb;
        sb = new StringBuilder(String.format("%1$s\n%2$s\n%3$s\n%4$s\n%5$s\n",
                                DVD.type,
                                this.getTitle(),
                                this.getMajorArtist(),
                                this.getPlayingTime(),
                                this.getNumPlays()));

        sb.append(this.getSupportingActors().length).append("\n");
        if (this.getSupportingActors().length > 0) sb.append(Util.join(this.getSupportingActors()));
        sb.append(this.getDirector()).append("\n");
        sb.append(this.getFormat()).append("\n");
        sb.append(this.getSpecialFeatures().length).append("\n");
        if (this.getSpecialFeatures().length > 0) sb.append(Util.join(this.getSpecialFeatures()));
        sb.append(this.isWideScreenFormat()).append("\n");
        sb.append(this.isTVFormat()).append("\n");
        sb.append(this.getSoundOptions().length).append("\n");
        if (this.getSoundOptions().length > 0) sb.append(Util.join(this.getSoundOptions()));

        return sb.toString();
    }

    public String toString(){
        return String.format("<DVD: %s>", getTitle());
    }
}
