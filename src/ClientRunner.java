import java.io.*;

/*****************************
* Purpose: This class is designed to start the client for the
* anonymous chat program.
* @author: David Maestas
* Date Last Modified: 11/24/2021
*******************************/
public class ClientRunner{
	public static void main(String args[]) throws IOException {
		ChatClient c = new ChatClient();
		c.connect("localhost", 1234);
		c.launchWindow();
	}//end main
}//end ClientRunner