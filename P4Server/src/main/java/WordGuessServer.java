import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.GameLogic;
import server.Server;
import ui.UIStatic;

public class WordGuessServer extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		UIStatic.primaryStage = primaryStage;
		UIStatic.primaryStage.setTitle(UIStatic.gameTitle);

		GameLogic.server = new Server();

		UIStatic.initScenes();
		UIStatic.primaryStage.setScene(UIStatic.connectScene.getScene());
		UIStatic.primaryStage.initStyle(StageStyle.UNDECORATED);
		UIStatic.primaryStage.show();
	}

}
