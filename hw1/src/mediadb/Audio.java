package mediadb;

public abstract class Audio extends Media{

    private String[] guestArtists;
    private String producer = "";
    private String format = "";

    private int rating = 0;

    public Audio() {}

    public Audio(String title){
        this.setTitle(title);
    }

    public String getFormat(){
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setYourRating(int r) {
        rating = r;
    }

    public String[] getGuestArtists() {
        return guestArtists;
    }

    public void setGuestArtists(String[] guestArtists) {
        this.guestArtists = guestArtists;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String toString(){
        return String.format("<Audio: %s>", getTitle());
    }
}
