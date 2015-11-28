package edu.sju;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Hash table implementation using chaining.
 **/
public class Hashtable<K, V> {

    /** Array of linked lists represents the hashtable. */
    private LinkedList<Entry<K, V>>[] table;
    private int numKeys;
    private static final int CAPACITY = 201;
    /** Keep load factor below 0.9. */
    private static final double LOAD_THRESHOLD = 0.9;
    /** Total number of collisions found during insertion/lookup. */
    private int totalCollisions = 0;
    private int totalInsertionsLookups = 0;
    /** Total length of chains found during insertion/lookup. */
    private int totalChainLengths = 0;
    /** Maximum length of a chain found during insertion/lookup. */
    private int maxChainLength = 0;

    /** Contains key-value pairs for a hash table. */
    private static class Entry<K, V> implements Map.Entry<K, V> {

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
        table = new LinkedList[CAPACITY];
    }

    /**
     * Calculates the current load factor.
     * @return the current load factor.
     */
    public double getLoadFactor(){
        return numKeys / (double) table.length;
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

    public int getMaxChainLength(){
        return maxChainLength;
    }

    private void addChainLength(int len){
        maxChainLength = Math.max(maxChainLength, len);
        totalChainLengths += len;
        if (len > 0)
            totalInsertionsLookups++;
    }

    /**
     * Method get for class Hashtable.
     * @param key The key being sought
     * @return The value associated with this key if found;
     *         otherwise, null
     */
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return null; // key is not in the table.
        }

        int chainLength = 0;
        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]) {
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
     * Method insert for class Hashtable.
     * @post This key-value pair is inserted in the
     *       table and numKeys is incremented. If the key is already
     *       in the table, its value is changed to the argument
     *       value and numKeys is not changed.
     * @param key The key of item being inserted
     * @param value The value for this key
     * @return The old value associated with this key if
     *         found; otherwise, null
     */
    public V insert(K key, V value) {
        int index = key.hashCode() % table.length;

        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            // No collision. Create a new linked list at table[index].
            table[index] = new LinkedList<Entry<K, V>>();
        } else {
            totalCollisions++;
        }

        int chainLength = 0;
        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]) {
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
        // Key is not in the table, add new item.
        table[index].addFirst(new Entry<K, V>(key, value));
        numKeys++;
        if (numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash();
        }
        return null;
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("[");
        boolean first = true;
        for (LinkedList<Entry<K, V>> aTable : table) {
            if (aTable != null) {
                for (Entry<K, V> e : aTable) {
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

    /** Returns the number of entries in the map */
    public int size() {
        return numKeys;
    }

    /** Returns true if empty */
    public boolean isEmpty() {
        return numKeys == 0;
    }

    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return null; // Key not in table
        }
        Iterator<Entry<K, V>> iter = table[index].iterator();
        while (iter.hasNext()) {
            Entry<K, V> nextItem = iter.next();
            // If the search is successful, return the value.
            if (nextItem.key.equals(key)) {
                V returnValue = nextItem.value;
                iter.remove();
                return returnValue;
            }
        }
        return null; // Key not in table
    }

    /**
     * Expands table size when loadFactor exceeds LOAD_THRESHOLD
     * @post the size of table is doubled and is an
     *       odd integer. Each non-deleted entry from the original
     *       table is reinserted into the expanded table.
     *       The value of numKeys is reset to the number of items
     *       actually inserted; numDeletes is reset to 0.
     */
    public void rehash() {
        // Save a reference to oldTable
        LinkedList<Entry<K, V>>[] oldTable = table;
        // Double capacity of this table
        table = new LinkedList[2 * oldTable.length + 1];

        // Reinsert all items in oldTable into expanded table.
        numKeys = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                for (Entry<K, V> nextEntry : oldTable[i]) {
                    // Insert entry in expanded table
                    insert(nextEntry.key, nextEntry.value);
                }
            }
        }
    }

}

