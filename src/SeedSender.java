/*****************************
 * Purpose:
 *
 *@author: Shiv Patel
 * Date Last Modified:
 *******************************/
import java.net.;
import java.io.;
import java.util.*;
import java.security.SecureRandom;

public class SeedSender
{
    public static void main(String args[])
    {
        Iterator it = connectedClients.iterator();
        while(it.hasNext()){
            int seedNumber = it.next().seedGenerator(); //send seeds to objects
            it.sendMessage("Your seed number is: " + seedNumber);
        }
        System.out.println("All seeds have been generated and sent.");
        //call ByteReader.java
    }
}