package application;

import ai_algorithm.SearchAlgorithm;

public class SearchThread extends Thread {

	SearchAlgorithm search;

	public SearchThread(SearchAlgorithm search) {
		this.search = search;
	}

	@Override
	public void run() {
		super.run();
		
		search.search();
		return;
	}
}
