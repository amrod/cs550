package edu.sju;

public class KDTree {

    Node root;
    boolean addReturn;

    public class Node{
        Point2D data;
        Node left;
        Node right;

        public Node(double x, double y){
            this.data = new Point2D(x, y);
            this.left = null;
            this.right = null;
        }

        public Node(Point2D p){
            this.data = p;
            this.left = null;
            this.right = null;
        }
    }

    public  KDTree(){
        root = null;
    }

    public KDTree(double x, double y) {
        this.root = new Node(x, y);
    }

    public KDTree(Point2D p){
        this.root = new Node(p);
    }

    public KDTree(double x, double y, KDTree left, KDTree right) {
        root = new Node(x, y);
        if (left != null)
            this.root.left = left.root;
        if (right != null)
            this.root.right = right.root;
    }

    public boolean equals(KDTree other){
        return equals(root, other.root);
    }

    private boolean equals(Node localRoot, Node otherRoot){
        if (localRoot == null && otherRoot == null)
            return true;
        else if (localRoot == null || otherRoot == null)
            return false;

        if (localRoot.data.equals(otherRoot.data)){
            return (equals(localRoot.left, otherRoot.left) &&
                    equals(localRoot.right, otherRoot.right));
        }
        return false;
    }

    public void insert(Point2D p){
        root = insert(root, p, 0);
    }

    private Node insert(Node localRoot, Point2D p, int depth){
        int axis = depth % Point2D.getSize();

        if (localRoot == null){
            addReturn = true;
            return new Node(p);
        }

        if (p.getAtIndex(axis) < this.root.data.getAtIndex(axis))
            localRoot.left = insert(localRoot.left, p, depth + 1);
        else
            localRoot.right = insert(localRoot.right, p, depth + 1);

        return localRoot;
    }

}
