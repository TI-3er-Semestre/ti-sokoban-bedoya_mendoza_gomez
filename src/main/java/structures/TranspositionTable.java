package structures;

/**
 * Hash table for storing visited game states.
 * Uses linear probing for collision resolution.
 */
public class TranspositionTable {

    private StateKey[] table;
    private int capacity;

    public TranspositionTable(int capacity) {
        this.capacity = capacity;
        this.table = new StateKey[capacity];
    }

    /**
     * Calculates the index for a given key.
     * @param key the state key to hash
     * @return index position in the table
     */
    private int hash(StateKey key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Creates a new hash table with a fixed capacity.
     * @param capacity maximum number of elements the table can store
     */
    public void add(StateKey key) {
        int index = hash(key);
        int startIndex = index;

        while (table[index] != null) {
            if (table[index].equals(key)) {
                return; // already exists
            }

            index = (index + 1) % capacity;

            if (index == startIndex) {
                return; // table full
            }
        }

        table[index] = key;
    }

    /**
     * Checks if a key exists in the table.
     * @param key the state key to search
     * @return true if the key is found, false otherwise
     */
    public boolean contains(StateKey key) {
        int index = hash(key);
        int startIndex = index;

        while (table[index] != null) {
            if (table[index].equals(key)) {
                return true;
            }

            index = (index + 1) % capacity;

            if (index == startIndex) {
                return false;
            }
        }

        return false;
    }
}