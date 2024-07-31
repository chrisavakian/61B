package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    //Use .equals
    //Load factor: that is N/M, when the buckets get too full

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int capacity;
    private double loadFactor;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.capacity = 16;
        this.loadFactor = 0.75;
        this.size = 0;
        this.buckets = this.createTable(this.capacity);
    }

    public MyHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.loadFactor = 0.75;
        this.size = 0;
        this.buckets = this.createTable(this.capacity);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.buckets = this.createTable(this.capacity);
    }

    public static void main (String args[]) {
        MyHashMap<String, String> temp = new MyHashMap<>(4,1);

        temp.put("kerfuffle", "kerfuffle");
        temp.put("broom", "broom");
        temp.put("hroom", "hroom");
        temp.put("ragamuffin", "ragamuffin");
        temp.put("donkey", "donkey");
        temp.put("brekky", "brekky");
        temp.put("blob", "blob");
        temp.put("zenzizenzizenzic", "zenzizenzizenzic");
        temp.put("yap", "yap");

        System.out.print("asdf");
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] temp =  new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            temp[i] = this.createBucket();
        }
        return temp;
    }

    private void resize() {
        Collection<Node>[] toReplace = createTable(this.capacity * 2);
        this.capacity = this.capacity * 2;

        for (Collection<Node> i: buckets) {
            for (Node j: i) {
                int bucketOfInterest = Math.floorMod(Hash(j.key), this.capacity);

                toReplace[bucketOfInterest].add(j);
            }
        }

        this.buckets = toReplace;
    }

    private int Hash(K x) {
        return x.toString().length();
    }

    @Override
    public void put(K key, V value) {
        Node temp = new Node(key, value);
        int bucketOfInterest = Math.floorMod(Hash(key), this.capacity);
        Collection<Node> listOfInterest = this.buckets[bucketOfInterest];

        for (Node i: listOfInterest) {
            if (i.key.equals(key)) {
                i.value = value;
                return;
            }
        }

        listOfInterest.add(temp);
        this.size++;

        if (((this.size + 0.0) / this.capacity) > this.loadFactor) {
            this.resize();
        }
    }

    @Override
    public V get(K key) {
        int bucketOfInterest = Math.floorMod(Hash(key), this.capacity);

        Collection<Node> listOfInterest = this.buckets[bucketOfInterest];
        V returnVal = null;

        for (Node i: listOfInterest) {
            if (i.key.equals(key)) {
                returnVal = i.value;
            }
        }
        return returnVal;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketOfInterest = Math.floorMod(Hash(key), this.capacity);
        Collection<Node> listOfInterest = this.buckets[bucketOfInterest];

        for (Node i: listOfInterest) {
            if (i.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }


    //Dumb but prob works.
    @Override
    public void clear() {
        this.buckets = this.createTable(this.capacity);
        this.size = 0;
    }

    //DON'T HAVE TO DO ANYTHING BELOW THIS
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Bruh");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Bruh");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Bruh");
    }
}
