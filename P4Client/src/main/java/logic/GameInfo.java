package logic;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {

    public ArrayList<Pair<String, Integer>> categories_numLetters;
    public int letterGuessesLeft;
    public int wordGuessesLeft;

    @Override
    public String toString() {
        return "GameInfo{" +
                "categories_numLetters=" + categories_numLetters +
                ", letterGuessesLeft=" + letterGuessesLeft +
                ", wordGuessesLeft=" + wordGuessesLeft +
                '}';
    }
}