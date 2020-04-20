import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.GameLogic;
import server.Server;

public class WordGuessServer extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("(server) Playing word guess!!!");

		Server server = new Server();
		if (server.startServer(4444)) {
			server.start();
		}
		
		Scene scene = new Scene(new HBox(),50,50);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
