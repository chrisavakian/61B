package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    public static void main(String[] args) { }

    Comparator<T> c;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.c = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }

        T largest = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            if (c.compare(largest, this.get(i)) < 0) {
                largest = this.get(i);
            }
        }

        return largest;
    }

    public T max(Comparator<T> a) {
        if (this.isEmpty()) {
            return null;
        }

        T largest = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            if (a.compare(largest, this.get(i)) < 0) {
                largest = this.get(i);
            }
        }

        return largest;
    }

    public boolean equals(Object o) {
        return o == this;
    }
}
