package chatSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by magnusfinvik on 22.04.2015.
 */
public class client extends Application {
    Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

    }

    private void initialView() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Server.class.getResource("ServerView.fxml"));
            Pane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[]args) {
        launch(args);
    }


    //input.readUTF(); blokkerer fram til den f�r noe � lese
}
