/*****************************
 * Purpose:
 *
 *@author: Shiv Patel
 * Date Last Modified:
 *******************************/
import java.util.Iterator;
import java.util.Date;
import java.util.Timer;
import java.util.Vector;

public class SessionTimer
{
    long startTime = System.currentTimeMillis();
    private Server server; // server class object to access the connectedClients vector

    public void TimerMethod(){
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        //fifteen min timer
        while (elapsedTime < 2601000)
        {
            elapsedTime = (new Date()).getTime() - startTime;
        }
        // after 15 over, disconnect all clients
        Vector<CThread> connectedClients = server.getConnectedClients();
        for(CThread client: connectedClients) {
            server.disconnectClient(client.getName(), client);
        }
    }

}
