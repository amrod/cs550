package mediadb;


public abstract class Media{
    private String title;
    private String majorArtist;
    private int playingTime;
    private int numPlays;

    public abstract void playMedia();
    public abstract void setYourRating(int r);
    public abstract String dump();

    public int getNumPlays(){
        return numPlays;
    }

    public String getTitle(){
        return title;
    }

    public String getMajorArtist(){
        return majorArtist;
    }

    public int getPlayingTime(){
        return playingTime;
    }

    public String toString(){
        return String.format("<Media: %s>", getTitle());
   }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMajorArtist(String majorArtist) {
        this.majorArtist = majorArtist;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public void setNumPlays(int numPlays) {
        this.numPlays = numPlays;
    }

    public void incrementPlays() {
        this.numPlays++;
    }
}