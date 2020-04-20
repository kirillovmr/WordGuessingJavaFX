package logic;

import javafx.util.Pair;

import java.util.*;

public class GameLogic {
    private GameLogic() {}

    private static ArrayList<String> categories = new ArrayList<>(Arrays.asList("Animals", "Cities", "Food", "Flora"));
    private static List<List<String>> words = new ArrayList<>();

    private static void initWordsArray() {
        words = new ArrayList<>();
        words.add(Arrays.asList("Elephant", "Tiger", "Godzilla", "Kangaroo"));
        words.add(Arrays.asList("Zurich", "Chicago", "Dublin", "Munich", "London"));
        words.add(Arrays.asList("Burger", "Ginger", "Garlic", "Onion", "Orange"));
        words.add(Arrays.asList("Sakura", "Ambrosia", "Almond", "Baobab", "Chestnut"));
    }

    public static ArrayList<Pair<String, String>> generateWords() {
        ArrayList<Pair<String, String>> list = new ArrayList<>();

        if (words.size() == 0) {
            initWordsArray();
        }

        ArrayList<String> categories_copy = new ArrayList<>(categories);
        for (int i=0; i<3; i++) {
            String category, word;

            Random rand = new Random();
            category = categories_copy.remove(rand.nextInt(categories_copy.size()));
            word = words.get(categories.indexOf(category)).get(rand.nextInt(words.get(categories.indexOf(category)).size()));
            list.add(new Pair<>(category, word));
        }

        return list;
    }

    // Generating 3 letters from word and 3 not
    public static ArrayList<Character> generateCharacters(String word) {
        ArrayList<Character> list = new ArrayList<>();
        ArrayList<Character> alphabet = new ArrayList<>(Arrays.asList(
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
        ));

        // Creating list of word characters
        String upper_word = word.toUpperCase();
        ArrayList<Character> wordCharacters = new ArrayList<>();
        for (int i=0; i<word.length(); i++) {
            if (!wordCharacters.contains(upper_word.charAt(i))) {
                wordCharacters.add(upper_word.charAt(i));
            }
        }

        // Pick 3 characters from the word
        Collections.shuffle(wordCharacters);
        for(int i=0; i<3; i++) {
            Character c = wordCharacters.remove(0);
            alphabet.remove(c);
            list.add(c);
        }

        // Remove remaining word characters from the alphabet
        for(int i=0; i<wordCharacters.size(); i++) {
            alphabet.remove(wordCharacters.remove(0));
        }

        // Pick 3 characters from alphabet
        Collections.shuffle(alphabet);
        for(int i=0; i<3; i++) {
            list.add(alphabet.remove(0));
        }

        Collections.shuffle(list);
        return list;
    }
}
