package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Button extends javafx.scene.control.Button {

    public Button(String text, double width, EventHandler<ActionEvent> onCLick) {
        super();

        this.setText(text);
        this.setOnAction(onCLick);

        this.setMinWidth(width);
        this.setWidth(width);
        this.setMaxWidth(width);
    }

}
