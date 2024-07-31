//import org.eclipse.jetty.server.Authentication;
import java.util.ArrayList;
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

    public static void main(String[] args) {
    }

    //Default contructor with no initial condition
    public LinkedListDeque() {

        sentinel = new Node(null, sentinel, sentinel);

        sentinel.next = sentinel;
        sentinel.previous = sentinel;

        size = 1;
    }

    public LinkedListDeque(T i) {
        Node temp = new Node(i, sentinel, sentinel);
        sentinel = new Node(i, temp, temp);

        temp.next = sentinel;
        temp.previous = sentinel;

        size = 2;
    }

    @Override
    //Working
    public void addFirst(T x) {
        Node item = new Node(x, sentinel.next, sentinel);
        sentinel.next.previous = item;
        sentinel.next = item;
        size++;
    }

    //Working
    @Override
    public void addLast(T x) {
        Node item = new Node(x, sentinel, sentinel.previous);
        sentinel.previous.next = item;
        sentinel.previous = item;
        size++;
    }

    @Override
    //Not Tested
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
    //Not tested with my test cases, but come on
    public boolean isEmpty() {

        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    //Not tested, but also come on
    public int size() {
        return size - 1;
    }

    @Override
    //Not tested
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
    //Not tested
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
    //Not tested
    public T get(int index) {
        if ((size == 1) || (index > size - 2) || (index < 0)) {
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
