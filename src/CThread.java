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

public class CThread extends Thread
{
    public Socket socket;
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

            printUsers();
            String clientName = reader.readLine();
            server.addClientName(clientName);

            //announce new user
            String serverMsg = "new user connected: " + clientName;
            server.broadcast(serverMsg, this);

            String clientMsg;

            // at this point dont wanna send with user name
            do{
                clientMsg = reader.readLine();
                serverMsg = "[" + clientName + "]" + clientMsg;
                server.broadcast(serverMsg, this);
            }while(true);

            /*
            server.disconnectClient(clientName, this);
            socket.close();

            serverMsg = clientName + "has left";
            server.broadcast(serverMsg, this);*/

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

    public byte[] getMsg(Socket socket)
    {
        try
        {
            byte[] msg;
            DataInputStream input = new DataInputStream(socket.getInputStream());
            int length = input.readInt(); // getting the length of array
            if (length > 0)
            {
                msg = new byte[length];
                input.readFully(msg, 0, msg.length);
            }
            else{
                msg = new byte[0];
                msg[0] = 0;
            }
            return msg;
        }
        catch (IOException ex)
        {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
