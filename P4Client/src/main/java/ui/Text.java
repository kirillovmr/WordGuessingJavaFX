package ui;

public class Text extends javafx.scene.text.Text {

    public Text(String text) {
        super(text);
        this.getStyleClass().add("dropShadow");
    }

    public Text(String text, String style) {
        super(text);
        this.getStyleClass().addAll("dropShadow", style);
    }

}
