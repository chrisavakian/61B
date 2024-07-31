package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        private T item;
        private Node next;
        private Node previous;

        private Node(T i, Node n, Node p) {
            item = i;
            next = n;
            previous = p;
        }
    }

    private Node sentinel;
    private int size;

    public static void main(String[] args) { }

    @Override
    public String toString() {
        Node temp = sentinel.next;
        StringBuilder tempString = new StringBuilder();

        tempString.append("[");

        while (temp != sentinel) {
            tempString.append(temp.item);
            if (temp.next != sentinel) {
                tempString.append(", ");
            }
            temp = temp.next;
        }

        tempString.append("]");
        return tempString.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        if (obj instanceof Deque otherset) {
            if (this.size != (otherset.size())) {
                return false;
            }

            for (int i = 0; i < size; i++) {
                if (!otherset.get(i).equals(this.get(i))) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private int pos;
        private Node iteratorNode;
        public LinkedListIterator() {
            pos = 0;
            iteratorNode = sentinel.next;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = iteratorNode.item;
            iteratorNode = iteratorNode.next;
            pos++;
            return returnItem;
        }
    }

    public LinkedListDeque() {

        sentinel = new Node(null, sentinel, sentinel);

        sentinel.next = sentinel;
        sentinel.previous = sentinel;

        size = 0;
    }

    public LinkedListDeque(T i) {
        Node temp = new Node(i, sentinel, sentinel);
        sentinel = new Node(i, temp, temp);

        temp.next = sentinel;
        temp.previous = sentinel;

        size = 1;
    }

    @Override
    public void addFirst(T x) {
        Node item = new Node(x, sentinel.next, sentinel);
        sentinel.next.previous = item;
        sentinel.next = item;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node item = new Node(x, sentinel, sentinel.previous);
        sentinel.previous.next = item;
        sentinel.previous = item;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        Node currNode = sentinel.next;

        while (currNode != sentinel) {
            returnList.add(currNode.item);
            currNode = currNode.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {

        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        Node onChoppingBlock = sentinel.next;

        if (onChoppingBlock == sentinel) {
            return null;
        }

        sentinel.next = onChoppingBlock.next;
        onChoppingBlock.next.previous = sentinel;

        size--;

        onChoppingBlock.next = null;
        onChoppingBlock.previous = null;

        return onChoppingBlock.item;
    }

    @Override
    public T removeLast() {
        Node onChoppingBlock = sentinel.previous;

        if (onChoppingBlock == sentinel) {
            return null;
        }

        sentinel.previous = onChoppingBlock.previous;
        onChoppingBlock.previous.next = sentinel;

        size--;

        onChoppingBlock.next = null;
        onChoppingBlock.previous = null;

        return onChoppingBlock.item;
    }

    @Override
    public T get(int index) {
        if ((size == 1) || (index > size - 1) || (index < 0)) {
            return null;
        }

        int counter = 0;
        Node currNode = sentinel.next;

        while (counter != index) {
            currNode = currNode.next;
            counter++;
        }

        return currNode.item;
    }

    private T getHelper(int index, int currIndex, Node currNode) {

        if (currIndex < index) {
            return getHelper(index, currIndex + 1, currNode.next);
        } else {
            return currNode.item;
        }
    }

    @Override
    public T getRecursive(int index) {

        if ((size == 1) || (index > size - 2) || (index < 0)) {
            return null;
        }
        return getHelper(index, 0, sentinel.next);
    }
}
