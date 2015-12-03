package edu.sju;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Hash table implementation using separate chaining.
 **/
public class Hashtable<K,V> {

    private static final int CAPACITY = 201;
    /** Keep load factor below 0.9. */
    private static final double LOAD_THRESHOLD = 0.9;
    /** Current number of keys in the table */
    private int numberOfKeys;
    /** Total number of collisions found during insertion/lookup. */
    private int totalCollisions = 0;
    private int totalInsertionsLookups = 0;
    /** Total length of chains found during insertion/lookup. */
    private int totalChainLengths = 0;
    /** Maximum length of a chain found during insertion/lookup. */
    private int maxChainLength = 0;
    /** Array of linked lists represents the hash table. */
    private LinkedList<Entry<K,V>>[] mainTable;

    /** Contains key-value pairs for a hash table. */
    private static class Entry<K,V> implements Map.Entry<K,V> {

        private K key;
        private V value;

        /**
         * Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key.
         * @return The key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value.
         * @return The value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the value.
         * @param val The new value
         * @return The old value
         */
        @Override
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

        /**
         * Return a String representation of the Entry
         * @return a String representation of the Entry
         *         in the form key = value
         */
        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

    }

    // Constructor
    public Hashtable() {
        mainTable = new LinkedList[CAPACITY];
    }

    /**
     * Calculates the current load factor.
     * @return the current load factor.
     */
    public double getLoadFactor(){
        return numberOfKeys / (double) mainTable.length;
    }

    /**
     * Total number of collisions found.
     * @return Total number of collisions found.
     */
    public int getTotalCollisions(){
        return totalCollisions;
    }

    /**
     * Computes the average chain length found during insertions/lookups.
     * @return average chain length found during insertions/lookups.
     */
    public double getAverageChainLength(){
        if (totalInsertionsLookups < 1){
            return 0;
        }
        return (double) totalChainLengths / (double) totalInsertionsLookups;
    }

    /**
     * Gets the max chain length found so far.
     * @return max chain length found so far.
     */
    public int getMaxChainLength(){
        return maxChainLength;
    }

    /**
     * Adds the given length to the running total of chain lengths and
     * increments the running total of insertions and lookups. This total
     * is used to compute the average chain length.
     * @param len length of chain to add to the total.
     */
    private void addChainLength(int len){
        maxChainLength = Math.max(maxChainLength, len);
        totalChainLengths += len;
        if (len > 0)
            totalInsertionsLookups++;
    }

    /**
     * Inserts the key-value pair in the table and increments numberOfKeys.
     * If the key is already in the table, its value is changed to the argument
     * value and numberOfKeys is not changed.
     * @param key The key of item being inserted
     * @param value The value for this key
     * @return The old value associated with this key if
     *         found; otherwise, null
     */
    public V insert(K key, V value) {
        int index = key.hashCode() % mainTable.length;

        if (index < 0) {
            index += mainTable.length;
        }
        if (mainTable[index] == null) {
            // No collision. Create a new linked list at mainTable[index].
            mainTable[index] = new LinkedList<Entry<K,V>>();
        } else {
            totalCollisions++;
        }

        int chainLength = 0;
        // Search the list at mainTable[index] to find the key.
        for (Entry<K,V> nextItem : mainTable[index]) {
            chainLength++;
            // If the search is successful, replace the old value.
            if (nextItem.key.equals(key)) {
                // Replace value for this key.
                V oldVal = nextItem.value;
                nextItem.setValue(value);
                addChainLength(chainLength);
                return oldVal;
            }
        }

        addChainLength(chainLength);
        // Key is not in the mainTable, add new item.
        mainTable[index].addFirst(new Entry<K,V>(key, value));
        numberOfKeys++;
        if (numberOfKeys > (LOAD_THRESHOLD * mainTable.length)) {
            rehash();
        }
        return null;
    }

    /**
     * Method get for class Hashtable.
     * @param key The key being sought
     * @return The value associated with this key if found;
     *         otherwise, null
     */
    public V get(Object key) {
        int index = key.hashCode() % mainTable.length;
        if (index < 0) {
            index += mainTable.length;
        }
        if (mainTable[index] == null) {
            return null; // key is not in the mainTable.
        }

        int chainLength = 0;
        // Search the list at mainTable[index] to find the key.
        for (Entry<K,V> nextItem : mainTable[index]) {
            chainLength++;

            if (nextItem.key.equals(key)) {
                addChainLength(chainLength);
                return nextItem.value;
            }
        }

        addChainLength(chainLength);
        // key is not in the chain
        return null;
    }

    /**
     * Removes the entry with the given key from the hashtable.
     * @param key Key of entry to remove.
     * @return Value of entry removed, or null if key not in the table
     */
    public V remove(Object key) {
        int index = key.hashCode() % mainTable.length;
        if (index < 0) {
            index += mainTable.length;
        }
        if (mainTable[index] == null) {
            return null; // Key not in mainTable
        }
        Iterator<Entry<K,V>> iter = mainTable[index].iterator();
        while (iter.hasNext()) {
            Entry<K,V> nextItem = iter.next();
            // If the search is successful, return the value.
            if (nextItem.key.equals(key)) {
                V returnValue = nextItem.value;
                iter.remove();
                return returnValue;
            }
        }
        // Key not in mainTable
        return null;
    }

    /** Returns the number of entries in the map */
    public int size() {
        return numberOfKeys;
    }

    /** Returns true if empty */
    public boolean isEmpty() {
        return numberOfKeys == 0;
    }

    /**
     * Expands mainTable size when loadFactor exceeds LOAD_THRESHOLD.
     * The size of table is doubled and is an odd integer. Each non-deleted
     * entry from the original table is reinserted into the expanded table.
     * The value of numberOfKeys is reset to the number of items actually 
     * inserted; numDeletes is reset to 0.
     */
    public void rehash() {
        // Save a reference to origTable
        LinkedList<Entry<K,V>>[] origTable = mainTable;
        // Double capacity of this mainTable
        mainTable = new LinkedList[2 * origTable.length + 1];

        // Reinsert all items in origTable into expanded mainTable.
        numberOfKeys = 0;
        for (int i = 0; i < origTable.length; i++) {
            if (origTable[i] != null) {
                for (Entry<K,V> nextEntry : origTable[i]) {
                    // Insert entry in expanded mainTable
                    insert(nextEntry.key, nextEntry.value);
                }
            }
        }
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("[");
        boolean first = true;
        for (LinkedList<Entry<K,V>> aTable : mainTable) {
            if (aTable != null) {
                for (Entry<K,V> e : aTable) {
                    stb.append(aTable);
                    if (first) {
                        first = false;
                    } else {
                        stb.append(", ");
                    }
                }
            }
        }
        stb.append("]");
        return stb.toString();
    }

}

