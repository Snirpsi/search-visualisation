package application.debugger;

public class Debugger {

	private static boolean autostep = false;

	private static double pauseTimeInSeconds = 1.0;

	private static final Object mutex = new Object();

	private static String consoleText = "";

	public static void pause() {
		if (autostep) {
			try {
				Thread.sleep((long) (pauseTimeInSeconds * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {

			synchronized (mutex) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void pause(String breakpointDescription) {
		if (breakpointDescription != null) {
			consoleText = consoleText + "\n" + breakpointDescription;
		}
		pause();
	}

	protected static void resume() {
		System.out.println("RESUME1");
		synchronized (mutex) {
			System.out.println("RESUME2");
			mutex.notifyAll();
		}

	}

	protected static void autostepEnable() {
		autostep = true;
	}

	protected static void autostepDisable() {
		autostep = false;
	}

	protected static void setPauseTime(double seconds) {
		if (seconds < 0.01) {
			pauseTimeInSeconds = 0.01;
			return;
		}
		pauseTimeInSeconds = seconds;
	}

	protected static String getConsoleText() {
		return consoleText;
	}
	
	
}
