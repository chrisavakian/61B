import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

     //Below, you'll write your own tests for LinkedListDeque.

    @Test
    /** This test checks isEmpty */
    //Passed
    public void isEmptyTest() {

        //Test for False
        Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.isEmpty()).isFalse();

        //Test for true
        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.isEmpty()).isTrue();
    }

    @Test
    /** This test checks the size */
    //Passed
    public void sizeTest() {

        //Test for False
        Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.size()).isEqualTo(5);

        //Test for true
        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.size()).isEqualTo(0);
    }

    @Test
    /** This test checks the get iterative function */
    public void getTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.get(0)).isEqualTo(-2);
        assertThat(lld1.get(1)).isEqualTo(-1);
        assertThat(lld1.get(2)).isEqualTo(0);
        assertThat(lld1.get(3)).isEqualTo(1);
        assertThat(lld1.get(4)).isEqualTo(2);



        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.get(0)).isEqualTo(null);
        assertThat(lld2.get(-5)).isEqualTo(null);
    }

    @Test
    /** This test checks the get recursive function */
    public void getRecursiveTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.getRecursive(0)).isEqualTo(-2);
        assertThat(lld1.getRecursive(1)).isEqualTo(-1);
        assertThat(lld1.getRecursive(2)).isEqualTo(0);
        assertThat(lld1.getRecursive(3)).isEqualTo(1);
        assertThat(lld1.getRecursive(4)).isEqualTo(2);



        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.getRecursive(0)).isEqualTo(null);
    }

    @Test
    /** This test checks the remove first and remove last function */
    public void removeTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        //remove first, remove first to empty, remove first to one
        assertThat(lld1.removeFirst()).isEqualTo(-2);
        assertThat(lld1.removeFirst()).isEqualTo(-1);
        assertThat(lld1.removeFirst()).isEqualTo(0);
        assertThat(lld1.removeFirst()).isEqualTo(1);
        assertThat(lld1.removeFirst()).isEqualTo(2);


        //Remove last, remove last to empty, remove last to one
        Deque<Integer> lld2 = new LinkedListDeque<>();

        lld2.addLast(0);   // [0]
        lld2.addLast(1);   // [0, 1]
        lld2.addFirst(-1); // [-1, 0, 1]
        lld2.addLast(2);   // [-1, 0, 1, 2]
        lld2.addFirst(-2); // [-2, -1, 0, 1, 2]

        //remove first, remove first to empty, remove first to one
        assertThat(lld2.removeLast()).isEqualTo(-2);
        assertThat(lld2.removeLast()).isEqualTo(-1);
        assertThat(lld2.removeLast()).isEqualTo(0);
        assertThat(lld2.removeLast()).isEqualTo(1);
        assertThat(lld2.removeLast()).isEqualTo(2);


    }

    @Test
    /** This test checks the size */
    //Passed
    public void addAfterRemoveLast() {

        //Test for False
        Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        for (int i = 0; i < 5; i++) {
            lld1.removeLast();
        }

        lld1.addFirst(1);

        assertThat(lld1.get(0)).isEqualTo(1);
    }

    @Test
    /** This test checks the size */
    //Passed
    public void addAfterRemoveFirst() {

        //Test for False
        Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        for (int i = 0; i < 5; i++) {
            lld1.removeFirst();
        }

        lld1.addFirst(1);

        assertThat(lld1.get(0)).isEqualTo(1);
    }

    @Test
    /** This test checks the size */
    //Passed
    public void toListTest() {

        Deque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        List<Integer> full = new ArrayList<>();
        full.add(-2);
        full.add(-1);
        full.add(0);
        full.add(1);
        full.add(2);

        List<Integer> empty = new ArrayList<>();

        assertThat(lld1.toList()).isEqualTo(full);

        Deque<Integer> lld2 = new LinkedListDeque<>();
        assertThat(lld2.toList()).isEqualTo(empty);

    }

}