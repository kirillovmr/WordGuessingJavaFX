package ui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ExitButton extends ImageView {

    public ExitButton(double width) {
        super(UIStatic.loadImage("cross_mark.png"));
        StackPane.setAlignment(this, Pos.TOP_RIGHT);

        Image original = this.getImage();
        Image pressed = UIStatic.loadImage("cross_mark_pressed.png");

        assert pressed != null;
        UIStatic.fitImage(this, original, width);
        UIStatic.fitImage(this, pressed, width);

        this.setOnMousePressed(onClink -> this.setImage(pressed));
        this.setOnMouseReleased(onClick -> System.exit(0));
    }

}
