package ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LetterCell extends VBox {

    public Text text;

    public LetterCell(char letter) {
        this.text = new Text("" + letter);

        this.getChildren().add(this.text);

        this.setBackground(new Background(new BackgroundFill(Color.web("#fff", 0.75), new CornerRadii(3), Insets.EMPTY)));
    }
}
