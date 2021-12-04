/*****************************
 * Purpose:
 *
 *@author: Shiv Patel
 * Date Last Modified:
 *******************************/
import java.util.Iterator;
import java.util.Date;
import java.util.Timer;

public class SessionTimer
{
    long startTime = System.currentTimeMillis();
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

        while (elapsedTime < 2601000) {
            Iterator it = connectedClients.iterator();
            while(it.hasNext()){
                it.disconnectClient(it.getClientNames, it.getClientNames);
            }
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }

}
