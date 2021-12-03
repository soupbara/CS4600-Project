
/***********************************************************
* Purpose: This class is designed to start the client for the
* 	anonymous chat program.
* @author: David Maestas
* DATE LAST MODIFIED: 12/3/2021
************************************************************/
public class ClientRunner{
	
	public static void main(String args[]){
		//Creates an object of the ChatClient class
		ChatClient c = new ChatClient();
		//calls the launchWindow method from ChatClient
		//to run the Chat Client
		c.launchWindow();
	}//end main
	
}//end ClientRunner