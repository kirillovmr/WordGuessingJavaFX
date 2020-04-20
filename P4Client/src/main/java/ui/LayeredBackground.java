package ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import scenes.MyScene;

import java.util.ArrayList;
import java.util.Arrays;

public class LayeredBackground extends StackPane {

    public LayeredBackground() {
        ArrayList<String> images = new ArrayList<>(Arrays.asList("sky.png", "rocks_1.png", "rocks_2.png"));
        for(String filename : images) {
            ImageView imageView = new ImageView(UIStatic.loadImage(filename));
            imageView.setFitWidth(MyScene.width);
            imageView.setFitHeight(MyScene.height);

            this.getChildren().add(imageView);
        }
    }
}
