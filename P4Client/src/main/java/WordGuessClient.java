import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Logic;
import ui.UIStatic;

public class WordGuessClient extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		UIStatic.primaryStage = primaryStage;
		UIStatic.primaryStage.setTitle(UIStatic.gameTitle);

		Logic.init();

		UIStatic.initScenes();
		UIStatic.primaryStage.setScene(UIStatic.connectScene.getScene());
		UIStatic.primaryStage.initStyle(StageStyle.UNDECORATED);
		UIStatic.primaryStage.show();
	}

}
