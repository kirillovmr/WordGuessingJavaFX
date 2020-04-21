package client;

import communication.*;
import javafx.application.Platform;
import javafx.util.Pair;
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
    private boolean newWordRequested = false;
    private Consumer<GameInfo> gicb;
    private Consumer<ArrayList<Character>> lrcb;
    private Consumer<LetterCheck> lccb;
    private Consumer<Pair<Boolean, Integer>> wccb;
    private Consumer<Integer> rnwcb;

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
                    Platform.runLater(() -> this.lccb.accept(lc));
                }
                else if (data instanceof WordCheck && this.wordCheckRequested) {
                    WordCheck wc = (WordCheck) data;
                    this.wordCheckRequested = false;
                    Platform.runLater(() -> this.wccb.accept(new Pair<>(wc.correct, wc.checksLeft)));
                }
                else if (data instanceof RequestNewWord && this.newWordRequested) {
                    RequestNewWord rnw = (RequestNewWord) data;
                    this.newWordRequested = false;
                    Platform.runLater(() -> this.rnwcb.accept(rnw.numLetters));
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

    public void requestNewGame(Consumer<GameInfo> cb) {
        try {
            this.out.writeObject(new GameInfo());
            this.gameInfoRequested = true;
            this.gicb = cb;
        } catch (IOException e) {
            System.out.println("Error sending requestNewWord");
        }
    }

    public void requestNewWord(String category, Consumer<Integer> cb) {
        try {
            this.out.writeObject(new RequestNewWord(category));
            this.newWordRequested = true;
            this.rnwcb = cb;
        } catch (IOException e) {
            System.out.println("Error sending requestNewWord");
        }
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

    public void checkLetter(String category, char letter, Consumer<LetterCheck> cb) {
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

