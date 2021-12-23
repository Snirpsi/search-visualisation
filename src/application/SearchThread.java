package application;

import ai_algorithm.search.SearchAlgorithm;

public class SearchThread extends Thread {

	SearchAlgorithm search = null;
	

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
