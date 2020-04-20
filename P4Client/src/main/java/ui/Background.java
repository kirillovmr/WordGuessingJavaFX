package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scenes.MyScene;

public class Background extends ImageView {

    public Background() {
        super(UIStatic.loadImage("sky.png"));

        this.setFitWidth(MyScene.width);
        this.setFitHeight(MyScene.height);
    }
}
