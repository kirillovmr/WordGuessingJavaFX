package server;

import javafx.application.Platform;
import javafx.util.Pair;
import logic.GameInfo;
import logic.GameLogic;
import logic.LetterCheck;
import logic.WordCheck;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private int counter = 1;
    private ArrayList<ClientThread> clients = new ArrayList<>();
    private ServerSocket mySocket;

    public boolean startServer(int port) {
        try {
            this.mySocket = new ServerSocket(port);
            return true;
        }
        catch (IOException e) {
            System.out.println("Server socket did not launch");
            return false;
        }
    }

    public void stopServer() {
        // Closing server socket
        try { mySocket.close(); }
        catch (IOException e) {
            System.out.println("Server socket close exception");
        }

        // Closing active clients socket
        for (ClientThread t : clients) {
            try { t.connection.close(); }
            catch (IOException e) {
                System.out.println("Client socket close exception");
            }
        }
    }

    public void run() {
        try {
            System.out.println("Server is waiting for a client!");
            while(true) {
                ClientThread client = new ClientThread(mySocket.accept(), this.counter);
                System.out.println("Client has connected to server #" + counter);
                this.clients.add(client);
                client.start();
                this.counter += 1;
            }
        }
        catch(Exception e) {
            System.out.println("Server accept error");
            e.printStackTrace();
        }
    }

    private class ClientThread extends Thread {
        private int id;
        private Socket connection;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        private GameInfo gameInfo;
        private ArrayList<Pair<String, String>> categories_words;
        private int letterGuessesLeft = 6;
        private int wordGuessesLeft = 3;

        ClientThread(Socket s, int id) {
            this.connection = s;
            this.id = id;

            // Generating words
            this.categories_words = GameLogic.generateWords();
            System.out.println(this.categories_words);

            // Creating initial game info
            this.gameInfo = new GameInfo();
            this.gameInfo.categories_numLetters = new ArrayList<>();
            for (Pair<String, String> pair: this.categories_words) {
                this.gameInfo.categories_numLetters.add(new Pair<>(pair.getKey(), pair.getValue().length()));
            }
            this.gameInfo.letterGuessesLeft = this.letterGuessesLeft;
            this.gameInfo.wordGuessesLeft = this.wordGuessesLeft;
        }

        public void run() {
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
                return;
            }

            try {
                this.out.writeObject(this.gameInfo);
            } catch (IOException e) {
                System.out.println("Error sending initial game info");
                return;
            }

            while(true) {
                try {
                    Object info = in.readObject();
                    System.out.println("# " + this.id + " Received " + info);

                    if (info instanceof LetterCheck) {
                        LetterCheck lc = (LetterCheck) info;
                        lc.indexes = new ArrayList<>();

                        // Looking for presence of requested letter and storing their indexes
                        if (this.letterGuessesLeft > 0) {
                            for (Pair<String, String> pair : this.categories_words) {
                                if (pair.getKey().toUpperCase().equals(lc.category.toUpperCase())) {
                                    for (int i = 0; i < pair.getValue().length(); i++) {
                                        if (pair.getValue().toUpperCase().charAt(i) == lc.letter) {
                                            lc.indexes.add(i);
                                        }
                                    }
                                }
                            }
                        }

                        // Updating number of remaining tries
                        this.letterGuessesLeft -= 1;
                        lc.checksLeft = this.letterGuessesLeft;

                        // Sending the result back
                        this.out.writeObject(lc);
                    }
                    else if (info instanceof WordCheck) {
                        WordCheck wc = (WordCheck) info;
                        wc.correct = false;

                        // Checking is the word correct
                        if (this.wordGuessesLeft > 0) {
                            for (Pair<String, String> pair : this.categories_words) {
                                if (pair.getKey().toUpperCase().equals(wc.category.toUpperCase()) && pair.getValue().toUpperCase().equals(wc.word.toUpperCase())) {
                                    wc.correct = true;
                                }
                            }
                        }

                        // Updating number of remaining tries
                        this.wordGuessesLeft -= 1;
                        wc.checksLeft = this.wordGuessesLeft;

                        // Sending the result back
                        this.out.writeObject(wc);
                    }
                    else {
                        System.out.println("Received unrecognized object");
                    }
                }
                catch(Exception e) {
                    System.out.println("OOOOPPs...Something wrong with the socket from client: " + this.id + "....closing down!");
                    clients.remove(this);
                    break;
                }
            }
        }
    }
}





