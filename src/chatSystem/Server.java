package chatSystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by magnusfinvik on 29.04.2015.
 */
public class Server extends Application {
    ArrayList<ObjectOutputStream> list = new ArrayList<>();

    Stage primaryStage;
    private int clientNumber = 0;
    private TextArea ta = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initialView();
    }


    private void initialView() {
        try {
            Scene scene = new Scene(new ScrollPane(ta), 450, 200);
            primaryStage.setTitle("ChatServer");
            primaryStage.setScene(scene);
            primaryStage.show();

            new Thread(() -> {
                try {
                    ServerSocket serverSocket = new ServerSocket(8000);
                    ta.appendText("MultiThreadServer started at "
                            + new Date() + '\n');

                    while (true) {
                        Socket socket = serverSocket.accept();
                        new Thread(() -> {
                           new ClientHandler(socket);
                        });

                        clientNumber++;

                        Platform.runLater(() -> {
                            ta.appendText("Starting thread for client " + clientNumber +
                                    " at " + new Date() + '\n');
                        });

                        new Thread(new ClientHandler(socket)).start();
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }).start();
        }finally {

        }
        }

    public static void main(String[]args) {
        launch(args);
    }



    private class ClientHandler implements Runnable {
        private Socket socket;
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
                list.add(outputToClient);

                while(true){
                    Message message = (Message) inputFromClient.readObject();
                    for(int i = 0; i < list.size(); i++){
                        list.get(i).writeObject(message);
                    }

                    Platform.runLater(() -> {
                        ta.appendText("message received from client \n");
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
