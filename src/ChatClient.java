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

    private BufferedReader reader;
    private PrintWriter writer;

    private String sentMessage;
    private String receivedMessage;
    private boolean closeConnection = false; //connection is closed

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

            System.out.println("Connected to the chat server");
            receiveData(socket);
            sendData(socket);

            launch(); //launch GUI

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    /**
     * creates a byte stream over the socket connection to receive data
     * @param socket
     */
    public void receiveData(Socket socket) {
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            System.out.println("Error getting input stream: " + e.getMessage());
            e.printStackTrace();
        }

        while (true) {
            try {
                getMessage(reader.readLine());
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * creates a byte stream over the socket connection to send data
     * @param socket
     */
    public void sendData(Socket socket) {
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }
    }

    /**
     * helper method to display received message on GUI
     * @param text
     */
    public void getMessage(String text) {
        messages.getChildren().add(new Label("Anon: " + text));
    }

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
            }
        });
        Button sendButton = new Button("Send"); //click to send message
        sendButton.setOnAction(e -> {
            if (!sendMessage.getText().isEmpty()) {
                sendMessage(sendMessage.getText()); //send message
                messages.getChildren().add(new Label("You: " + sendMessage.getText())); //append new message onto chat window
                System.out.println(sendMessage.getText());
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
