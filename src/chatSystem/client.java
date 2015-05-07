package chatSystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by magnusfinvik on 22.04.2015.
 */
public class client extends Application {
    Stage primaryStage;
    private Socket socket;
    private DataOutputStream toServer;
    private DataInputStream fromServer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        initialView();

    }

    private void initialView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(chatSystem.Server.class.getResource("ClientView.fxml"));
            Pane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("ChatClient");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
        public static void main(String[]args) {
            launch(args);
        }

}
