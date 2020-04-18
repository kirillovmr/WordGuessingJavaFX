package ui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scenes.CategoryScene;
import scenes.ConnectScene;
import scenes.GameScene;
import scenes.MyScene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UIStatic {
    private UIStatic() {}

    public static final String gameTitle = "Word Guessing Game | Client";

    public static final Color GREEN = Color.GREEN;

    // Stage
    public static Stage primaryStage;

    // Scenes
    public static ConnectScene connectScene;
    public static CategoryScene categoryScene;
    public static GameScene gameScene;

    public static void initScenes() {
        connectScene = new ConnectScene();
        categoryScene = new CategoryScene();
        gameScene = new GameScene();
    }



    public static void setScene(MyScene scene) {
        primaryStage.setScene(scene.getScene());
    }


    // Returns a VBox with the given padding
    public static HBox spacer(double height, double width) {
        HBox spacer = new HBox();
        spacer.setPadding(new Insets(height, 0, 0, width));
        return spacer;
    }

    // Returns Image for given filename
    public static Image loadImage(String filename) {
        try { return new Image(new FileInputStream("src/main/resources/images/" + filename)); }
        catch (FileNotFoundException e) {
            System.err.println(">< createImage: File " + filename + " was not found");
            return null;
        }
    }
}
