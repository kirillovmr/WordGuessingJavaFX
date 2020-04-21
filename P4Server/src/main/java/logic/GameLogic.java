package logic;

import javafx.util.Pair;

import java.util.*;

public class GameLogic {

    private GameLogic() {}

    private static ArrayList<String> categories = new ArrayList<>(Arrays.asList("Animals", "Cities", "Food", "Flora", "Countries", "Home", "Car brands"));
    private static List<List<String>> words = new ArrayList<>();

    private static void initWordsArray() {
        words = new ArrayList<>();
        words.add(Arrays.asList("Elephant", "Tiger", "Godzilla", "Kangaroo", "Rabbit", "Penguin", "Iguana", "Antelope", "Cheetah", "Flamingo", "Leopard", "Squirrel", "Zebra"));
        words.add(Arrays.asList("Zurich", "Chicago", "Dublin", "Munich", "London", "Mumbai", "Madrid", "Houston", "Budapest", "Brussels", "Athens", "Warsaw", "Hamburg", "Helsinki", "Seville", "Bilbao"));
        words.add(Arrays.asList("Avocado", "Ginger", "Garlic", "Onion", "Orange", "Broccoli", "Chicken", "Coffee", "Dumpling", "Hummus", "Lobster", "Lasagna", "Pancake", "Yogurt", "Zucchini"));
        words.add(Arrays.asList("Sakura", "Ambrosia", "Almond", "Baobab", "Chestnut", "Lavender", "Mimosa", "Acacia", "Tulip", "Apricot", "Cashew", "Birch", "Cherry", "Acorn"));
        words.add(Arrays.asList("Austria", "Bahamas", "Brazil", "Canada", "Cyprus", "Ecuador", "France", "Germany", "Greece", "Paraguay", "Portugal", "Mongolia", "Ukraine", "Jamaica", "Iceland", "Romania", "Sweden", "Turkey", "Vietnam", "Estonia"));
        words.add(Arrays.asList("Kitchen", "Knife", "Fridge", "Curtain", "Window", "Garage", "Bicycle", "Armchair", "Mattress", "Bathroom", "Mirror", "Shower", "Blanket", "Speaker", "Laptop", "Candle", "Laundry"));
        words.add(Arrays.asList("Bentley", "Bugatti", "Cadillac", "Chrysler", "Ferrari", "Hyundai", "Infiniti", "Jaguar", "Lincoln", "Maserati", "Mercedes", "Nissan", "Peugeot", "Porsche", "Renault", "Subaru", "Toyota"));
    }

    // Returns a word from the given category
    public static String getWordFromCategory(String category, ArrayList<String> blacklist) {
        if (words.size() == 0) {
            initWordsArray();
        }

        int idx = categories.indexOf(category);
        ArrayList<String> words_copy = new ArrayList<>(words.get(idx));
        for(String blackListed: blacklist) {
            words_copy.remove(blackListed);
        }
        Collections.shuffle(words_copy);
        return words_copy.remove(0);
    }

    // Returns a pair of Category - Word
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
    // Returns a pair of generated letters and remaining
    public static Pair<ArrayList<Character>, ArrayList<Character>> generateCharacters(String word) {
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

        // Pick 3 characters from alphabet
        Collections.shuffle(alphabet);
        for(int i=0; i<3; i++) {
            list.add(alphabet.remove(0));
        }

        Collections.shuffle(list);
        return new Pair<>(list, alphabet);
    }

}
