package ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TextField extends javafx.scene.control.TextField {

    public TextField(String prompt, double width) {
        super();
        init(prompt, width, false);
    }

    public TextField(String prompt, double width, boolean delegateFocus) {
        super();
        init(prompt, width, delegateFocus);
    }

    private void init(String prompt, double width, boolean delegateFocus) {
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
