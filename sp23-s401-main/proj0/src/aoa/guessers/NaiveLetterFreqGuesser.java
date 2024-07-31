package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {

        Map<Character, Integer> map = new TreeMap<>();

        for (String i: words)
        {
            for (int j = 0; j < i.length(); j++)
            {
                char temchar = i.charAt(j);
                if (map.containsKey(temchar))
                {
                    map.put(temchar, map.get(temchar) + 1);
                }
                else
                {
                    map.put(temchar, 1);
                }
            }
        }

        return map;

    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character, Integer> map = new TreeMap<>();
        map = getFrequencyMap();

        int maxInt = 0;
        char maxChar = ' ';

        for (char i : map.keySet())
        {
            if (guesses.contains(i))
            {
                //Do nothing, we do not want anything from the guesses list to be counted
            }
            else
            {

                if( (map.get(i) > maxInt) || ((map.get(i) == maxInt) && (i < maxChar)) )
                {
                    maxInt = map.get(i);
                    maxChar = i;
                }

            }

        }

        return maxChar;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
