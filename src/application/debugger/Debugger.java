package application.debugger;

public class Debugger {

	private static boolean autostep = false;

	private static double pauseTimeInSeconds = 1.0;

	private static final Object pauseMutex = new Object();

	private static final Object textChangeMutex = new Object();

	private static String consoleText = "";

	private static DebuggerUI debuggerUi;

	public static void pause() {
		if (autostep) {
			try {
				Thread.sleep((long) (pauseTimeInSeconds * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {

			synchronized (pauseMutex) {
				try {
					pauseMutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void pause(String breakpointDescription) {
		synchronized (textChangeMutex) {
			if (breakpointDescription != null) {
				consoleText = consoleText + "\n" + breakpointDescription;
				if (isConnected()) {
					debuggerUi.notifyTextChange();
				}

			}
		}
		pause();

	}

	public static void consolePrintln(String string) {
		synchronized (textChangeMutex) {
			if (string != null) {
				consoleText = consoleText + "\n" + string;
				if (isConnected()) {
					debuggerUi.notifyTextChange();
				}
			}
		}
	}

	protected static void resume() {
		synchronized (pauseMutex) {
			pauseMutex.notify();
		}

	}

	protected static void autostepEnable() {
		autostep = true;
		Debugger.resume();
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

	protected static void connectToUi(DebuggerUI ui) {
		debuggerUi = ui;
	}

	private static boolean isConnected() {
		if (debuggerUi != null) {
			return true;
		}
		return false;
	}

	public static  DebuggerUI getDebuggerUI() {
		if (debuggerUi != null) {
			return debuggerUi;
		} else {
			return null;
		}
	}

}
