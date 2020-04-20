package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import logic.GameInfo;
import logic.Logic;
import ui.*;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryScene extends MyScene {

    ArrayList<Button> buttons;

    public CategoryScene() {
        super();

        buttons = new ArrayList<>();

        // Top pane
        BorderPane top = new BorderPane();
        top.setLeft(UIStatic.spacer(0, 30));
        top.setCenter(new Title(400));
        top.setRight(new StackPane(new ExitButton(30)));

        this.rootBox = new VBox(
                top,
                UIStatic.spacer(20, 0),
                new Text("Select a category:"),
                UIStatic.spacer(20, 0)
                // Buttons will be injected here
        );
        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.setPadding(new Insets(10,10,10,10));
        this.rootStack.getChildren().addAll(new LayeredBackground(), rootBox);
    }

    public void createButtons(ArrayList<String> names) {
        VBox buttonsBox = new VBox();
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        // Creating buttons
        for(int i=0; i<names.size(); i++) {
            int finalI = i;
            Button button = new Button(names.get(i), 200, e -> {
                Logic.currectCategory = names.get(finalI);
                UIStatic.setScene(UIStatic.gameScene);

                int numLetters = 1;
                for (Pair<String, Integer> pair: Logic.gameInfo.categories_numLetters) {
                    if (pair.getKey().equals(names.get(finalI))) {
                        numLetters = pair.getValue();
                    }
                }
                // TODO: Request letters from server
                UIStatic.gameScene.initGameBox(numLetters, new ArrayList<>(Arrays.asList('A','B','C','D','E','F')));
            });
            this.buttons.add(button);
            if (i > 0) {
                buttonsBox.getChildren().add(UIStatic.spacer(10, 0));
            }
            buttonsBox.getChildren().add(button);
        }

        // Deleting previous buttons if needed
        if (this.rootBox.getChildren().get(this.rootBox.getChildren().size()-1).getClass().getName().equals("javafx.scene.layout.VBox")) {
            this.rootBox.getChildren().remove(this.rootBox.getChildren().size()-1);
        }

        // Adding to scene
        this.rootBox.getChildren().add(buttonsBox);
    }

}
