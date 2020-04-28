package scenes;

import ui.GameLog;
import ui.UIStatic;

public class ActiveScene extends MyScene {

    public ActiveScene() {
        super();

        UIStatic.gameLog = new GameLog();

        this.rootBox.getChildren().addAll(
                this.createTopPane(),
                UIStatic.spacer(30, 0),
                UIStatic.gameLog
        );
    }
}
