package ai_algorithm.specific_algorithm_logic.csp.csp_content.util;

/**
 * Link to the original source code from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/util/CancellableThread.java
 *
 * Implements a thread with an additional flag indicating cancellation.
 *
 * @author Ruediger Lunde
 * @author Mike Stampone
 * @author Alexander (Comments adjusted)
 */
public class CancellableThread extends Thread {

    public CancellableThread() {
    }

    /**
     * Constructs a new cancellable thread with the given runnable
     *
     * @param runnable the runnable to be executed by the thread
     */
    public CancellableThread(Runnable runnable) {
        super(runnable);
    }

    /**
     * Returns <code>true</code> if the current thread is cancelled
     *
     * @return <code>true</code> if the current thread is cancelled
     */
    public static boolean currIsCancelled() {
        // Check if the current thread is an instance of CancellableThread
        if (Thread.currentThread() instanceof CancellableThread)
            // Return the value of the isCancelled flag of the current thread
            return ((CancellableThread) Thread.currentThread()).isCancelled;
        return false;
    }

    private volatile boolean isCancelled; // Flag indicating if the thread is cancelled

    /**
     * Returns <code>true</code> if this thread is cancelled
     *
     * @return <code>true</code> if this thread is cancelled
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Cancels this thread
     */
    public void cancel() {
        isCancelled = true;
    }
}