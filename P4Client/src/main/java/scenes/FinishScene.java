package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.util.Pair;
import logic.Logic;
import ui.*;

import java.util.ArrayList;

public class FinishScene extends MyScene {

    public FinishScene() {
        super();

        // Top pane
        BorderPane top = new BorderPane();
        top.setLeft(UIStatic.spacer(0, 20));
        top.setCenter(new Title(400));
        top.setRight(new StackPane(new ExitButton(30)));

        this.rootBox = new VBox(
                top,
                UIStatic.spacer(30, 0),
                this.createButtons()
        );
        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.setPadding(new Insets(10,10,10,10));
        this.rootStack.getChildren().addAll(new LayeredBackground(), rootBox);
    }

    public VBox createButtons() {
        VBox buttonsBox = new VBox();
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        Button playButton = new Button("Play again", 200, e -> {
            Logic.client.requestNewGame(gameInfo -> {
                Logic.gameInfo = gameInfo;
                ArrayList<String> categories = new ArrayList<>();
                for (Pair<String, Integer> pair: gameInfo.categories_numLetters) {
                    categories.add(pair.getKey());
                }
                UIStatic.categoryScene.createButtons(categories);
                UIStatic.setScene(UIStatic.categoryScene);
            });
        });
        Button exitButton = new Button("Exit", 200, e -> System.exit(0));
        buttonsBox.getChildren().addAll(
                playButton,
                UIStatic.spacer(10, 0),
                exitButton
        );

        return  buttonsBox;
    }

}
