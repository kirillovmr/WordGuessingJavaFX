package scenes;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import logic.Logic;
import ui.*;

import java.util.ArrayList;

public class CategoryScene extends MyScene {

    public CategoryScene() {
        super();

        this.rootBox.getChildren().addAll(
            this.createTopPane(),
            UIStatic.spacer(20, 0),
            new Text("Select a category:"),
            UIStatic.spacer(20, 0)
        );
    }

    public void createButtons(ArrayList<String> names) {
        VBox buttonsBox = new VBox();
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        // Creating buttons
        for(int i=0; i<names.size(); i++) {
            int finalI = i;
            Button button = new Button(names.get(i), 200, e -> {
                Logic.currentCategory = names.get(finalI);
                UIStatic.setScene(UIStatic.gameScene);

                int numLetters = 1;
                for (Pair<String, Integer> pair: Logic.gameInfo.categories_numLetters) {
                    if (pair.getKey().equals(names.get(finalI))) {
                        numLetters = pair.getValue();
                    }
                }

                // Requesting letters
                int finalNumLetters = numLetters;
                Logic.client.requestLetters(names.get(finalI), playerLetters -> UIStatic.gameScene.initGameBox(finalNumLetters, playerLetters));
            });
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
