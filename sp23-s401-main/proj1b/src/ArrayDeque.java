import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {

    public static void main(String[] args) { }

    private T[] arrValue;
    private int size;

    private int nextFirst;
    private int nextLast;

    //First item in array
    private int item;

    public ArrayDeque() {
        arrValue = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
        item = 8;
    }

    @Override
    public void addFirst(T x) {
        if (arrValue.length == size) {
            resizeUp();
        }

        arrValue[nextFirst] = x;

        size++;

        item = nextFirst;

        if (nextFirst - 1 < 0) {
            nextFirst = arrValue.length - 1;
        } else {
            nextFirst--;
        }

    }

    @Override
    public void addLast(T x) {
        if (arrValue.length == size) {
            resizeUp();
        }

        arrValue[nextLast] = x;

        size++;

        if (nextLast + 1 == arrValue.length) {
            nextLast = 0;
        } else {
            nextLast++;
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        int titem = item;
        while (returnList.size() != size) {
            if (titem == arrValue.length) {
                titem = 0;
            }
            returnList.add(arrValue[titem]);
            titem++;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void resizeUp() {
        T[] tempArr = (T[]) new Object[size * 2];
        int tempArrCounter = 0;

        int titem = item;
        while (tempArrCounter != size) {
            if (titem == arrValue.length) {
                titem = 0;
            }
            tempArr[tempArrCounter] = arrValue[titem];
            titem++;
            tempArrCounter++;
        }

        nextLast = arrValue.length;
        nextFirst = tempArr.length - 1;

        arrValue = tempArr;

        item = 0;
    }

    private void resizeDown() {
        T[] tempArr = (T[]) new Object[size];
        int tempArrCounter = 0;

        int titem = item;
        while (tempArrCounter != size) {
            if (titem == arrValue.length) {
                titem = 0;
            }
            tempArr[tempArrCounter] = arrValue[titem];
            titem++;
            tempArrCounter++;
        }

        nextLast = 0;
        nextFirst = tempArr.length - 1;

        arrValue = tempArr;

        item = 0;
    }

    @Override
    public T removeFirst() {
        T temp = null;

        if (isEmpty()) {
            return null;
        }

        if (size < (int) (arrValue.length * 0.25)
                && (arrValue.length > (1 + 1 + 1 + 1 + 1 + 1 + 1
                + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1))) {
            resizeDown();
        }

        if (nextFirst + 1 != arrValue.length) {
            temp = arrValue[nextFirst + 1];

            arrValue[nextFirst + 1] = null;

            nextFirst++;

            item = nextFirst + 1;

            size--;
        } else {
            temp = arrValue[0];

            arrValue[0] = null;

            nextFirst = 0;

            item = nextFirst + 1;

            size--;
        }

        return temp;

    }

    @Override
    public T removeLast() {
        T temp = null;

        if (isEmpty()) {
            return null;
        }

        if (size < (int) (arrValue.length * 0.25)
                && (arrValue.length > (1 + 1 + 1 + 1 + 1 + 1 + 1
                + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1))) {
            resizeDown();
        }

        if (nextLast - 1 >= 0) {
            temp = arrValue[nextLast - 1];

            arrValue[nextLast - 1] = null;

            nextLast--;

            size--;
        } else {
            temp = arrValue[arrValue.length - 1];

            arrValue[arrValue.length - 1] = null;

            nextLast = arrValue.length - 1;

            size--;
        }
        return temp;
    }

    @Override
    public T get(int index) {
        if (index > (arrValue.length - 1) || index < 0) {
            return null;
        }

        if ((arrValue.length - 1) - nextFirst <= index) {
            return arrValue[index - ((arrValue.length - 1) - nextFirst)];
        } else {
            return arrValue[index + (nextFirst + 1)];
        }
    }
}
