package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }
    public Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> words)
    {
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

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {//-e--

        List<String> guessedLetters = new ArrayList<>();

        for(String i: words)
        {
            boolean shouldWordAdded = true;

            for (int j = 0; j < pattern.length(); j++)
            {
                if (pattern.charAt(j) == '-')
                {
                    //Do nothing, we don't know if it's correct or not
                }
                else if (pattern.charAt(j) != '-')
                {
                    if(pattern.charAt(j) == i.charAt(j))
                    {
                        //Do nothing, hopefully the rest of the word works out
                    }
                    else if(pattern.charAt(j) != i.charAt(j))
                    {
                        shouldWordAdded = false;
                    }
                }
            }
            if(shouldWordAdded)
            {
                guessedLetters.add(i);
            }
        }

        //This will give something like {a = 1, b = 2,...}
        Map<Character, Integer> map = new TreeMap<>();
        map = getFreqMapThatMatchesPattern(guessedLetters);


        //This entire thing is to find the largest value in the map
        //Should be correct I just copypasted from naiveletter
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
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}