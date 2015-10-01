package mediadb;

public abstract class Video extends Media{

    private String[] supportingActors;
    private String format = "";
    private String director = "";
    private int rating = 0;

    public Video(){}

    public Video(String title){
        this.setTitle(title);
    }

    public String toString(){
        return String.format("<Video: %s>", getTitle());
    }

    public String getDirector(){
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFormat(){
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String[] getSupportingActors(){
        return supportingActors;
    }

    public void setSupportingActors(String[] supportingActors) {
        this.supportingActors = supportingActors;
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

}
