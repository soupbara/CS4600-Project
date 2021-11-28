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

public class ChatClient extends Application{
    /**
     * creates a byte stream over the socket connection to receive data
     * */
    /**
     * creates a byte stream over the socket connection to send data
     */

    /**
     * GUI code
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chatroom");

        VBox vbox = new VBox(10); //create VBox
        Scene scene = new Scene(vbox, 1280, 720); //create new scene

        primaryStage.setScene(scene); //places scene onto the primary stage
        primaryStage.show(); //display
    }
	/*
    public static void main(String[] args) {
        launch(args);
    }
	*/
}
