package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import logic.GameInfo;
import logic.Logic;
import ui.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ConnectScene extends MyScene {

    public ConnectScene() {
        super();

        // Top pane
        BorderPane top = new BorderPane();
        top.setLeft(UIStatic.spacer(0, 20));
        top.setCenter(new Title(400));
        top.setRight(new StackPane(new ExitButton(30)));

        this.rootBox = new VBox(
                top,
                UIStatic.spacer(30, 0),
                this.createForm()
        );
        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.setPadding(new Insets(10,10,10,10));
        this.rootStack.getChildren().addAll(new LayeredBackground(), rootBox);
    }

    private VBox createForm() {
        VBox vbox = new VBox();
        HBox fields = new HBox();

        TextField ipField = new TextField("127.0.0.1", 120, true);
        TextField portField = new TextField("4444", 70);

        fields.getChildren().addAll(
                ipField,
                UIStatic.spacer(0, 10),
                portField
        );
        vbox.getChildren().addAll(
                fields,
                UIStatic.spacer(10, 0),
                new Button("START!", 200, e -> {

                    if (Logic.client.connect(ipField.getText(), Integer.parseInt(portField.getText()), gi -> {
                        Logic.gameInfo = gi;
                        ArrayList<String> categories = new ArrayList<>();
                        for (Pair<String, Integer> pair: gi.categories_numLetters) {
                            categories.add(pair.getKey());
                        }
                        UIStatic.categoryScene.createButtons(categories);
                    })) {
                        Logic.client.start();
                        UIStatic.setScene(UIStatic.categoryScene);
                    } else {
                        System.out.println("Error connecting to server");
                    }
                })
        );
        fields.setAlignment(Pos.TOP_CENTER);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }

}
