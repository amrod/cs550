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

    /**
     * Builds a text representation of this tree using a preorder traversal.
     * @return The String representation of the tree.
     */
    public String toString(){
        StringBuilder stb = new StringBuilder();
        preOrderToString(root, stb, 0);
        return stb.toString();
    }

    /**
     * Creates a text representation of the tree rooted at localRoot using a
     * preorder traversal.
     * @param localRoot Root of the tree to build string representation.
     * @param stb StringBuilder instance where to build the text representation.
     * @param depth Depth of the current node within the tree.
     */
    private void preOrderToString(Node localRoot, StringBuilder stb, int depth){

        for (int i = 0; i < depth; i++)
            stb.append("\t");

        if (localRoot == null){
            stb.append("null\n");
        }else {
            stb.append(String.format("(%1$s, %2$s)\n", localRoot.data.x,
                    localRoot.data.y));
            preOrderToString(localRoot.left, stb, depth + 1);
            preOrderToString(localRoot.right, stb, depth + 1);
        }
    }

    /**
     * Finds the point with the smallest value in the d-th dimension. Let d=0
     * represent the x dimension, d=1 represent the y dimension.
     * @param d Dimension
     * @return The pint with the smallest value on dimension d found so
     *                 far, or null if tree is empty.
     */
    public Point2D findMin(int d){
        if (root == null)
            return null;
        else
            return findMin(root, d, root.data);
    }

    /**
     *  Recursively finds the point with the smallest value in the d-th
     *  dimension. Let d=0 represent the x dimension, d=1 represent the y
     *  dimension.
     * @param localRoot Root of the tree to analyze.
     * @param d Dimension
     * @param smallest The pint with the smallest value on dimension d found so
     *                 far.
     * @return The point with the smallest value in dimension d found in the
     * tree.
     */
    private Point2D findMin(Node localRoot, int d, Point2D smallest){
        if (localRoot == null)
            return smallest;

        if (localRoot.data.getAtIndex(d) < smallest.getAtIndex(d))
            smallest = localRoot.data;

        smallest = findMin(localRoot.left, d, smallest);
        smallest = findMin(localRoot.right, d, smallest);

        return smallest;
    }

    /**
     * Finds the point with the largest value in the d-th dimension. Let d=0
     * represent the x dimension, d=1 represent the y dimension.
     * @param d Dimension
     * @return The pint with the largest value on dimension d found so
     *                 far, or null if tree is empty.
     */
    public Point2D findMax(int d){
        if (root == null)
            return null;
        else
            return findMax(root, d, root.data);
    }

    /**
     *  Recursively finds the point with the largest value in the d-th
     *  dimension. Let d=0 represent the x dimension, d=1 represent the y
     *  dimension.
     * @param localRoot Root of the tree to analyze.
     * @param d Dimension
     * @param largest The pint with the largest value on dimension d found so
     *                 far.
     * @return The point with the largest value in dimension d found in the
     * tree.
     */
    private Point2D findMax(Node localRoot, int d, Point2D largest){
        if (localRoot == null)
            return largest;

        if (localRoot.data.getAtIndex(d) > largest.getAtIndex(d))
            largest = localRoot.data;

        largest = findMax(localRoot.left, d, largest);
        largest = findMax(localRoot.right, d, largest);

        return largest;
    }
}
