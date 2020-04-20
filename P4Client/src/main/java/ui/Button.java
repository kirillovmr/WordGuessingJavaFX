package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class Button extends javafx.scene.control.Button {

    public Button(String text, double width, EventHandler<ActionEvent> onCLick) {
        super();

        this.setText(text);
        this.setOnAction(onCLick);

        this.setMinWidth(width);
        this.setWidth(width);
        this.setMaxWidth(width);

        this.setBackground(new Background(new BackgroundFill(Color.web("F18A31"), new CornerRadii(6), Insets.EMPTY)));
        this.setTextFill(Color.web("#000"));

        this.setStyle("-fx-font-size: 20; -fx-border-width: 2; -fx-border-color: black; -fx-border-radius: 5");
    }
}
