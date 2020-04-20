package ui;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Text extends javafx.scene.text.Text {

    public Text(String text) {
        super(text);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(0);
        ds.setRadius(3.0);
        ds.setColor(Color.color(0, 0, 0));

        this.setEffect(ds);
    }
}
