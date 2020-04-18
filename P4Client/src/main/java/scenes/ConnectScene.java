package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.*;

public class ConnectScene extends MyScene {

    public ConnectScene() {
        super();

        this.rootBox = new VBox(
                new StackPane(new ExitButton(30)),
                UIStatic.spacer(10, 0),
                new Title(500),
                UIStatic.spacer(30, 0),
                this.createForm()
        );
        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.setPadding(new Insets(10,10,10,10));
        this.rootStack.getChildren().addAll(new Background(), rootBox);
    }

    private VBox createForm() {
        VBox vbox = new VBox();
        HBox fields = new HBox();

        fields.getChildren().addAll(
            new TextField("127.0.0.1", 120, true),
            UIStatic.spacer(0, 10),
            new TextField("5555", 70)
        );
        vbox.getChildren().addAll(
                fields,
                UIStatic.spacer(10, 0),
                new Button("START!", 200, UIStatic.GREEN, e -> {
                    UIStatic.setScene(UIStatic.categoryScene);
                })
        );
        fields.setAlignment(Pos.TOP_CENTER);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }

}
