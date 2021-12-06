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
    private ByteReader byterReader;
    private Vector<CThread> connectedClients;
    public void sendSeed()
    {
        for(CThread client: connectedClients){
            long seedNumber = server.seedGenerator(); //"secure" seed generator method in server class
            client.sendMessage("Your seed number is: " + seedNumber);
        }
        System.out.println("All seeds have been generated and sent.");
        //call ByteReader.java
        byterReader.reader();
    }
}