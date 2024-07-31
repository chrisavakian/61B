import java.util.List;
import java.util.ArrayList;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L)
    {
        int sum = 0;
        for (int i: L)
        {
            sum = sum + i;
        }

        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L)
    {
        List<Integer> lst = new ArrayList<>();

        for (int i: L)
        {
            if(i % 2 == 0)
            {
                lst.add(i);
            }
        }

        return lst;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2)
    {

        List<Integer> lst = new ArrayList<>();

        for (int i: L1)
        {
            if (L2.contains(i))
            {
                lst.add(i);
            }
        }

        return lst;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c)
    {
        int occur = 0;

        for (String i: words)
        {
            for (int j = 0; j < i.length(); j++)
            {
                if(i.charAt(j) == c)
                {
                    occur = occur + 1;
                }
            }
        }

        return occur;
    }
}