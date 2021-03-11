import java.util.ArrayList;
import java.util.Collection;

public class HashMap<K, T> {
    private static final int defaultSize = 8;
    private static final double loadFactor = 0.75;

    private Entry[] table;
    /**
     * Max number of entries. If number of entries more than
     * threshold than resize needed.
     */
    private int threshold;

    /**
     * Size of a table
     */
    private int capacity = defaultSize;

    /**
     * Number of entries in table
     */
    private int size = 0;

    public HashMap() {
        this.table = new Entry[capacity];
        threshold = (int)(capacity * loadFactor);
    }

    public HashMap(int size) {
        this.capacity = size;
        this.table = new Entry[capacity];
    }

    public HashMap(int size, int threshold) {
        this(size);
        this.threshold = threshold;
    }

    /**
     * @return Number of entry in map
     */
    public int size() {
        return size;
    }

    /**
     * @return True if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checking if key exist in map
     * @param key Key to check
     * @return True if key exist, false otherwise
     */
    public boolean containsKey(K key) {
        int hash = key == null ? 0 : hash(key.hashCode());
        int pos = indexFor(hash, capacity);

        Entry<K, T> entry = (Entry<K, T>) table[pos];

        if (entry != null) {
            //Searching for last one or equal key
            do {
                //If key is equal return true
                if (entry.getKey().equals(key)) {
                    return true;
                }
            } while (entry.getNext() != null);
        }

        return false;
    }

    /**
     * Checking if value exist in map
     * @param value Value to search
     * @return True if value exist, false otherwise
     */
    public boolean containsValue(T value) {
        for (int i = 0; i < capacity; i++) {
            Entry<K,T> entry = (Entry<K,T>) table[i];
            while (entry != null) {
                if (entry.getValue().equals(value)) {
                    return true;
                }

                entry = entry.getNext();
            }
        }

        return false;
    }

    /**
     * Return value by giving key
     * @param key Key to search
     * @return Value if key exist, null otherwise
     */
    public T get(K key) {
        int hash = key == null ? 0 : hash(key.hashCode());
        int pos = indexFor(hash, capacity);

        Entry<K, T> entry = (Entry<K, T>) table[pos];

        if (entry != null) {
            //Searching for last one or equal key
            do {
                //If key is equal replacing return value
                if (entry.getKey() == key || entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            } while (entry.getNext() != null);
        }

        return null;
    }

    /**
     * Add pair key-value to map
     * @param key Pair key
     * @param value Value to save
     * @return Old value if key already exist, null otherwise
     */
    public T put(K key, T value) {
        if (key == null) {
            return putOnPosition(null, value, 0);
        }

        int hash = hash(key.hashCode());
        int pos = indexFor(hash, capacity);

        return putOnPosition(key, value, pos);
    }

    /**
     * Removes entry by giving key
     * @param key Key to remove by
     * @return Value of removed entry, null if entry not exist or have null value
     */
    public T remove(K key) {
        int hash = key == null ? 0 : hash(key.hashCode());
        int pos = indexFor(hash, capacity);

        Entry<K, T> entry = (Entry<K, T>) table[pos];
        Entry<K, T> prevEntry = null;

        while (entry != null) {
            if (entry.getKey().equals(key)) {

                if (prevEntry == null) {
                    table[pos] = entry.getNext();
                }
                else {
                    prevEntry.setNext(entry.getNext());
                }

                return entry.getValue();
            }

            prevEntry = entry;
            entry = entry.getNext();
        }

        return null;
    }

    /**
     * Add all entry from giving collection
     * @param collection Collection of entry to add
     */
    public void putAll(Collection<Entry<K, T>> collection) {
        if (collection.size() > (capacity - size)) {
            while ((capacity - size) < collection.size()) {
                capacity = capacity * 2;
            }

            resize(capacity);
        }

        for (Entry<K, T> i: collection) {
            put(i.getKey(), i.getValue());
        }
    }

    /**
     * Deleting all entry and make map to default size
     */
    public void clear() {
        this.capacity = defaultSize;
        this.table = new Entry[this.capacity];
        this.threshold = (int)(this.capacity * loadFactor);
        this.size = 0;
    }

    /**
     * Collecting all values to array list
     * @return Array list of values
     */
    public Collection<T> values() {
        Collection<T> values = new ArrayList<T>();

        for (int i = 0; i < capacity; i++) {
            Entry<K,T> entry = (Entry<K,T>) table[i];
            while (entry != null) {
                values.add(entry.getValue());

                entry = entry.getNext();
            }
        }

        return values;
    }

    /**
     * Collecting all values to array list
     * @return Array list of keys
     */
    public Collection<K> keys() {
        Collection<K> values = new ArrayList<K>();

        for (int i = 0; i < capacity; i++) {
            Entry<K,T> entry = (Entry<K,T>) table[i];
            while (entry != null) {
                values.add(entry.getKey());

                entry = entry.getNext();
            }
        }

        return values;
    }

    /**
     * Collecting all entry to array list
     * @return Array list of entries
     */
    public Collection<Entry<K, T>> entries() {
        Collection<Entry<K, T>> values = new ArrayList<Entry<K, T>>();

        for (int i = 0; i < capacity; i++) {
            Entry<K,T> entry = (Entry<K,T>) table[i];
            while (entry != null) {
                values.add(entry);

                entry = entry.getNext();
            }
        }

        return values;
    }

    /**
     * Putting entry on position if key is unique
     * If key is not unique replacing value and returns old value
     * @param key Entry key
     * @param value Entry value
     * @param pos Position in table to insert
     * @return Old value of entry if key is already present in table
     */
    private T putOnPosition(K key, T value, int pos) {
        Entry<K, T> prevEntry = (Entry<K, T>) table[pos];

        //If entry not exist than create one
        if (prevEntry == null) {
            table[pos] = new Entry<K, T>(key, value);
        }
        else {
            //Searching for last one or equal key
            do {
                //If key is equal replacing value and return old value
                if (prevEntry.getKey().equals(key)) {
                    T prevEntryValue = prevEntry.getValue();
                    prevEntry.setValue(value);

                    return prevEntryValue;
                }

                //Set new entry on chain end
                if (prevEntry.getNext() == null) {
                    prevEntry.setNext(new Entry<K, T>(key, value));
                    prevEntry = prevEntry.getNext();
                }

                prevEntry = prevEntry.getNext();
            } while (prevEntry != null);
        }

        size++;
        if (size > threshold)
            resize(2 * capacity);

        return null;
    }

    /**
     * Resizing table to giving size
     * @param size Size of table to resize
     */
    private void resize(int size) {
        Entry[] oldTable = table;

        this.capacity = size;
        this.table = new Entry[this.capacity];
        this.threshold = (int)(this.capacity * loadFactor);
        this.size = 0;

        transfer(oldTable);
    }

    /**
     * Transferring all entry from old table to new
     * @param oldTable Table with entries to transfer
     */
    private void transfer(Entry[] oldTable) {
        for (int i = 0; i < oldTable.length; i++) {
            Entry<K,T> entry = (Entry<K,T>) oldTable[i];
            while (entry != null) {
                put(entry.getKey(), entry.getValue());
                entry = entry.getNext();
            }
        }
    }

    private int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int indexFor(int h, int length) {
        return h % (length - 1);
    }
}
