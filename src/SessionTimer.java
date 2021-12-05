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
    Server server; // server class object to access the connectedClients vector

    //long elapsedTime = 0L.
    /*public static void main(String args[])
    {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L.

        while (elapsedTime < 2601000)
        {
            Iterator it = connectedClients.iterator();
            while(it.hasNext()){
                it.disconnectClient(it.getClientNames, it.getClientNames)
            }
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }*/

    public void TimerMethod(){
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < 2601000)
        {
            Vector<CThread> connectedClients = server.getConnectedClients();
            for(CThread client: connectedClients) {
                server.disconnectClient(client.getName(), client);
            }
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }

}
