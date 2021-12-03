/*****************************
 * Purpose: Class containing all GUI code, handles creating
 * and executing the socket connection for the basic chat client
 *@author: April Trinh
 * Date Last Modified: 11/24/2021
 *******************************/

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;

public class ChatClient extends Application{
    String key = "helloworld";

    /**
     * creates a byte stream over the socket connection to receive data
     * */
    public void receiveData() throws FileNotFoundException {

    }

    /**
     * creates a byte stream over the socket connection to send data
     */
    public String sendData() {
        return null;
    }

    /**
     * GUI code
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat Client");

        BorderPane keyInput = new BorderPane(); //create key input page BorderPane
        Scene keyInputScene = new Scene(keyInput, 800, 600); //create key input scene

        BorderPane chatRoom = new BorderPane();
        Scene chatRoomScene= new Scene(chatRoom,800, 600);

        Label inputKeyLabel = new Label("Input Key");
        TextField inputKey = new TextField();
        inputKey.setOnKeyReleased(e -> {
            if (inputKey.getText().equals(key) && (e.getCode() == KeyCode.ENTER))
                primaryStage.setScene(chatRoomScene);
        });
        VBox inputKeyContainer = new VBox(inputKeyLabel, inputKey);

        keyInput.setCenter(inputKeyContainer);
        inputKeyContainer.setAlignment(Pos.CENTER);

        primaryStage.setScene(keyInputScene); //places scene onto the primary stage
        primaryStage.show(); //display
    }
	
	public void launchWindow(){
		launch();
	}
	
	/*
    public static void main(String[] args) {
        launch(args);
    }
	*/
}
