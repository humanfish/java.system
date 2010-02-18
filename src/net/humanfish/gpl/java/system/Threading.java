package net.humanfish.gpl.java.system;

/**
 * Manage the running of several threads in the background.
 * 
 * @author Uejji
 */
public class Threading
{
	/**
	 * Maximum number of threads to allow at once.
	 */
	private static final int MAX_THREADS = (int)
		(Math.sqrt(Runtime.getRuntime().availableProcessors()) * 3);
	
	/**
	 * Thread timeout duration in milliseconds.
	 */
	private static final int THREAD_TIMEOUT = 1000;
	
	/**
	 * Primary thread array.
	 */
	private static Thread[] t = new Thread[MAX_THREADS];
	
	/**
	 * Creates a new thread from a passed Runnable object.
	 * 
	 * @param inObject - The Runnable object to start as a
	 * 					 new thread.
	 * @return Thread - The created Thread if creation was
	 * 					successful.  null if unsuccessful.
	 */
	public static Thread newThread(Runnable inObject)
	{
		// Instantiate a new Thread.
		Thread outThread = null;
		
		// Current system time in milliseconds.
		long currentTime = System.currentTimeMillis();
		
		// Iterator
		int i = 0;
		
		// Loop until we start the thread or run out of time.
		while (outThread == null)
		{
			// Break out of the loop immediately if we exceed the thread timeout.
			if ((int) (System.currentTimeMillis() - currentTime)
					>= THREAD_TIMEOUT)
				break;
			
			// If the current thread is not a running thread
			if (t[i] == null || ! t[i].isAlive())
			{
				// Start a new thread.
				t[i] = new Thread(inObject);
				t[i].start();
				
				// Set outThread to this thread.
				outThread = t[i];
			}
			
			/* If we reach the end of the Thread array without
			 * bailing, start over.
			 */
			if (++i >= t.length) i = 0;
		}
		
		// Return the started thread.
		return outThread;
	}
	
	/**
	 * Checks the status of any started Threads and determines
	 * whether any are still running.
	 * 
	 * @return True if any Thread is still running.  False if
	 * all Threads have stopped.
	 */
	public static boolean isAlive()
	{
		// Count through the entire Thread array.
		for (int i = 0; i < t.length; i++)
			
			// Return true if this Thread is instantiated and alive.
			if (t[i] != null && t[i].isAlive()) return true;
		
		// No Threads alive, so return false.
		return false;
	}
}
