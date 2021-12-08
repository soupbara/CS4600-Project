/*****************************
 * Purpose:
 *
 *@author: Shiv Patel
 * Date Last Modified:
 *******************************/
import java.net.*;
import java.io.*;
import java.util.*;
import java.security.SecureRandom;

public class SeedSender
{
    private Server server;
    // server runner object to reference server object
    private ServerRunner serverRunner = new ServerRunner();

    private ByteReader byteReader = new ByteReader(serverRunner.server);
    private Vector<CThread> connectedClients;
    private long [] clientSeeds;

    public SeedSender(Server server)
    {
        this.server = server;
    }

    //private  CThread client;
    public void sendSeed()
    {
        long seedNumber;
        System.out.println("getting connected clients");
        connectedClients = server.getConnectedClients();

        clientSeeds = new long [connectedClients.size()];
        int numClients = connectedClients.size();

        for(int i = 0; i < numClients; i++)
        {
            seedNumber = server.seedGenerator(); //"secure" seed generator method in server class
            clientSeeds[i] = seedNumber;
        }
        int c = 0;
        for(CThread currentClient: connectedClients)
        {
            // for the last client in the group, gets the seed of the first client
            if(c == numClients-1)
            {
                currentClient.sendMessage("Your seed number is: " + clientSeeds[connectedClients.indexOf(currentClient)], currentClient.socket);
                currentClient.sendMessage("Your pairwise shared secret is: " + clientSeeds[0], currentClient.socket);
                break;
            }
            // the index of the current client should match the index of the seed in long array
            currentClient.sendMessage("Your seed number is: " + clientSeeds[connectedClients.indexOf(currentClient)], currentClient.socket);
            // the current client will recieve the seed of the next client in vector
            currentClient.sendMessage("Your pairwise shared secret is: " + clientSeeds[connectedClients.indexOf(currentClient)+1], currentClient.socket);
            c++;
        }

        System.out.println("All seeds have been generated and sent.");
        //call ByteReader.java
        byteReader.reader();
    }
}