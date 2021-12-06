import java.net.Socket;
import java.util.Random;
import java.io.*;

/*****************************
 * Purpose: When anonymous mode is activated, this class is triggered
 * and starts and infinite loop. It uses a seed given to the client by
 * the server and the other client to generate pseudo random "coin tosses"
 *@author: April Trinh
 * Date Last Modified: 12/05/2021
 *******************************/

public class ByteSender extends ServerReader {

    private boolean stop;
	private byte[] serverToss;
	private byte[] clientToss;
	private byte[] xorResult;
	private Socket socket;

    /**
     * This class will then make a check against a boolean variable to see
     * if the user has made an inputted message in the chat client. If the
     * user has inputted a message, it will start reading the bytes of the
     * message and XORing them against the results of the XORed generated
     * coin tosses. Otherwise (no message sent), the coin tossed will be
     * sent to the server. This procedure continues until the client receives
     * a signal to stop.
     */
    public ByteSender(long serverSeed, long clientSeed) { //assuming seed is an int

        // unsure if we're supposed to generate a new value each time or not
        serverToss = coinToss(serverSeed);
        clientToss = coinToss(clientSeed);
		xorResult = new byte[serverToss.length];	//byte[] to store the XOR result
		
        //XOR these two together
		//for loop that XOR each bit of serverToss and clientToss
		//the result is stored in the byte[] xorResult
		for(int i = 0; i < serverToss.length; i++){
			xorResult[i] = (byte)(serverToss[i] ^ clientToss[i]);
		}//end for

        stop = true;

        while (!stop) {
            if (hasSentMessage()) {
                String sentMessage = getSentMessage(); //read bytes of message
                byte[] finalResult = new byte[sentMessage.length()];

                //reverse sent message and put into byte array
                byte[] temp = new byte[sentMessage.length()];
                for (int i = sentMessage.length()-1; i >= 0; i--) {
                    temp[i] = (byte)sentMessage.charAt(i);
                }

                int i = 0;
                int length;
                if (temp.length > xorResult.length) //determining which is longer
                    length = temp.length;
                else
                    length = xorResult.length;

                while (i < length) {
                    if (i >= xorResult.length) //if temp.length > xorResult.length
                        finalResult[i] = (byte)(0 ^ temp[i]);
                    else if (i >= temp.length && i > xorResult.length) //if xorResult.length > temp.length
                        finalResult[i] = (byte)(0 ^ xorResult[i]);
                    else
                        finalResult[i] = (byte)(xorResult[i] ^ temp[i]); //XOR them against result of xorResult
                    i++;
                }//end for
            }
            else { //user has not sent a message
                //send generated coin tosses and XORed value to server
                socket = new Socket();
                sendData(socket);
            }
        }
    }

    /**
     * Pseudo coin toss where each toss ia a string of numbers, and each
     * number's binary value will equal 8 coin tosses. A generated binary
     * value will be XORed.
     * @return
     */
    private byte[] coinToss(long seed) {
        // generates a random integer from 0-256 (2^8)
        // NOTE: will generate the SAME random number if given the same seed every time
        Random coin = new Random(seed);
        int toss = coin.nextInt(256);
        // output 8 bit binary as a String
        String binaryToss = String.format("%8s", Integer.toBinaryString(toss)).replaceAll(" ", "0");
        // convert String to byte array
        byte[] binaryByteArray = new byte[8];
        for (int i = 7; i >= 0; i--) {
            int temp = Integer.parseInt(String.valueOf(binaryToss.charAt(i)));
            binaryByteArray[i] = (byte)temp;
            System.out.print(binaryByteArray[i]);
        }

        return binaryByteArray;
    }

    public void sendData(Socket socket) {
        try {
            OutputStream output = socket.getOutputStream();
            output.write(xorResult);
            PrintWriter writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }
    }
}
