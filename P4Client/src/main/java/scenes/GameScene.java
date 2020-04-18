package scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ui.*;

import java.util.ArrayList;


public class GameScene extends MyScene {

    ArrayList<LetterCell> serverGuessed;

    public GameScene() {
        super();

        // Top pane
        BorderPane top = new BorderPane();
        top.setLeft(UIStatic.spacer(0, 30));
        top.setCenter(new Title(500));
        top.setRight(new StackPane(new ExitButton(30)));

        this.rootBox = new VBox(
                top,
                UIStatic.spacer(10, 0)
        );
        this.initGameBox(7);

        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.setPadding(new Insets(10,10,10,10));
        this.rootStack.getChildren().addAll(new Background(), rootBox);
    }

    public void initGameBox(int numLetters) {
        StackPane gameBox = new StackPane();
        gameBox.setAlignment(Pos.TOP_CENTER);

        Text sgText = new Text("Server guessed:");
        gameBox.getChildren().add(sgText);

        // Creating and aligning letter cells
        this.serverGuessed = new ArrayList<>();
        boolean even = numLetters % 2 == 0;
        int middle = (int) Math.floor((double)numLetters / 2);
        for(int i=0; i<numLetters; i++) {
            LetterCell cell = new LetterCell(' ');
            this.serverGuessed.add(cell);
            gameBox.getChildren().add(cell);

            this.translate(cell, (-middle + i + (even ? 0.5 : 0)) * 50, 30, null);
        }

        // Deleting previous buttons if needed
        if (this.rootBox.getChildren().get(this.rootBox.getChildren().size()-1).getClass().getName().equals("javafx.scene.layout.StackPane")) {
            this.rootBox.getChildren().remove(this.rootBox.getChildren().size()-1);
        }

        // Adding to scene
        this.rootBox.getChildren().add(gameBox);
    }

    private void translate(Node node, double byX, double byY, EventHandler<ActionEvent> onFinish) {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.millis(1000));
        t.setNode(node);
        t.setByX( byX );
        t.setByY( byY );
        t.setOnFinished(onFinish);
        t.play();
    }

}
