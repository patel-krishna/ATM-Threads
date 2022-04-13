import java.util.Arrays;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	int numOfPhilo;
	boolean chopsticks[];
	boolean turnToTalk;
	boolean hungryState[];

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numOfPhilo = piNumberOfPhilosophers;
		chopsticks = new boolean[piNumberOfPhilosophers];
		turnToTalk = true;
		hungryState = new boolean[piNumberOfPhilosophers];

		for (int i = 0; i < numOfPhilo; i++) {
			chopsticks[i] = true;
			hungryState[i] = true;
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
	public synchronized void pickUp(final int piTID) {
		System.out.println("DEBUG pickup piTID: " + piTID);

		// Save piTID into temp value for array indexing
		int hungryIndex = (piTID - 1) % hungryState.length;

		// Get index of left and right chopsticks based on hungryIndex
		int leftChopstick = hungryIndex % chopsticks.length;
		int rightChopstick = (hungryIndex + 1) % chopsticks.length;

		System.out.println("DEBUG L R chopstick: " + leftChopstick + " " + rightChopstick);

		// Check hungryState
		while (hungryState[hungryIndex]) {

			// DEBUG
			System.out.println("DEBUG Chopsticks array: " + Arrays.toString(chopsticks));
			System.out.println("DEBUG hungryState array: " + Arrays.toString(hungryState));

			// If chopsticks are in use, wait
			if (!chopsticks[leftChopstick] || !chopsticks[rightChopstick]) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.err.println("An error occurred in Monitor.pickup()");
				}
			}
			// If not in use, set to in use
			else {
				chopsticks[leftChopstick] = false;
				chopsticks[rightChopstick] = false;
				hungryState[hungryIndex] = false;
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		System.out.println("DEBUG putDown piTID: " + piTID);

		// Save piTID into temp value for array indexing
		int hungryIndex = (piTID - 1) % hungryState.length;

		// Get index of left and right chopsticks based on hungryIndex
		int leftChopstick = hungryIndex % chopsticks.length;
		int rightChopstick = (hungryIndex + 1) % chopsticks.length;

		System.out.println("DEBUG L R chopstick: " + leftChopstick + " " + rightChopstick);

		// Return states to true to show they are available once again
		chopsticks[leftChopstick] = true;
		chopsticks[rightChopstick] = true;
		hungryState[hungryIndex] = true;

		this.notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk() {
		// ...
		while (!turnToTalk) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		turnToTalk = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk() {
		// ...
		turnToTalk = true;
		this.notifyAll();
	}
}

// EOF
