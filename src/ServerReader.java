/*******************************************************************************************************
* Purpose: This class is designed to run an infinite loop and listen in on the read byte stream
* 	from the ChatClient class for any data sent in by the server. This class then acts in accordance to
*	the data that it finds within the stream.
* @authors: David Maestas, April Trinh
* DATE LAST MODIFIED: 12/4/2021
******************************************************************************************************/

import java.io.*;
import java.net.*;

public class ServerReader {
	
	private Socket socket;
	
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
		this.socket = socket;
        try {
            OutputStream output = socket.getOutputStream();
            Printwriter writer = new PrintWriter(output, true);
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
	* creates a byte stream over the socket connection to send user messages
	*@param String message: the message the user wishes to send
	*/
	public void sendMessageData(String message){
        try {
            OutputStream output = socket.getOutputStream();
			output.write(message.getBytes());	//writes the message to the output stream
            Printwriter writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }//end catch
	}//end sendMessageData
	
	
}//end ServerReader