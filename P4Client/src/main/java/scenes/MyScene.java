package scenes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.UIStatic;

public class MyScene {

    public static final double width = 700;
    public static final double height = 500;

    private Scene scene;
    protected StackPane rootStack;
    protected VBox rootBox;

    private double xOffset = 0;
    private double yOffset = 0;

    protected MyScene() {
        this.rootStack = new StackPane();
        this.scene = new Scene(this.rootStack, width, height);

        this.scene.getStylesheets().add("styles/Style.css");

        this.rootStack.setOnMousePressed(event -> {
            this.xOffset = event.getSceneX();
            this.yOffset = event.getSceneY();
        });
        this.rootStack.setOnMouseDragged(event -> {
            UIStatic.primaryStage.setX(event.getScreenX() - this.xOffset);
            UIStatic.primaryStage.setY(event.getScreenY() - this.yOffset);
        });
    }

    public Scene getScene() {
        return this.scene;
    }
}
