package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class LettersRequest implements Serializable {
    public String category;
    public ArrayList<Character> letters;

    public LettersRequest(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "LettersRequest{" +
                "category='" + category + '\'' +
                ", letters=" + letters +
                '}';
    }
}
