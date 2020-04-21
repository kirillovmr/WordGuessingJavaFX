package logic;

import client.Client;
import communication.GameInfo;
import javafx.util.Pair;

import java.util.ArrayList;

public class Logic {
    private Logic() {}

    public static Client client;
    public static GameInfo gameInfo;
    public static String currentCategory;

    // Resets all the static part
    public static void init() {
        client = new Client();
        gameInfo = null;
        currentCategory = null;
    }

    public static ArrayList<String> extractCategoriesFromGameInfo(GameInfo gameInfo) {
        ArrayList<String> categories = new ArrayList<>();
        for (Pair<String, Integer> pair: gameInfo.categories_numLetters) {
            categories.add(pair.getKey());
        }
        return categories;
    }
}
