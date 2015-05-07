package chatSystem;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Created by magnusfinvik on 29.04.2015.
 */
public class ClientController implements Initializable{

    @FXML
    TextField messageArea;
    @FXML
    TextArea messageOutput;
    @FXML
    TextField nameTag;
    @FXML
    Button connect;

    private Socket socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    @FXML
    public void handle() {
        try{
            String message = messageArea.getText().trim();
            String name = nameTag.getText().trim();
            Message m = new Message(name, message);
            toServer.writeObject(m);

        } catch (UnknownHostException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void streamCreatorAndMore() {
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Message outPrint = (Message) fromServer.readObject();
                Platform.runLater(() -> {
                    String name = outPrint.getName();
                    String mess = outPrint.getMessage();
                    messageOutput.appendText(name + ": " + mess + "\n");
                    messageArea.clear();
                });
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void connect() {
        try {
            socket = new Socket("localhost", 8000);
            new Thread(() -> {
                    streamCreatorAndMore();
            }).start();
        } catch (Exception e){

        }
    }
}
