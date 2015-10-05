// A. Rodriguez: Based on Koffman and Wolfgang's SingleLinkedList. Modified into a double linked circular list.
package edu.sju;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SingleLinkedList is a class that provides some of the
 * capabilities required by the List interface using
 * a single linked list data structure.
 * Only the following methods are provided:
 * get, set, add, remove, size, toString
 * @author Koffman and Wolfgang 
 */
public class LinkedList<E> implements Iterable<E>{


    // Nested Class
    /*<listing chapter="2" number="1">*/
    /** A Node is the building block for the SingleLinkedList */
    private static class Node<E> {

        /** The data value. */
        private E data;
        /** The links */
        private Node<E> next = null;
        private Node<E> previous = null;

        /**
         * Construct a node with the given data value and link
         * @param data - The data value
         * @param next - link to the next node
         * @param previous - link to the previous node
         */
        public Node(E data, Node<E> previous, Node<E> next) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

        /**
         * Construct a node with the given data value
         * @param data - The data value 
         */
        public Node(E data) {
            this(data, null, null);
        }
    }

    private class LLIterator implements Iterator<E> {
        Node<E> nextNode;
        Node<E> lastReturned;
        Node<E> beforeLastReturned;

        public LLIterator(){
            nextNode = head;
            lastReturned = null;
//            beforeLastReturned = null;
        }

        /**
         * Advances the iterator to the next node and returns the consumed
         * node's data value.
         * @return A data object of type E.
         */
        public E next(){
            if (!hasNext())
                throw new NoSuchElementException();

//            if (lastReturned != null)
//                beforeLastReturned = lastReturned;

            lastReturned = nextNode;
            nextNode = nextNode.next;

            return lastReturned.data;
        }

        /**
         * Test if there are any more nodes left in the LinkedList to iterate
         * over.
         * @return true if there are nodes left, false if we're already at the
         * tail.
         */
        public boolean hasNext(){
            return lastReturned != tail;
        }

        /**
         * Removes the last returned node from the LinkedList.
         */
        public void remove(){
            if (lastReturned == null)
                throw new IllegalStateException("The next() method has not " +
                        "been called or remove() was already called.");

            // Next called only once, so lastReturned is 1st element
            if (lastReturned.previous == tail) {
                head = nextNode;
                tail.next = nextNode;
            }
            else
                lastReturned.previous.next = nextNode;

            size--; // Decrease list size after removing an element.
            lastReturned = null;
        }


    }

    // Data fields
    /** A reference to the head of the list */
    private Node<E> head = null;
    /** A reference to the tail of the list */
    private Node<E> tail = null;
    /** The size of the list */
    private int size = 0;

    // Helper Methods
    /** Insert an item as the first item of the list.
     *	@param item The item to be inserted
     */
    private void addFirst(E item) {
        if (head == null){
            head = new Node<>(item);
            head.next = head;
            head.previous = head;
            tail = head;
        } else {
            head = new Node<>(item, head.previous, head);
        }
        size++;
    }

    /** Insert an item as the last item of the list.
     *	@param item The item to be inserted
     */
    private void addLast(E item) {
        if (head == null){
            addFirst(item);
        } else {
            tail = new Node<>(item, tail, tail.next);
            tail.previous.next = tail;
            size++;
        }
    }

    /**
     * Add a node after a given node
     * @param node The node which the new item is inserted after
     * @param item The item to insert
     */
    private void addAfter(Node<E> node, E item) {
        node.next = new Node<>(item, node, node.next);
        size++;
    }

    /**
     * Add a node before a given node
     * @param node The node which the new item is inserted before
     * @param item The item to insert
     */
    private Node<E> addBefore(Node<E> node, E item) {
        node.previous = new Node<>(item, node.previous, node);
        size++;
        return node.previous;
    }

    /**
     * Remove the first node from the list
     * @return The removed node's data or null if the list is empty
     */
    private E removeFirst() {
        Node<E> temp = head;
        if (head != null) {
            head = head.next;
            size--;
            return temp.data;
        } else {
            return null;
        }
    }

    /**
     * Remove the node after a given node
     * @param node The node before the one to be removed
     * @return The data from the removed node, or null
     *          if there is no node to remove
     */
    private E removeAfter(Node<E> node) {
        Node<E> temp = node.next;
        if (node.next != null) {
            node.next = node.next.next;
            size--;
            return temp.data;
        } else {
            return null;
        }
    }

    /**
     * Find the node at a specified index
     * @param index The index of the node sought
     * @return The node at index or null if it does not exist
     */
    private Node<E> getNode(int index) {
        Node<E> node = head;

        if (index == size - 1)
            return tail;

        for (int i = 0; i < index && node != null; i++) {
            node = node.next;
        }

        return node;
    }

    // Public Methods

    public LLIterator iterator(){
        return new LLIterator();
    }

    /**
     * Get the data value at index
     * @param index The index of the element to return
     * @return The data at index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    /**
     * Set the data value at index
     * @param index The index of the item to change
     * @param newValue The new value
     * @return The data value priviously at index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E set(int index, E newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        E result = node.data;
        node.data = newValue;
        return result;
    }

    /**
     * Insert the specified item at the specified position in the list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indicies)
     * @param index Index at which the specified item is to be inserted
     * @param item The item to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if (index == 0) {
            addFirst(item);
        } else if (index == size){
            addLast(item);
        } else {
            Node<E> node = getNode(index - 1);
            addAfter(node, item);
        }
    }

    /**
     * Append the specified item to the end of the list
     * @param item The item to be appended
     * @return true (as specified by the Collection interface)
     */
    public boolean add(E item) {
        addLast(item);
        return true;
    }

    /**
     * Retrieves and removes the head (first element) of this list.
     * @return the head of this list
     */
    public E remove() {
        return removeFirst();
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left
     * (subtracts one from their indices). Returns the element that was removed from the list.
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     */
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));

        E n;

        if (index == 0)
            n = removeFirst();
        else if (index == size - 1)
            n = removeLast();
        else
            n = removeAfter(getNode(index - 1));

        return n;
    }

    /**
     * Remove the last node from the list
     * @return The removed node's data or null if the list is empty
     */
    public E removeLast() {
        Node<E> temp = tail;
        if (tail != null) {
            tail = tail.previous;
            size--;
            return temp.data;
        } else {
            return null;
        }
    }

    /**
     * Query the size of the list
     * @return The number of objects in the list
     */
    int size() {
        return size;
    }

    /**
     * Obtain a string representation of the list
     * @return A String representation of the list 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> p = head;
        if (p != null) {
            while (p.next != null) {
                sb.append(p.data.toString());
                sb.append(" ==> ");
                p = p.next;
            }
            sb.append(p.data.toString());
        }
        sb.append("]");
        return sb.toString();
    }

// Insert solution to programming exercise 3, section 5, chapter 2 here

// Insert solution to programming exercise 4, section 5, chapter 2 here

// Insert solution to programming exercise 2, section 5, chapter 2 here
}
