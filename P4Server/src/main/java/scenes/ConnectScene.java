package scenes;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.GameLogic;
import ui.*;

public class ConnectScene extends MyScene {

    public ConnectScene() {
        super();

        TextField ipField = new TextField("127.0.0.1", 120, true);
        ipField.setDisable(true);
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
                            int port = Integer.parseInt(portField.getText());
                            if (GameLogic.server.startServer(port)) {
                                GameLogic.server.start();
                                UIStatic.setScene(UIStatic.activeScene);
                                UIStatic.gameLog.add("Server successfully launched on port " + port + ".");
                            } else {
                                System.out.println("Error initializing a server");
                            }
                        })
                )
        );
    }
}
