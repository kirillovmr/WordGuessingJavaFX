package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Title extends ImageView {

    public Title(double width) {
        super(UIStatic.loadImage("title_image.png"));

        Image image = this.getImage();
        double _width = image.getWidth();
        double _height = image.getHeight();
        double height = _height * width / _width;

        this.setFitWidth(width);
        this.setFitHeight(height);
    }
}
