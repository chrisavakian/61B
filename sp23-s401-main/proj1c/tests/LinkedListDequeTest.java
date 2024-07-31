import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class LinkedListDequeTest {

    @Test
    public void testEqualDeque() {
        Deque<String> lld1 = new LinkedListDeque<>();
        Deque<String> lld2 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        boolean temp = lld1.equals(lld2);

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void iteratorTest () {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        lld1.addFirst("bruh");
        for (String s : lld1) {
            System.out.println(s);
        }
    }

    @Test
    public void toStringTest () {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);
    }

}
