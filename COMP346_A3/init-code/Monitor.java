/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	int numOfPhilo;
	boolean chopsticks[];
	boolean turnToTalk;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numOfPhilo = piNumberOfPhilosophers;
		chopsticks = new boolean[piNumberOfPhilosophers];
		turnToTalk = true;

		for(int i = 0; i< chopsticks.length; i++){
			chopsticks[i] = true;
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		// ...
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// ...
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
		while(!turnToTalk){
			try{
				this.wait();
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		turnToTalk = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
		turnToTalk = true;
		this.notifyAll();
	}
}

// EOF
