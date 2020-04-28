package scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.ExitButton;
import ui.LayeredBackground;
import ui.Title;
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
        this.rootStack = new StackPane(new LayeredBackground());
        this.rootStack.getStyleClass().add("rootStack");
        this.scene = new Scene(this.rootStack, width, height);

        this.scene.getStylesheets().add("styles/Style.css");

        this.rootBox = new VBox();
        this.rootBox.getStyleClass().add("rootBox");
        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootStack.getChildren().add(rootBox);

        this.rootStack.setOnMousePressed(event -> {
            this.xOffset = event.getSceneX();
            this.yOffset = event.getSceneY();
        });
        this.rootStack.setOnMouseDragged(event -> {
            UIStatic.primaryStage.setX(event.getScreenX() - this.xOffset);
            UIStatic.primaryStage.setY(event.getScreenY() - this.yOffset);
        });
    }

    protected BorderPane createTopPane() {
        BorderPane pane = new BorderPane();

        pane.setLeft(UIStatic.spacer(0, 20));
        pane.setCenter(new Title(400));
        pane.setRight(new StackPane(new ExitButton(30)));

        return pane;
    }

    public Scene getScene() {
        return this.scene;
    }
}
