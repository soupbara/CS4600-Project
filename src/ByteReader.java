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
    public void reader() {
        //SeedSender seedSender = new SeedSender(serverRunner.server);

        connectedClients = serverRunner.server.getConnectedClients();
        // send seeds first
        //seedSender.sendSeed();

        //start tracking the session time
        timer.TimerMethod();
        boolean recieved = false;
        int c = 1;
        byte[] XORresult = new byte[8];
        while (!recieved) {
            //each client             1 BYTE
            byte[][] XORGroupResults = new byte[connectedClients.size()][8];
            int numClients = connectedClients.size();
            //two arrays to complete XOR in stage 2
            //byte[] XORresult = new byte[8];
            for (CThread client : connectedClients) {
                int currentClient = connectedClients.indexOf(client);
                // client.socket, client specific socket??
                byte[] clientXOR = client.getMsg(client.socket);
                for (int i = 0; i <= 8; i++) {
                    // logging all stage one XOR results
                    XORGroupResults[currentClient][i] = clientXOR[i];
                    //skipping XOR process for first client until we get another client stage 1 result
                    if (connectedClients.indexOf(client) != 0) {
                        //doing stage 2
                        for (int k = 0; k < 8; k++) {
                            //                       previous client stage 1 result   XOR  current client's
                            XORresult[k] = (byte) (XORGroupResults[currentClient - 1][i] ^ XORGroupResults[currentClient][i]);
                        }
                    }
                }
                c++;
            }
            if (c == numClients) {
                recieved = true;
            }
        }
        //sending the results to all clients
        for (CThread client : connectedClients) {
            serverRunner.server.broadcast((String) XORresult.toString(), client);
        }

    }
}