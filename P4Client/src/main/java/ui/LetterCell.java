package ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LetterCell extends VBox {

    public static final double opacity = 0.75;
    public Text text;

    public LetterCell(char letter) {
        this.text = new Text("" + letter);

        this.getChildren().add(this.text);

        this.makeDefault();
    }

    public LetterCell(LetterCell cell) {
        this.text = new Text(cell.text.getText());
        this.getChildren().add(this.text);
        this.setBackground(cell.getBackground());
    }

    public void makeRed() {
        this.setBackground(new Background(new BackgroundFill(Color.web("#DC143C", opacity), new CornerRadii(3), Insets.EMPTY)));
    }

    public void makeDefault() {
        this.setBackground(new Background(new BackgroundFill(Color.web("#fff", opacity), new CornerRadii(3), Insets.EMPTY)));
    }
}
