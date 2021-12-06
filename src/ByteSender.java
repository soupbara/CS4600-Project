import java.util.Random;

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

    /**
     * This class will then make a check against a boolean variable to see
     * if the user has made an inputted message in the chat client. If the
     * user has inputted a message, it will start reading the bytes of the
     * message and XORing them against the results of the XORed generated
     * coin tosses. Otherwise (no message sent), the coin tossed will be
     * sent to the server. This procedure continues until the client receives
     * a signal to stop.
     */
    public ByteSender(int serverSeed, int clientSeed) { //assuming seed is an int

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

                //XOR them against results of XORed generated coin tosses
            }
            else { //user has not sent a message
                //send generated coin tosses and XORed value to server
            }

            //if (user signals stop) { stop = true; }
        }
    }

    /**
     * Pseudo coin toss where each toss ia a string of numbers, and each
     * number's binary value will equal 8 coin tosses. A generated binary
     * value will be XORed.
     * @return
     */
    private byte[] coinToss(int seed) {
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
}
