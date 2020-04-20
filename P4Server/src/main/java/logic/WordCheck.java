package logic;

import java.io.Serializable;

public class WordCheck implements Serializable {
    public String category;
    public String word;
    public boolean correct;
    public int checksLeft;

    public WordCheck(String category, String word) {
        this.category = category;
        this.word = word;
    }

    @Override
    public String toString() {
        return "WordCheck{" +
                "category='" + category + '\'' +
                ", word='" + word + '\'' +
                ", correct=" + correct +
                ", checksLeft=" + checksLeft +
                '}';
    }
}
