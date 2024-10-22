package application;

import ai_algorithm.search.SearchAlgorithm;
import application.debugger.Debugger;
import settings.Settings;
/**
 * template for searchthread
 * @author Severin
 *
 */
public class SearchThread extends Thread {

	private final SearchAlgorithm search;

	public volatile boolean toBeStopped;

	public SearchThread(SearchAlgorithm search) {
		this.search = search;
		this.toBeStopped = false;
		this.setDaemon(true);
	}

	@Override
	public void run() {
		super.run();
		try {
			search.search();
		} catch (RuntimeException e) {
			if (Settings.DEBUGMODE) {
//				System.out.println("safely stopped");
				throw e;
			}
		}
	}

	@Override
	public void interrupt() {
		super.interrupt(); // <-- awake from sleep
		if (Settings.DEBUGMODE)
			System.out.println("interrupted");
		this.toBeStopped = true; // <-- end thread at safe place next time debugger is called
		// continues thread to next pause then cleans it up
		Debugger.resume();

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
