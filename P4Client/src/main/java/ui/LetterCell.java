package ui;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LetterCell extends VBox {

    public Text text;

    public LetterCell(char letter) {
        this.text = new Text("" + letter);
        this.getChildren().add(this.text);
        this.makeDefault();
    }

    public LetterCell(LetterCell cell) {
        this.text = new Text(cell.text.getText());
        this.text.setStyle(cell.text.getStyle());
        this.getChildren().add(this.text);
        this.setBackground(cell.getBackground());
    }

    public void makeRed() {
        this.getStyleClass().add("letterCellRed");
    }

    public void makeDefault() {
        this.getStyleClass().remove("letterCellRed");
    }

}
