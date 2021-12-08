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

public class ByteReader
{
    private Server server;

    // server runner object to reference server object
    private ServerRunner serverRunner = new ServerRunner();

    private SessionTimer timer = new SessionTimer(serverRunner.server);

    private static Vector<CThread> connectedClients = new Vector<CThread>();

    public ByteReader(Server server)
    {
        this.server = server;
    }


    //call session timer
    public void reader()
    {
        //SeedSender seedSender = new SeedSender(serverRunner.server);

        connectedClients = serverRunner.server.getConnectedClients();
        // send seeds first
        //seedSender.sendSeed();

        //start tracking the session time
        timer.TimerMethod();
        //
        while(true)
        {
                                          //each client             1 BYTE
            byte[][] XORGroupResults = new byte[connectedClients.size()] [8];
            int numClients = connectedClients.size();
            for(CThread client : connectedClients)
            {
                int currentClient = connectedClients.indexOf(client);
                // client.socket, client specific socket??
                byte [] clientXOR = client.getMsg(client.socket);
                for(int i = 0; i <= 8; i++)
                {
                    XORGroupResults [currentClient][i] = clientXOR[i];
                }
            }
        }
    }

}