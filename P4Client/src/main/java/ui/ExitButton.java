package ui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ExitButton extends ImageView {

    public ExitButton(double width) {
        super(UIStatic.loadImage("cross_mark.png"));
        this.fitImage(this.getImage(), width);

        // Alignment in stack pane
        StackPane.setAlignment(this, Pos.TOP_RIGHT);

        this.setOnMousePressed(onClink -> this.setImage(UIStatic.loadImage("cross_mark_pressed.png")));
        this.setOnMouseReleased(onClick -> System.exit(0));
    }

    private void fitImage(Image image, double width) {
        double _width = image.getWidth();
        double _height = image.getHeight();
        double height = _height * width / _width;

        this.setFitWidth(width);
        this.setFitHeight(height);
    }
}
