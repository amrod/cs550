// A. Rodriguez: Based on Koffman and Wolfgang's SingleLinkedList. Modified into a double linked circular list.
package edu.sju;

import java.util.Iterator;
import java.util.ListIterator;
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

    private class LLIterator implements ListIterator<E> {
        Node<E> nextNode = head;
        Node<E> lastReturned = null;
        int index = 0;

        public LLIterator(){}

        public LLIterator(int i){
            if (i<0 || i > size)
                throw new IndexOutOfBoundsException();

            if (i == size) {
                nextNode = head; // redundant
                index = size;
            } else {
                for (index=0; index < i; index++) {
                    nextNode = nextNode.next;
                }
            }
        }

        /**
         * Test if there are any more nodes left in the LinkedList to iterate over.
         * @return true if there are nodes left, false if we're already at the tail.
         */
        public boolean hasNext(){
            return index < size;
        }

        public boolean hasPrevious(){
            return index > 0;
        }

        public int nextIndex(){
            return index;
        }

        public int previousIndex(){
            return index - 1;
        }

        /**
         * Advances the iterator to the next node and returns the consumed
         * node's data value.
         * @return A data object of type E.
         */
        public E next(){
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = nextNode;
            nextNode = lastReturned.next;
            index++;

            return lastReturned.data;
        }

        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = nextNode.previous;
            nextNode = lastReturned;
            index--;

            return lastReturned.data;
        }

        public void set(E item){
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.data = item;
        }


        /**
         * Removes the last returned node from the LinkedList.
         */
        public void remove(){
            if (lastReturned == null)
                throw new IllegalStateException();

            if (lastReturned.next == lastReturned) {
                head = tail = null;
            }else {
                lastReturned.previous.next = lastReturned.next;
                lastReturned.next.previous = lastReturned.previous;
            }

            size--; // Decrease list size after removing an element.
            lastReturned = null;
        }

        public void add(E data){
            Node<E> newNode = new Node<>(data);

            if (head == null){
                head = newNode;
                tail = newNode;
                newNode.next = newNode;
                newNode.previous = newNode;

            }else{
                newNode.next = nextNode;
                newNode.previous = nextNode.previous;
                nextNode.previous.next = newNode;
                nextNode.previous = newNode;

                if (index == size)
                    tail = newNode;
                else if (index == 0)
                    head = newNode;
            }

            index++;
            size++;
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
        addAt(item, 0);
    }

    /** Insert an item as the last item of the list.
     *	@param item The item to be inserted
     */
    private void addLast(E item) {
        addAt(item, size);
    }

    private void addAt(E item, int index) {
        ListIterator<E> iter = iterator(index);
        iter.add(item);
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
        return removeAt(0);
    }

    /**
     * Remove the node after a given node
     * @param index The node before the one to be removed
     * @return The data from the removed node, or null
     *          if there is no node to remove
     */
    private E removeAt(int index) {
        ListIterator<E> iter = iterator(index);
        E item = iter.next();
        iter.remove();
        return item;
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

    public LLIterator iterator(int index){
        return new LLIterator(index);
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
     * @return The data value previously at index
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
            n = removeAt(index);

        return n;
    }

    /**
     * Remove the last node from the list
     * @return The removed node's data or null if the list is empty
     */
    public E removeLast() {
        ListIterator<E> iter = iterator(size);
        E item = iter.previous();
        iter.remove();
        return item;
    }

    /**
     * Query the size of the list
     * @return The number of objects in the list
     */
    int getSize() {
        if (head == null)
            return 0;

        int count;
        Node<E> node = head.next;

        for (count=1; node != head; count++)
            node = node.next;

        assert size == count;
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
