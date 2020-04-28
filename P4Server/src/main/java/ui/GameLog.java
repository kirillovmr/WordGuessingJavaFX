package ui;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.text.SimpleDateFormat;

public class GameLog extends ListView<String> {

    public GameLog() {
        this.getStyleClass().add("gameLog");

        // Delegating focus
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.getParent().requestFocus();
            }
        });

        StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    }

    public void add(String text) {
        SimpleDateFormat time_formatter = new SimpleDateFormat("HH:mm:ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());

        this.getItems().add(current_time_str + ": " + text);
        this.scrollTo(Integer.MAX_VALUE);
    }
}
