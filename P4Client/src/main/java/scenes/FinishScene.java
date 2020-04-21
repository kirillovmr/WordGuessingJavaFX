package scenes;

import javafx.scene.layout.VBox;

import logic.Logic;
import ui.*;

public class FinishScene extends MyScene {

    public FinishScene() {
        super();

        this.rootBox.getChildren().addAll(
            this.createTopPane(),
            UIStatic.spacer(30, 0),
            new VBox(
                new Button("Play again", 200, e -> Logic.client.requestNewGame(gameInfo -> {
                    Logic.gameInfo = gameInfo;
                    UIStatic.categoryScene.createButtons(Logic.extractCategoriesFromGameInfo(gameInfo));
                    UIStatic.setScene(UIStatic.categoryScene);
                })),
                UIStatic.spacer(10, 0),
                new Button("Exit", 200, e -> System.exit(0))
            )
        );
    }
}
