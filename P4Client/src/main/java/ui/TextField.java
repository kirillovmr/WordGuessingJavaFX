package ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.Serializable;
import java.util.function.Consumer;

public class TextField extends javafx.scene.control.TextField {

    public TextField(String prompt, double width) {
        super();
        init(prompt, width, false);
    }

    public TextField(String prompt, double width, boolean delegateFocus) {
        super();
        init(prompt, width, delegateFocus);
    }

    public void setOnEnter(Consumer<String> callback) {
        this.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                callback.accept(this.getText());
            }
        });
    }

    private void init(String prompt, double width, boolean delegateFocus) {
        this.setAlignment(Pos.CENTER);
        this.setPromptText(prompt);
        this.setText(prompt);

        this.setMinWidth(width);
        this.setWidth(width);
        this.setMaxWidth(width);

        this.setStyle("-fx-font-size: 20");

        // Delegating focus to its parent if asked
        if (delegateFocus) {
            final BooleanProperty firstTime = new SimpleBooleanProperty(true);
            this.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && firstTime.get()) {
                    this.getParent().requestFocus(); // Delegate the focus to container
                    firstTime.setValue(false);
                }
            });
        }
    }
}
