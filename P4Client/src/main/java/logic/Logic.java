package logic;

import client.Client;
import javafx.util.Pair;

import java.util.ArrayList;

public class Logic {
    private Logic() {}

    public static Client client;
    public static GameInfo gameInfo;
    public static String currectCategory;

    // Resets all the static part
    public static void init() {
        client = new Client();
        gameInfo = null;
        currectCategory = null;
    }
}
