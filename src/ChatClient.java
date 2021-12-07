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
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Application{
    private String key = "helloworld";
    private ByteArrayInputStream byteInput;
    private ByteArrayOutputStream byteOutput;

    private String hostname;
    private int port;

//    private BufferedReader reader;
//    private PrintWriter writer;

    private String sentMessage;
    private String receivedMessage;
    private boolean closeConnection = false; //connection is closed

    ServerReader serverReader = new ServerReader();

    private VBox messages = new VBox(); //displays sent messages

    public ChatClient() throws IOException {
        try {
            byteInput = new ByteArrayInputStream(new byte[4]);
            byteOutput = new ByteArrayOutputStream();
            //run the connection here
        } finally { //close the connection once it is done
            if (byteInput != null)
                byteInput.close();
            if (byteOutput != null) {
                byteOutput.close();
            }
        }
    }

    /**
     * Helper method to pass in the host name and the port number
     * @param hostname
     * @param port
     */
    public void connect(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void launchWindow() throws IOException {
        try {
            Socket socket = new Socket(hostname, port);
            //launch(); //launch GUI
            System.out.println("Connected to the chat server");
            serverReader.receiveData(socket);
            serverReader.sendData(socket);

            launch(); //launch GUI

            while (true) { // display received messages
                messages.getChildren().add(new Label("Anon: " + serverReader.getReceivedMessage()));
            }

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    //place where receiveData was

    //place where sendData was

    /**
     * helper method to display sent message on GUI
     */
    public void sendMessage(String text) {
        sentMessage = text;
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

        /**INPUT KEY PAGE*/
        Label inputKeyLabel = new Label("Input Key");
        TextField inputKey = new TextField();
        inputKey.setOnKeyReleased(e -> {
            if (inputKey.getText().equals(key) && (e.getCode() == KeyCode.ENTER))
                primaryStage.setScene(chatRoomScene);
        });
        VBox inputKeyContainer = new VBox(inputKeyLabel, inputKey);

        /**CHAT PAGE*/
        chatRoom.setCenter(messages);

        TextField sendMessage = new TextField("Send a message"); //where you type
        sendMessage.setOnKeyReleased(e -> {
            if (!sendMessage.getText().isEmpty() && (e.getCode() == KeyCode.ENTER)) {
                sendMessage(sendMessage.getText()); //send message
                messages.getChildren().add(new Label("You: " + sendMessage.getText())); //append new message onto chat window
                System.out.println(sendMessage.getText());
                serverReader.sendMessageData(sendMessage.getText());	//calls the sendMessageData method from ServerReader
            }
        });
        Button sendButton = new Button("Send"); //click to send message
        sendButton.setOnAction(e -> {
            if (!sendMessage.getText().isEmpty()) {
                sendMessage(sendMessage.getText()); //send message
                messages.getChildren().add(new Label("You: " + sendMessage.getText())); //append new message onto chat window
                System.out.println(sendMessage.getText());
                serverReader.sendMessageData(sendMessage.getText());	//calls the sendMessageData method from ServerReader
            }
        });
        HBox bottomBar = new HBox(sendMessage, sendButton);
        chatRoom.setBottom(bottomBar);

        keyInput.setCenter(inputKeyContainer);
        inputKeyContainer.setAlignment(Pos.CENTER);

        primaryStage.setScene(keyInputScene); //places scene onto the primary stage
        primaryStage.show(); //display
    }
	
	/*
    public static void main(String[] args) {
        launch(args);
    }
	*/
}
