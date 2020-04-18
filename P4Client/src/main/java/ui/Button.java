package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Button extends javafx.scene.control.Button {

    public Button(String text, double width, Color color, EventHandler<ActionEvent> onCLick) {
        super();

        this.setText(text);
        this.setOnAction(onCLick);

        this.setMinWidth(width);
        this.setWidth(width);
        this.setMaxWidth(width);

        this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(3), Insets.EMPTY)));
        this.setTextFill(Color.web("#fff"));

        this.setStyle("-fx-font-size: 20");
    }
}
