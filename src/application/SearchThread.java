package application;

import ai_algorithm.search.SearchAlgorithm;
import application.debugger.Debugger;

public class SearchThread extends Thread {

	SearchAlgorithm search = null;

	public volatile boolean toBeStoped;

	public SearchThread(SearchAlgorithm search) {
		this.search = search;
		this.toBeStoped = false;
		this.setDaemon(true);
	}

	@Override
	public void run() {
		super.run();
		try {
			search.search();
		} catch (RuntimeException e) {
			System.out.print("savely stoped");
		}
		return;
	}

	@Override
	public void interrupt() {
		super.interrupt(); // <-- awake from sleep
		System.out.println("interrupted");
		this.toBeStoped = true;
		// continues thread to next pause then cleans it up
		Debugger.resume();

	}
}
