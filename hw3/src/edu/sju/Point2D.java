package edu.sju;

class Point2D{
    double x;
    double y;

    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point2D other){
        return (other.x == this.x && other.y == this.y);
    }

    public double getAtIndex(int i){
        if (i == 0)
            return x;
        else if(i == 1)
            return y;
        else
            throw new IndexOutOfBoundsException();
    }

    public static int getSize(){
        return 2;
    }
}
