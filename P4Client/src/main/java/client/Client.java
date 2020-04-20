package client;

import javafx.application.Platform;
import javafx.util.Pair;
import logic.GameInfo;
import logic.LetterCheck;
import logic.LettersRequest;
import logic.WordCheck;
import ui.UIStatic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Client extends Thread {
    private Socket socketClient;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private boolean gameInfoRequested = true;
    private boolean lettersRequested = false;
    private boolean letterCheckRequested = false;
    private boolean wordCheckRequested = false;
    private Consumer<GameInfo> gicb;
    private Consumer<ArrayList<Character>> lrcb;
    private Consumer<Pair<ArrayList<Integer>, Integer>> lccb;
    private Consumer<Pair<Boolean, Integer>> wccb;

    public Client() { }

    // Connects to the given IP
    public boolean connect(String ip, int port, Consumer<GameInfo> cb) {
        try {
            socketClient = new Socket(ip, port);
            this.gicb = cb;
            return true;
        }
        catch (Exception e) {
            System.out.println("No connect");
            return false;
        }
    }

    // Starts a thread
    public void run() {
        try {
            this.out = new ObjectOutputStream(this.socketClient.getOutputStream());
            this.in = new ObjectInputStream(this.socketClient.getInputStream());
            this.socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {
            System.out.println("Client streams exception");
        }

        // Reads the data
        while(true) {
            try {
                Object data = this.in.readObject();
                System.out.println("Received: " + data);

                if (data instanceof GameInfo && this.gameInfoRequested) {
                    GameInfo gi = (GameInfo) data;
                    Platform.runLater(() -> this.gicb.accept(gi));
                }
                else if (data instanceof LettersRequest && this.lettersRequested) {
                    LettersRequest lr = (LettersRequest) data;
                    this.lettersRequested = false;
                    Platform.runLater(() -> this.lrcb.accept(lr.letters));
                }
                else if (data instanceof LetterCheck && this.letterCheckRequested) {
                    LetterCheck lc = (LetterCheck) data;
                    this.letterCheckRequested = false;
                    Platform.runLater(() -> this.lccb.accept(new Pair<>(lc.indexes, lc.checksLeft)));
                }
                else if (data instanceof WordCheck && this.wordCheckRequested) {
                    WordCheck wc = (WordCheck) data;
                    this.wordCheckRequested = false;
                    Platform.runLater(() -> this.wccb.accept(new Pair<>(wc.correct, wc.checksLeft)));
                }
                else {
                    System.out.println("Unrecognized data received");
                }
            }
            catch(Exception e) {
                System.out.println("Client read exception");
                break;
            }
        }

        // In case of disconnect - going back to the connect scene
        Platform.runLater(() -> UIStatic.setScene(UIStatic.connectScene));
    }

    public void requestLetters(String category, Consumer<ArrayList<Character>> cb) {
        try {
            this.out.writeObject(new LettersRequest(category));
            this.lettersRequested = true;
            this.lrcb = cb;
        } catch (IOException e) {
            System.out.println("Error sending requestLetters");
        }
    }

    public void checkLetter(String category, char letter, Consumer<Pair<ArrayList<Integer>, Integer>> cb) {
        try {
            this.out.writeObject(new LetterCheck(category, letter));
            this.letterCheckRequested = true;
            this.lccb = cb;
        } catch (IOException e) {
            System.out.println("Error sending checkLetter");
        }
    }

    public void checkWord(String category, String word, Consumer<Pair<Boolean, Integer>> cb) {
        try {
            this.out.writeObject(new WordCheck(category, word));
            this.wordCheckRequested = true;
            this.wccb = cb;
        } catch (IOException e) {
            System.out.println("Error sending wordLetter");
        }
    }
}

