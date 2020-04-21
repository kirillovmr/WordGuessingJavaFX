package scenes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Logic;
import ui.*;

public class ConnectScene extends MyScene {

    public ConnectScene() {
        super();

        TextField ipField = new TextField("127.0.0.1", 120, true);
        TextField portField = new TextField("4444", 70);

        this.rootBox.getChildren().addAll(
            this.createTopPane(),
            UIStatic.spacer(30, 0),

            new VBox(
                new HBox(
                    ipField,
                    UIStatic.spacer(0, 10),
                    portField
                ),
                UIStatic.spacer(10, 0),

                new Button("START!", 200, e -> {
                    if (Logic.client.connect(ipField.getText(), Integer.parseInt(portField.getText()), gameInfo -> {
                        Logic.gameInfo = gameInfo;
                        UIStatic.categoryScene.createButtons(Logic.extractCategoriesFromGameInfo(gameInfo));
                    })) {
                        Logic.client.start();
                        UIStatic.setScene(UIStatic.categoryScene);
                    } else {
                        System.out.println("Error connecting to server");
                    }
                })
            )
        );
    }
}
