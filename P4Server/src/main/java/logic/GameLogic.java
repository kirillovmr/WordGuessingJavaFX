package logic;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
}
