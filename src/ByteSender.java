import java.util.Random;

/*****************************
 * Purpose: When anonymous mode is activated, this class is striggered
 * and starts and infinite loop. It uses a seed given to the client by
 * the server and the other client to generate pseudo random "coin tosses"
 *@author: April Trinh
 * Date Last Modified: 12/05/2021
 *******************************/

public class ByteSender {
    private int seed; //assuming seed is an int

    public ByteSender(int seed) { //unsure if we're supposed to have one or two seeds
        this.seed = seed;
    }

    /**
     * Pseudo coin toss where each toss ia a string of numbers, and each
     * number's binary value will equal 8 coin tosses. A generated binary
     * value will be XORed. This class will then make a check against a
     * boolean variable to see if the user has made an inputted message in
     * the chat client. If the user has inputted a message, it will start
     * reading the bytes of the message and XORing them against the results
     * of the XORed generated coin tosses. Otherwise (no message sent), the
     * coin tossed will be sent to the server. This procedure continues until
     * the client receives a signal to stop.
     * @return
     */
    private int coinToss() {
        // generates a random integer from 0-256 (2^8)
        Random coin = new Random(10);
        int toss = coin.nextInt(256);
        // output 8 bit binary as a String
        String binaryToss = String.format("%8s", Integer.toBinaryString(toss)).replaceAll(" ", "0");

        return 0;
    }
}
