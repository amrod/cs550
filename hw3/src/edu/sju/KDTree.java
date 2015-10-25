package edu.sju;

public class KDTree {

    Node root;
    boolean addReturn;

    public class Node{
        Point2D data;
        Node left;
        Node right;

        /**
         * Constructor creates a Node given the x and y coordinates.
         * @param x Coordinate on x axis
         * @param y Coordinate on y axis
         */
        public Node(double x, double y){
            this.data = new Point2D(x, y);
            this.left = null;
            this.right = null;
        }

        /**
         * Constructor creates a Node given the Points2D coordinate pair.
         * @param p Previously-instantiated Point2D
         */
        public Node(Point2D p){
            this.data = p;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Constructor creates an KDTree.
     */
    public  KDTree(){
        root = null;
    }

    /**
     * Constructor creates a KDTree with a single node.
     * @param x Coordinate on x axis.
     * @param y Coordinate on y axis.
     */
    public KDTree(double x, double y) {
        this.root = new Node(x, y);
    }

    /**
     * Constructor creates a KDTree given the Point2D coordinate pair.
     * @param p Previously-instantiated Point2D
     */
    public KDTree(Point2D p){
        this.root = new Node(p);
    }

    /**
     * Constructor creates a KDTree given the x and y coordinates and left and
     * right subtrees.
     * @param x Coordinate on x axis
     * @param y Coordinate on y axis
     * @param left Left subtree
     * @param right Right subtree
     */
    public KDTree(double x, double y, KDTree left, KDTree right) {
        root = new Node(x, y);
        if (left != null)
            this.root.left = left.root;
        if (right != null)
            this.root.right = right.root;
    }

    /**
     * Compares this tree with other and returns true if they are equal, false
     * otherwise.
     * @param other The KDTree to compare against.
     * @return true if the trees are identical, false otherwise.
     */
    public boolean equals(KDTree other){
        return equals(root, other.root);
    }

    /**
     * Recursively compares two trees starting from the given roots.
     * @param localRoot Root node of local tree.
     * @param otherRoot Root node of tree to compare against.
     * @return true if the trees are identical, false otherwise.
     */
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

    /**
     * Inserts a new node in this tree.
     * @param p Coordinate pair to initialize the new node with.
     */
    public void insert(Point2D p){
        root = insert(root, p, 0);
    }

    /**
     * Inserts a new node in tree rooted at localRoot.
     * @param localRoot Root of tree where to insert the node.
     * @param p Coordinate pair to initialize the new node with.
     * @param depth current depth in the tree.
     * @return The root of the tree received, with the new node added.
     */
    private Node insert(Node localRoot, Point2D p, int depth){
        int axis = depth % Point2D.getSize();

        if (localRoot == null){
            addReturn = true;
            return new Node(p);
        }

        if (p.getAtIndex(axis) < localRoot.data.getAtIndex(axis))
            localRoot.left = insert(localRoot.left, p, depth + 1);
        else
            localRoot.right = insert(localRoot.right, p, depth + 1);

        return localRoot;
    }

    /**
     * Search for coordinates p in this tree.
     * @param point Coordinate pair to search for.
     * @return The point if found, null otherwise.
     */
    public Point2D search(Point2D point){
        return search(root, point, 0);
    }

    /**
     * Search for coordinates p in tree rooted at localRoot.
     * @param localRoot Root of tree where to search.
     * @param p Coordinate pair to search for.
     * @param depth current depth in the tree.
     * @return The point if found, null otherwise.
     */
    private Point2D search(Node localRoot, Point2D p, int depth){
        int axis = depth % Point2D.getSize();

        if (localRoot == null)
            return null;

        if (localRoot.data.equals(p))
            return new Point2D(localRoot.data.x, localRoot.data.y);

        if (p.getAtIndex(axis) < localRoot.data.getAtIndex(axis))
            return search(localRoot.left, p, depth + 1);
        else
            return search(localRoot.right, p, depth + 1);
    }
}
