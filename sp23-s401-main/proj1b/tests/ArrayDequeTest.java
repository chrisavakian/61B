import org.junit.jupiter.api.Test;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    public void isEmptyTest() {

        //Test for False
        ArrayDeque<Integer> a = new ArrayDeque<>();

        a.addLast(0);   // [0]
        a.addLast(1);   // [0, 1]
        a.addFirst(-1); // [-1, 0, 1]
        a.addLast(2);   // [-1, 0, 1, 2]
        a.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(a.isEmpty()).isFalse();

        //Test for true
        Deque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.isEmpty()).isTrue();
    }

    @Test
    public void sizeTest() {

        //size
        ArrayDeque<Integer> a = new ArrayDeque<>();

        a.addLast(0);   // [0]
        a.addLast(1);   // [0, 1]
        a.addFirst(-1); // [-1, 0, 1]
        a.addLast(2);   // [-1, 0, 1, 2]
        a.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(a.size()).isEqualTo(5);

        //size after remove from empty
        ArrayDeque<Integer> c = new ArrayDeque<>();

        assertThat(c.size()).isEqualTo(0);

        //size after remove to empty
        ArrayDeque<Integer> b = new ArrayDeque<>();

        b.addLast(0);   // [0]
        b.addLast(1);   // [0, 1]
        b.addFirst(-1); // [-1, 0, 1]
        b.addLast(2);   // [-1, 0, 1, 2]
        b.addFirst(-2); // [-2, -1, 0, 1, 2]

        b.removeLast();
        b.removeLast();
        b.removeLast();
        b.removeLast();
        b.removeLast();

        assertThat(b.size()).isEqualTo(0);
    }

    @Test
    public void toListTest() {

        //to list nonempty
        ArrayDeque<Integer> a = new ArrayDeque<>();

        a.addLast(0);   // [0]
        a.addLast(1);   // [0, 1]
        a.addFirst(-1); // [-1, 0, 1]
        a.addLast(2);   // [-1, 0, 1, 2]
        a.addFirst(-2); // [-2, -1, 0, 1, 2]

        List<Integer> temp = new ArrayList<>();
        temp.add(-2);
        temp.add(-1);
        temp.add(0);
        temp.add(1);
        temp.add(2);

        assertThat(a.toList()).isEqualTo(temp);

        //to list empty
        List<Integer> temp2 = new ArrayList<>();

        Deque<Integer> lld2 = new ArrayDeque<>();
        assertThat(lld2.toList()).isEqualTo(temp2);
    }

    @Test
    public void getTest() {

        //get_valid
        ArrayDeque<Integer> a = new ArrayDeque<>();

        a.addLast(0);   // [0]
        a.addLast(1);   // [0, 1]
        a.addFirst(-1); // [-1, 0, 1]
        a.addLast(2);   // [-1, 0, 1, 2]
        a.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(a.get(0)).isEqualTo(-2);

        //get_oob_neg
        assertThat(a.get(-1)).isEqualTo(null);

        //get_oob_large
        assertThat(a.get(50000)).isEqualTo(null);
    }

    @Test
    public void removeTest() {

        //remove first
        //remove first to empty
        //remove first to one
        //remove first to trigger resize
        ArrayDeque<Integer> a = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            a.addLast(i);
        }

        for (int i = 0; i < 100; i++) {
            assertThat(a.removeFirst()).isEqualTo(i);
        }

        //remove last
        //remove last to empty
        //remove last to one
        //remove last to trigger resize
        ArrayDeque<Integer> b = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            b.addLast(i);
        }

        for (int i = 0; i < 100; i++) {
            assertThat(b.removeFirst()).isEqualTo(i);
        }
    }

    @Test
    public void fillThenRemoveFirstTillEmpty() {

        ArrayDeque<Integer> a = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            a.addLast(i);
        }

        for (int i = 0; i < 100; i++) {
            a.removeLast();
        }

        List<Integer> temp = new ArrayList<>();

        assertThat(a.toList()).isEqualTo(temp);

    }

    @Test
    public void addTest() {

        //add_first_from_empty, add_first_nonempty, add_first_trigger_resize
        ArrayDeque<Integer> a = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            a.addFirst(i);
        }

        //add_last_from_empty, add_last_nonempty, add_last_trigger_resize
        ArrayDeque<Integer> b = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            b.addLast(i);
        }

    }

    @Test
    public void addAfterRemoveTest() {

        //add_first_after_remove_to_empty, add_last_after_remove_to_empty
        ArrayDeque<Integer> a = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            a.addLast(i);
        }

        for (int i = 0; i < 10; i++) {
            a.removeFirst();
        }

        a.addFirst(1);

        a.removeFirst();

        a.addLast(2);

        //assertThat(a.addFirst(1);).isEqualTo(null);

    }
}
