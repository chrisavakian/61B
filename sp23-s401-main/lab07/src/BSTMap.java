import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        K key;
        V item;
        Node larger;
        Node smaller;

        private Node(K key, V item, Node larger, Node smaller) {
            this.key = key;
            this.item = item;
            this.larger = larger;
            this.smaller = smaller;
        }
    }

    //Creates root node
    Node root;

    //This is for tracking the size
    int size;

    public BSTMap() {
        //Sets node root to null
        this.root = new Node(null, null, null, null);

        //Sets size to null
        this.size = 0;
    }

    private Node allHelper(Node currNode, K key) {
        K currKey = currNode.key;

        if (currNode.larger != null && currKey.compareTo(key) < 0) {
            return allHelper(currNode.larger, key);
        } else if (currNode.smaller != null && currKey.compareTo(key) > 0) {
            return allHelper(currNode.smaller, key);
        } else {
            return currNode;
        }
    }

    @Override
    public void put(K key, V value) {
        if (size == 0) {
            root = new Node(key, value, null, null);
            size++;
        } else {
            Node temp = new Node(key, value, null, null);
            Node parentNode = allHelper(root, key);

            if (parentNode.key.compareTo(key) > 0) {
                parentNode.smaller = temp;
                size++;
            } else if (parentNode.key.compareTo(key) < 0) {
                parentNode.larger = temp;
                size++;
            } else if (parentNode.key.compareTo(key) == 0) {
                parentNode.item = temp.item;
            }
        }
    }

    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }

        Node temp = allHelper(root, key);

        return temp.item;
    }

    @Override
    public boolean containsKey(K key) {
        //Before root == null
        if (root == null || root.key == null) {
            return false;
        }

        Node temp = allHelper(root, key);

        //Add temp.item == null ||
        if (!temp.key.equals(key)) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }


    //Everything under this don't implement
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
