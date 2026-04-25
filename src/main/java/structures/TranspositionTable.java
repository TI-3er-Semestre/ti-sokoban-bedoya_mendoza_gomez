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

    private int hash(StateKey key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Adds a state key to the table.
     * Avoids duplicates and handles collisions.
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
     * Checks if a state key already exists.
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