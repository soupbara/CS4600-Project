/*****************************
 * Purpose: The CThread class will create the required objects and methods that are needed for the client connection.
 * All the messages sent by the clients will be handled by this class
 *
 *@author:
 * Date Last Modified: Alexa Tang
 *******************************/
import java.io.*;
import java.net.*;
import java.util.*;
import java.net.*;

public class CThread extends Thread
{
    private Socket socket;
    private int port;
    private PrintWriter writer;
    private Server server;
    public CThread (Socket socket, int port)
    {
        this.socket = socket;
        this.port = port;
    }
    public void run()
    {
        try
        {
            //calling the input and output streams
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            //announce new user

            //loop for reading messages


        }
        catch (IOException ex)
        {
            System.out.println("Error in the ClientThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //giving the new client a list of already connected clients
    public void printUsers()
    {
        if(server.hasUsers())
        {
            writer.println("Connected users: " + server.getClientNames());
        }
        else
        {
            writer.println("No other users connected at this time");
        }
    }

    //sending the users the message
    public void sendMessage(String message)
    {
        writer.println(message);
    }

}
