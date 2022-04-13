/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers {
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv) {
		try {
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

			// If no argument, set philosophers to default number
			if (argv.length == 0) {
				iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
			}
			// If the argument length > 1 throw exception and exit program
			else if (argv.length > 1) {
				System.err.println("Only 1 argument is needed");
				System.err.println("Usage: java DiningPhilosophers [NumberOfPhilosophers]");
				System.exit(1);
			}
			// Else parse argument to spit out a number
			else {
				iPhilosophers = parseIntArg(argv[0]);
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Let 'em sit down
			for (int j = 0; j < iPhilosophers; j++) {
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			System.out.println(
					iPhilosophers +
							" philosopher(s) came in for a dinner.");

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for (int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		} catch (InterruptedException e) {
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * 
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException) {
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}

	/**
	 * Returns int number of philosophers from string args or handles parseInt
	 * exception
	 * 
	 * @param argument String argument
	 * @return int Number of philosophers
	 */
	public static int parseIntArg(String argument) {

		// Initialize numPhilosophers to 0
		int numPhilosophers = 0;

		// Parse argument to integer or exit program if not integer
		try {
			numPhilosophers = Integer.parseInt(argument);
		} catch (NumberFormatException ne) {
			errorMessageAndExit(argument);
		}

		// Stop program if number of philosophers is less than 0
		if (numPhilosophers < 0) {
			errorMessageAndExit(argument);
		}

		return numPhilosophers;
	}

	/**
	 * Generate error message and exit program
	 */
	public static void errorMessageAndExit(String argument) {
		System.err.printf("%s is not a positive integer.\n", argument);
		System.err.println("Usage: java DiningPhilosophers [NUMBER_OF_PHILOSOPHERS]");
		System.exit(1);
	}
}

// EOF
