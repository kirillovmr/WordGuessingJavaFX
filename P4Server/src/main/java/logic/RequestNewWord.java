package logic;

import java.io.Serializable;

public class RequestNewWord implements Serializable {
    public String category;
    public int numLetters;

    public RequestNewWord(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RequestNewWord{" +
                "category='" + category + '\'' +
                ", numLetters=" + numLetters +
                '}';
    }
}
