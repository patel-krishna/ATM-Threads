
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kerly Titus
 */
public class Driver2 {

	/**
	 * Main method
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Network objNetwork = new Network(); /* Activate the network */
		objNetwork.start();

		Server objServer1 = new Server("server1"); /* Start the server */
		objServer1.start();
		Server objServer2 = new Server("server2");
		objServer2.start();
		
		// Inserting 3rd server thread
		Server objServer3 = new Server("server3");
		objServer3.start();

		Client objClient1 = new Client("sending"); /* Start the sending client thread */
		objClient1.start();
		Client objClient2 = new Client("receiving"); /* Start the receiving client thread */
		objClient2.start();
	}
}
