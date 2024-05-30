package application.debugger;

import application.SearchThread;
import settings.Settings;

public class Debugger {

	private static boolean autostep = false;

	private static double pauseTimeInSeconds = 1.0;

	private static final Object pauseMutex = new Object();

	private static final Object textChangeMutex = new Object();

	private static String consoleText = "";

	private static DebuggerUI debuggerUi;

	/**
	 * pauses any given search algorithm
	 */
	public static void pause() {
		// wen thread get�tet werden muss:
		killIfStoped();

		if (autostep) {
			try {
				Thread.sleep((long) (pauseTimeInSeconds * 1000));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				killIfStoped();
			}
		} else {

			synchronized (pauseMutex) {
				try {
					pauseMutex.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					killIfStoped();
				}
			}
		}
	}

	/**
	 * pauses any given search algorithm and prints a message to the application
	 * console
	 * 
	 * @param breakpointDescription
	 */
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

	/**
	 * prints a message to the application console without pausing
	 * 
	 * @param msg message to print
	 */

	public static void consolePrintln(String msg) {
		synchronized (textChangeMutex) {
			if (msg != null) {
				consoleText = consoleText + "\n" + msg;
				if (isConnected()) {
					debuggerUi.notifyTextChange();
				}
			}
		}
	}

	/**
	 * resumes the thread when user clicks step button
	 */
	public static void resume() {
		synchronized (pauseMutex) {
			pauseMutex.notifyAll();
		}

	}

	/**
	 * kills the thread if it stops
	 */
	private static void killIfStoped() {
		SearchThread searcher = (SearchThread) Thread.currentThread();

		if (searcher.toBeStopped ) {
			System.out.println("threadstoper");
			throw new RuntimeException("ThreadStoper");
		}

	}

	/**
	 * enables auto stepping
	 */
	protected static void autostepEnable() {
		autostep = true;
		Debugger.resume();
	}

	/**
	 * disables auto stepping
	 */
	protected static void autostepDisable() {
		autostep = false;
	}

	/**
	 * pauses thread
	 */
	protected static void setPauseTime(double seconds) {

		if (seconds < Settings.DEBUGGER_MINIMUM_TIME_DELAY) {
			pauseTimeInSeconds = Settings.DEBUGGER_MINIMUM_TIME_DELAY;
			return;
		}

		pauseTimeInSeconds = seconds;
	}

	/**
	 * returns the text on the console
	 * 
	 * @return
	 */
	protected static String getConsoleText() {
		return consoleText;
	}

	/**
	 * connects to one user interface to notify for new messages
	 * 
	 * @param ui
	 */
	protected static void connectToUi(DebuggerUI ui) {
		debuggerUi = ui;
	}

	/**
	 * tests if debugger is connected to the ui
	 */
	private static boolean isConnected() {
        return debuggerUi != null;
    }

	/**
	 * get connected ui
	 * 
	 * @return the debugger ui
	 */

	public static DebuggerUI getDebuggerUI() {
		if (debuggerUi != null) {
			return debuggerUi;
		} else {
			return null;
		}
	}

}

/*
 * Copyright (c) 2022 Severin Dippold
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
