package edu.sju;

public class KDTree {

    Point2D data;
    KDTree left;
    KDTree right;

    public KDTree(double x, double y) {
        data = new Point2D(x, y);
        left = null;
        right = null;
    }

    public KDTree(double x, double y, KDTree left, KDTree right) {
        data = new Point2D(x, y);
        this.left = left;
        this.right = right;
    }

    public boolean equals(KDTree other){
        if (this.left == null && other.left != null ||
                this.left != null && other.left == null) {
            return false;
        }

        if (this.right == null && other.right != null ||
                this.right != null && other.right == null) {
            return false;
        }

        if (this.data.equals(other.data)) {
            boolean lEq = true;
            boolean rEq = true;

            if (this.left != null){
                lEq = this.left.equals(other.left);
            }

            if (this.right != null){
                rEq = this.right.equals(other.right);
            }

            return (lEq && rEq);
        }

        return false;

    }

    public void insert(Point2D p){

    }

}
