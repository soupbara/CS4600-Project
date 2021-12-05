/*****************************
 * Purpose: Server class contains the socket connections for each client, the socket objects
 * for both the client and the server, and the generator for the pseudorandom seed that
 * that will be used by the client for their random 'coin tosses'
 *@author: Alexa Tang
 * Date Last Modified:
 *******************************/

import java.net.*;
import java.io.*;
import java.util.*;
import java.security.SecureRandom;
public class Server
{
    // HashSets do not allow for duplicates
    //private Set<> userThreads = new HashSet<UserThread>();

    //Vector containing the socket connections for each client
    private static Vector<CThread> connectedClients = new Vector<CThread>();
    private static Vector<String> clientNames = new Vector<String>();

    private static String address = "239.0.0.0";
    private static int port = 1234;
    public static String getAddress()
    {
        return address;
    }
    public static int getPort()
    {
        return port;
    }

    // seed for the clients to use for their "coin toss"
    public static long seedGenerator()
    {
        SecureRandom rand = new SecureRandom();
        long val = rand.nextLong();
        System.out.println("val = " + val);
        return val;
    }
    public static void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is listening to port: " + port);
            //starting the ongoing loop to listen for incoming connections from clients
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("A new user has connected");

                //creating a CThread class object
                CThread newClient = new CThread(socket, port);
                connectedClients.add(newClient);
                newClient.start();

            }
        }
        catch(IOException ex)
        {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //should we send message to *everyone* ???
    public void broadcast(String message, CThread excludeUser)
    {
        for(CThread user: connectedClients)
        {
            if (user != excludeUser)
            {
                user.sendMessage(message);
            }
        }
    }

    public boolean hasUsers()
    {
        return !connectedClients.isEmpty();
    }

    Vector<String> getClientNames()
    {
        return this.clientNames;
    }

    //storing the name of a newly connected client
    void addClientName(String name)
    {
        clientNames.add(name);
    }

    //when a client disconnects
    void disconnectClient(String name, CThread DisClient)
    {
        boolean disconnected = clientNames.remove(name);
        if(disconnected)
        {
            connectedClients.remove(DisClient);
            System.out.println("The client " + name + "has left");
        }
    }

    public Vector<CThread> getConnectedClients()
    {
        return connectedClients;
    }


    public static void main(String args[])
    {
        //getting the seed to send to the clients
        //seedGenerator();

        Server server = new Server();
        server.run();

    }
}
