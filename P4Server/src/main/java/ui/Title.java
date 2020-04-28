package ui;

import javafx.scene.image.ImageView;

public class Title extends ImageView {

    public Title(double width) {
        super(UIStatic.loadImage("title_image.png"));
        UIStatic.fitImage(this, this.getImage(), width);
    }

}
