package scenes;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;
import logic.Logic;
import ui.*;

import java.util.ArrayList;
import java.util.Arrays;


public class GameScene extends MyScene {

    StackPane gameBox;
    ArrayList<LetterCell> serverGuessed;
    ArrayList<LetterCell> playerGuessed;

    int guessesLeft;

    public GameScene() {
        super();

        // Top pane
        BorderPane top = new BorderPane();
        top.setLeft(UIStatic.spacer(0, 30));
        top.setCenter(new Title(400));
        top.setRight(new StackPane(new ExitButton(30)));

        this.rootBox = new VBox(
                top,
                UIStatic.spacer(10, 0)
        );
        this.initGameBox(7, new ArrayList<>(Arrays.asList('Z', 'A', 'L', 'S', 'E', 'P')));

        this.rootBox.setAlignment(Pos.TOP_CENTER);
        this.rootBox.setPadding(new Insets(10,10,10,10));
        this.rootStack.getChildren().addAll(new LayeredBackground(), rootBox);
    }

    public void initGameBox(int numLetters, ArrayList<Character> playerLetters) {
        this.gameBox = new StackPane();
        this.gameBox.setAlignment(Pos.TOP_CENTER);

        this.guessesLeft = 6;

        Text sgText = new Text("Server guessed:");
        this.gameBox.getChildren().add(sgText);

        // Creating and aligning server guessed letter cells
        this.serverGuessed = new ArrayList<>();
        boolean even = numLetters % 2 == 0;
        int middle = (int) Math.floor((double)numLetters / 2);
        for(int i=0; i<numLetters; i++) {
            LetterCell cell = new LetterCell(' ');
            this.serverGuessed.add(cell);
            this.gameBox.getChildren().add(cell);

            int finalI = i;
            this.translate(cell, 0, 30, 1, e -> {
                this.translate(cell, (-middle + finalI + (even ? 0.5 : 0)) * 50, 0, 500, null);
            });
        }

        Text glText = new Text("Try to guess a letter:");
        this.gameBox.getChildren().add(glText);
        this.translate(glText, 0, 80, 1, null);

        // Front declaration of guesses left text
        Text gLeftText = new Text("" + this.guessesLeft + " guesses left");

        // Creating and aligning player guess letter cells
        this.playerGuessed = new ArrayList<>();
        for(int i=0; i<playerLetters.size(); i++) {
            LetterCell cell = new LetterCell(playerLetters.get(i));
            this.playerGuessed.add(cell);
            this.gameBox.getChildren().add(cell);

            int finalI = i;
            int finalI1 = i;
            this.translate(cell, 0, 110, 1, e -> {
                this.translate(cell, (-3 + finalI + 0.5) * 50, 0, 500, e2 -> {
                    cell.setOnMouseClicked(onClink -> {
                        if (this.guessesLeft > 0) {
                            // TODO: ASK FOR NEW LETTER AS WELL
                            Logic.client.checkLetter(Logic.currectCategory, playerLetters.get(finalI1), pair -> {
                                if (pair.getKey().size() > 0) {
                                    this.correctGuess(cell, pair.getKey(), '?');
                                    // TODO: ADJUST GUESSES LEFT FROM THE SERVER RESPONSE
                                }
                                else {
                                    this.wrongGuess(cell, '?');
                                }
                            });
                        }
                        this.fade(gLeftText, 1, 0, 200, e3 -> {
                            this.guessesLeft = this.guessesLeft > 0 ? this.guessesLeft - 1 : 0;
                            gLeftText.setText("" + this.guessesLeft + " guesses left");
                            this.fade(gLeftText, 0, 1, 200, null);
                        });
                    });
                });
            });
        }

//        Text gLeftText = new Text("" + this.guessesLeft + " guesses left");
        gLeftText.setStyle("-fx-font-size: 15");
        this.gameBox.getChildren().add(gLeftText);
        this.translate(gLeftText, 0, 155, 1, null);

        Text gwText = new Text("Or guess a word and press enter:");
        this.gameBox.getChildren().add(gwText);
        this.translate(gwText, 0, 180, 1, null);

        // Front declaration of press enter test
        // TODO: Adjust guesses left accordingly
        Text peText = new Text("3 guesses left");

        // Creating input field
        TextField inputWord = new TextField("ANCIENT", 200, true);
        this.gameBox.getChildren().add(inputWord);
        this.translate(inputWord, 0, 210, 1, null);
        inputWord.setOnEnter(value -> {
            // If player guessed the word correctly
            if (value.length() == 7) {
                for(int i=0; i<value.length(); i++) {
                    this.flip(this.serverGuessed.get(i), 200, value.charAt(i), e -> {
                        // Creating a list of elements to remove
                        ArrayList<Node> toRemove = new ArrayList<>(Arrays.asList(sgText, glText, gLeftText, gwText, peText, inputWord));
                        toRemove.addAll(playerGuessed);

                        // Removing them from the scene
                        for(Node node: toRemove) {
                            this.fade(node, 1, 0, 100, onFinish -> {
                                this.gameBox.getChildren().remove(node);
                            });
                        }

                        // Scaling the result
                        for(LetterCell cell: serverGuessed) {
                            int duration = 300;
                            this.scale(cell, 0.3, duration, null);
                            this.translate(cell, cell.getTranslateX() * 0.7, 0, duration, null);
                        }
                    });
                }

                // Showing win text
                Text winText = new Text("YOU GUESSED!");
                winText.setVisible(false);
                this.gameBox.getChildren().add(winText);
                this.translate(winText, 0, 120, 1, null);

                // TODO: Request serfor for categories, if less then 1 -> go to win screen

                // Redirect text
                int secondsToGo = 4;
                Text redirText = new Text("Going to the category selection in " + (secondsToGo-1) + "..");
                redirText.setVisible(false);
                this.gameBox.getChildren().add(redirText);
                this.translate(redirText, 0, 160, 1, null);

                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {
                    int i = secondsToGo;

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (i == secondsToGo) {
                            winText.setVisible(true);
                            redirText.setVisible(true);
                        }
                        else {
                            fade(redirText, 1, 0, 300, e -> {
                                redirText.setText("Going to the category selection in " + i + " ..");
                                fade(redirText, 0, 1, 300, null);
                            });
                        }
                        i -= 1;
                    }
                }));
                timeline.setOnFinished(onFinish -> {
                    // TODO: Request remaining categories from server
                    UIStatic.setScene(UIStatic.categoryScene);
                    UIStatic.categoryScene.createButtons(new ArrayList<>(Arrays.asList("Category 1", "Category 2")));
                });
                timeline.setCycleCount(secondsToGo);
                timeline.play();
            }
            // If user guessed wrong
            else {
                int duration = 30;
                this.translate(inputWord, 15, 0, duration, e -> {
                    this.translate(inputWord, -30, 0, duration*2, e2-> {
                        this.translate(inputWord, 30, 0, duration*2, e3 -> {
                            this.translate(inputWord, -30, 0, duration*2, e4 -> {
                                this.translate(inputWord, 15, 0, duration, null);
                            });
                        });
                    });
                });
            }
        });

//        Text peText = new Text("and press ENTER");
        peText.setStyle("-fx-font-size: 15");
        this.gameBox.getChildren().add(peText);
        this.translate(peText, 0, 255, 1, null);

        // Deleting previous gameBox if needed
        if (this.rootBox.getChildren().get(this.rootBox.getChildren().size()-1).getClass().getName().equals("javafx.scene.layout.StackPane")) {
            this.rootBox.getChildren().remove(this.rootBox.getChildren().size()-1);
        }

        // Adding to scene
        this.rootBox.getChildren().add(this.gameBox);
    }

    private void correctGuess(LetterCell cell, ArrayList<Integer> correctIndexes, char newLetter) {
        for (int correctIndex: correctIndexes) {
            LetterCell cellToOpen = this.serverGuessed.get(correctIndex);
            double byX = cellToOpen.getTranslateX() - cell.getTranslateX();
            double byY = cellToOpen.getTranslateY() - cell.getTranslateY();

            // Creating a temp cell
            LetterCell newPlayerCell = new LetterCell(cell);
            this.gameBox.getChildren().add(newPlayerCell);

            // Moving a temp cell on top of clicked cell
            this.translate(newPlayerCell, cell.getTranslateX(), cell.getTranslateY(), 1, e-> {

                // Moving a temp cell above the cellToOpen
                this.fade(cellToOpen, 1, 0, 500, null);
                this.translate(newPlayerCell, byX, byY, 500, e2 -> {

                    // Replacing text in cellToOpen and displaying it
                    cellToOpen.text.setText(newPlayerCell.text.getText());
                    this.fade(cellToOpen, 0, 1, 1, null);

                    // Removing temp cell
                    this.fade(newPlayerCell, 1, 0, 1, onFinish -> {
                        this.gameBox.getChildren().remove(newPlayerCell);
                    });

                    // Displaying new letter in player guess area
                    this.fade(cell, 1, 0, 300, onFinish -> {
                        cell.text.setText("" + newLetter);
                        this.fade(cell, 0, 1, 300, null);
                    });
                });
            });
        }
    }

    private void wrongGuess(LetterCell cell, char newLetter) {
        int duration = 50;
        cell.makeRed();
        this.rotate(cell, 30, duration, e -> {
            this.rotate(cell, -60, duration*2, e2 -> {
                this.rotate(cell, 60, duration*2, e3 -> {
                    this.rotate(cell, -60, duration*2, e4 -> {
                        this.rotate(cell, 30, duration, e5 -> {
                            this.fade(cell, 1, 0, 200, e6 -> {
                                cell.makeDefault();
                                cell.text.setText("" + newLetter);
                                this.fade(cell, 0, 1, 200, null);
                            });
                        });
                    });
                });
            });
        });
    }

    private void flip(LetterCell cell, int duration, char newLetter, EventHandler<ActionEvent> onFinish) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(duration), cell);
        transition.setByX(-1);
        transition.setOnFinished(e -> {
            cell.text.setText("" + newLetter);
            transition.setByX(1);
            transition.setOnFinished(onFinish);
            transition.play();
        });
        transition.play();
    }

    private void scale(Node node, double scaleBy, int duration, EventHandler<ActionEvent> onFinish) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(duration), node);
        transition.setByX(scaleBy);
        transition.setByY(scaleBy);
        transition.setOnFinished(onFinish);
        transition.play();
    }

    private void rotate(Node node, double angle, int duration, EventHandler<ActionEvent> onFinish) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), node);
        rt.setByAngle(angle);
        rt.setOnFinished(onFinish);
        rt.play();
    }

    private void fade(Node node, double from, double to, int duration, EventHandler<ActionEvent> onFinish) {
        FadeTransition ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.setOnFinished(onFinish);
        ft.play();
    }

    private void translate(Node node, double byX, double byY, int duration, EventHandler<ActionEvent> onFinish) {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.millis(duration));
        t.setNode(node);
        t.setByX( byX );
        t.setByY( byY );
        t.setOnFinished(onFinish);
        t.play();
    }

}
