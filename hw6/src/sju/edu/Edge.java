package sju.edu;

/** Representation of an Edge, the relationship between two vertices. */
public class Edge {

    private double weight;
    // Destination vertex
    private int dest;
    // Source vertex
    private int src;

    /** Constructs a weighted edge with a source of src and a destination of
     *  dest. The weight is set to to w.
     *  @param src - Source vertex
     *  @param dest - Destination vertex
     *  @param w - Weight
     */
    public Edge(int src, int dest, double w) {
        this.src = src;
        this.dest = dest;
        weight = w;
    }

    /** Constructs an Edge with a source of src and a destination of dest, with
     *  a weight of 1.
     *  @param src - Source vertex
     *  @param dest - Destination vertex
     */
    public Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
        weight = 1.0;
    }

    /** Get the weight
     *  @return Weight
     */
    public double getWeight() {
        return weight;
    }

    /** Get the source
     *  @return Source
     */
    public int getSrc() {
        return src;
    }

    /** Get the destination
     *  @return Destination
     */
    public int getDest() {
        return dest;
    }

    /** Computes a hash code for an edge. The hash code is the source shifted
     *  left 16 bits exclusive or with the dest
     *  @return a hash code for an edge
     */
    @Override
    public int hashCode() {
        return (src << 16) ^ dest;
    }

    /** Compares two edges foe equality. Edges are equal if the source and
     *  destination are equal. Weight is not considered.
     *  @param other object to compare to.
     *  @return true if the edges have the same source and destination, false
     *  otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge edge = (Edge) other;
            return (src == edge.src && dest == edge.dest);
        } else {
            return false;
        }
    }

    /** Creates a String representation of the edge in the form:
     *    [(src, dest): weight]
     *  @return String representation of this edge
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[(");
        builder.append(Integer.toString(src));
        builder.append(", ");
        builder.append(Integer.toString(dest));
        builder.append("): ");
        builder.append(Double.toString(weight));
        builder.append("]");

        return builder.toString();
    }
}

