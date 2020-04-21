package communication;

import java.io.Serializable;
import java.util.ArrayList;

public class LetterCheck implements Serializable {

    public String category;
    public char letter;
    public ArrayList<Integer> indexes;
    public int checksLeft;
    public char newLetter;

    public LetterCheck(String category, char letter) {
        this.category = category;
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "LetterCheck{" +
                "category='" + category + '\'' +
                ", letter=" + letter +
                ", indexes=" + indexes +
                ", checksLeft=" + checksLeft +
                ", newLetter=" + newLetter +
                '}';
    }
}
