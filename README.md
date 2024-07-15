# Search-Visualization

Search-Visualization is a framework that can be used to implement and visualize AI algorithms. The Search-Visualization uses two perspectives to represent the algorithmic behavior. On the left hand side a search tree is drawn that shows which search node was expanded by the algorithm. On the right hand side the problem with a representation of the state is shown.
AI algorithms and search threads are independent of each other. The visualization is done via an entity component system.


## Features:

* Provides Classes for AI and search algorithms
* Representation and implementation of two games for AI to work with
* Construction and representation of search trees according to the algorithm data structure
* GUI with control elements for visualization control 
* Administrators can view all pictures on one page with the user inserted data
* Pausing and resuming algorithms
* Representation and implementation of two MapColoringProblems (Australia and General)
* Abstracted classes for CSP problems and CSP search algorithms


## Dependencies:

* Java 17
* JavaFX 21.0.2


## Installation:

* Install Java17
	* https://www.java.com/de/download/manual.jsp
* Install JDK 
	* https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
* Install an IDE (e.g. IntelliJ Community Edition)
	* https://www.jetbrains.com/idea/download/?section=windows
* Install JavaFX libary into IDE
	* https://www.jetbrains.com/help/idea/javafx.html#create-project
	* https://openjfx.io/openjfx-docs/
* Import src folder into your IDE
* Run the `Main.java` class in the `application` package

You should see the following:

![empty search-visualization winow](https://1drv.ms/i/s!AtIGvMOY6z5JjoQAo3szOKchZX04pw?e=sx5qOT?raw=true)
*Picture 1:  Empty window*

Select a problem and a search-algorithm and press the START-Button. Press the STEP-Button to proceed the visualization.
![proceeding search-visualization winow](https://snirps.ddns.net/public/search-visualization/proceed.PNG)
*Picture 2: Depth first search*


## Implement your own AI-Algorithm:

Navigate to the `ai_algorithm` package inside go to `search` create a new class with your own algorithm name e.g. `DepthFirstSearchExplored `. 

![ai algorithm package](https://snirps.ddns.net/public/search-visualization/ai_algorithm-package.png)
*Picture 3: Package organization*

```java
package ai_algorithm.search;

import ai_algorithm.ExploredSet;
import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.State;
import application.debugger.Debugger;

public class DepthFirstSearchExplored extends SearchAlgorithm {
	@Override
	public Path search() {
		SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
		Frontier frontier = new Frontier();
		ExploredSet explored = new ExploredSet();
		explored.add(start);
		Debugger.pause();
		if (this.problem.isGoalState(start.getState())) {
			return start.getPath();
		}
		frontier.add(start);
		Debugger.pause();
		while (!frontier.isEmpty()) {
			SearchNode node = frontier.removeLast();
			Debugger.pause();
			System.out.println(node);
			for (SearchNode child : node.expand()) {
				State state = child.getState();
				if (problem.isGoalState(state)) {
					Debugger.pause("Finished");
					return child.getPath();
				}
				if (!explored.contains(state)) {
					Debugger.pause();
					explored.add(child);
					frontier.add(child);
				}
			}
		}
		return null;
	}
}
```
As a last step you have to announce your algorithm to the framework by adding the algorithms name to the algorithm `SearchAndProblemRegister`.
```java
public class SearchAndProblemRegister {
...
	public static String[] searchAlgorithms = { //
			DepthFirstSearch.class.getName(), //
			DepthFirstSearchExplored.class.getName(), //
			RecursiveDepthSearch.class.getName(), //
			BreadthFirstSearch.class.getName(), // <<<your new algorithm
			BidirectionalBreadthFirstSearch.class.getName(), //
			ManualSearch.class.getName()//
	};
...
}
```
Start the framework and select your new algorithm.
The selection sequence for the framework is shown in picture 2.


## How does it work:

Like many Game Engines the Search-Visualization framework is based on an **Entity-Component-Sytem**. Therefore it uses **Game-Objects** to represent all Objects that are part of a search algorithm. The components can be give to a Game-Object to add functionality.
![EntityComponentSystem](https://snirps.ddns.net/public/search-visualization/EntityComponentSystem.svg)
*Picture 3: Components are assigned to Game-Objects*

The visualization is seperated into two threads the **Search-Thread** and the **Visualization-Thread**.  The connecting element between them are **visitors** that are managed by the **Game-Object-Registry** to be applied on the Game-Objects.
![FrameworkeArchitecture](https://snirps.ddns.net/public/search-visualization/GameObjekt-Lebenszyklus_klein.svg)
*Picture 4: Broad architecture of the framework*

For more information on this topic read the full documentation: [full java doc](https://snirps.ddns.net/public/search-visualization/JavaDoc/).


## Results

Here are some pictures generated by the Search-Visualization:

### Depth First Search
![DepthFirstSearch](https://snirps.ddns.net/public/search-visualization/Algorithmen/Tiefensuche.PNG)

### Depth First Search with Explored Set
![DepthFirstSearchExploredSet](https://snirps.ddns.net/public/search-visualization/Algorithmen/Tiefensuche%20mit%20ExploredSet.PNG)

### Recursive Depth Search
![FrameworkeArchitecture](https://snirps.ddns.net/public/search-visualization/Algorithmen/Rekursive%20Tiefensuche.PNG)

### Breadth First Search
![FrameworkeArchitecture](https://snirps.ddns.net/public/search-visualization/Algorithmen/Breitensuche.PNG)

### Bidirectional Breadth First Search
![FrameworkeArchitecture](https://snirps.ddns.net/public/search-visualization/Algorithmen/Bidirektional.PNG)

### Manual Search
![FrameworkeArchitecture](https://snirps.ddns.net/public/search-visualization/Algorithmen/Manuelle-suche.PNG)


## Implement your own CSP Search Algorithm

Navigate to the `ai_algorithm` package inside go to `search\csp` create a new class with your own csp algorithm name e.g. `BacktrackingArcConsistancy3Search`.

```java
package ai_algorithm.search.csp;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.CspProblem;
import ai_algorithm.problems.CspState;
import ai_algorithm.problems.mapColoring.Pair;
import application.debugger.Debugger;
import ecs.GameObjectRegistry;

import java.util.*;

public class BacktrackingArcConsistancy3Search extends CspSearchAlgorithm {

    public BacktrackingArcConsistancy3Search() {
        super();
    }

    public BacktrackingArcConsistancy3Search(CspProblem problem) {
        super(problem);
    }

    @Override
    public Path search() {
        SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
        Frontier frontier = new Frontier();
        if (this.problem.isGoalState(start.getState())) {
            return start.getPath();
        }

        // Frontier is only used for coloring nodes correctly
        frontier.add(start);
        Debugger.pause();

        Path result = backtrack(start, frontier);
        if (result != null) {
            return result;
        }

        Debugger.pause("No Solution found");
        return null;
    }

    private Path backtrack(SearchNode searchNode, Frontier frontier) {
        frontier.remove(searchNode);
        if (this.problem.isGoalState(searchNode.getState())) {
            GameObjectRegistry.registerForStateChange(searchNode);
            Debugger.pause("Finished");
            return searchNode.getPath();
        }

        CspState cspState = (CspState) searchNode.getState();
        Map<String, String> assignments = cspState.getAssignments();
        boolean inference = arcConsistency3(this.problem.getContraints(), cspState.getDomains());
        if( !inference ) {
            GameObjectRegistry.registerForStateChange(searchNode);
            return null;
        }

        String var = selectUnassignedVariable(assignments);
        List<String> values = orderDomainValues(var, cspState, false);
        // For every "action" of Variable var
        for (SearchNode child : expand(searchNode, frontier, var, values)) {
            Path result = backtrack(child, frontier);
            // If a solution was found, return it
            if( result != null ) {
                GameObjectRegistry.registerForStateChange(searchNode);
                return result;
            }
            // Otherwise, try next value
        }
        GameObjectRegistry.registerForStateChange(searchNode);
        Debugger.pause("No Solution found");
        return null;
    }

    private String selectUnassignedVariable(Map<String, String> assignments) {
        String result = "";
        Boolean inAssignments = false;
        if (assignments.size() < this.problem.getVariables().size()) {
            for (String variable : this.problem.getVariables()) {
                if ((!assignments.containsKey(variable)) && !inAssignments){
                    result = variable;
                    inAssignments = true;
                }
            }
        }
        return result;
    }

    private List<String> orderDomainValues(String var, CspState state, boolean allowOnlyValidValues) {
        List<String> resultDomain = new ArrayList<>();

        if( allowOnlyValidValues ) {
            resultDomain.addAll(state.getDomain(var));
        } else {
            resultDomain.addAll(this.problem.getDomain().get(var));
        }
        Collections.shuffle(resultDomain);
        return resultDomain;
    }

    public boolean arcConsistency3(List<Pair<String, String>> contraints,
                                   Map<String, List<String>> domains) {
        List<Pair<String, String>> constraintCopy = new ArrayList<>(contraints);
        while (!constraintCopy.isEmpty()) {
            Pair<String, String> arc = constraintCopy.remove(0);
            List<String> neighbors = problem.getNeighbors(arc.getFirst());
            if (neighbors != null && revise(arc, domains)) {
                if (domains.get(arc.getFirst()).isEmpty()) {
                    System.out.println("No Solution");
                    return false;
                }
                for (String xk : neighbors) {
                    constraintCopy.add(new Pair<>(xk, arc.getFirst()));
                }
            }
        }
        return true;
    }

    private boolean revise(Pair<String, String> arc,
                           Map<String, List<String>> domain) {
        boolean revise = false;
        String xi = arc.getFirst();
        String xj = arc.getSecond();

        List<String> domain_i = domain.get(xi);
        List<String> domain_j = domain.get(xj);

        List<String> invalidValues = new ArrayList<>();
        for (String vi : domain_i) {
            boolean foundValidValue = false;
            for (String vj : domain_j) {
                Map<String, String> newAssignment = Map.of(xi, vi, xj, vj);
                // If the assignment is satisfied, this value for Xi is fine
                if (isSatisfiedWith(newAssignment, arc)) {
                    foundValidValue = true;
                    break;
                }
            }
            if (!foundValidValue) {
                invalidValues.add(vi);
                revise = true;
            }
        }
        for (String invalidValue : invalidValues) {
            domain_i.remove(invalidValue);
        }
        return revise;
    }

    public boolean isSatisfiedWith(Map<String, String> assignment, Pair<String, String> arc) {
        String val1 = assignment.get(arc.getFirst()); // get the value of the first variable
        String val2 = assignment.get(arc.getSecond());
        if (val1.equals(val2)) {
            return false;
        }
        return true;
    }
}
```

The abstract class `CspSearchAlgorithm` is implemented by the csp algorithms and serves as the superclass

```java
package ai_algorithm.search.csp;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.SearchNodeMetadataObject;
import ai_algorithm.problems.CspProblem;
import ai_algorithm.problems.CspState;
import ai_algorithm.problems.State;
import ai_algorithm.search.SearchAlgorithm;
import application.debugger.Debugger;
import ecs.GameObjectRegistry;

import java.util.List;

public abstract class CspSearchAlgorithm extends SearchAlgorithm {

	CspProblem problem;

	public CspSearchAlgorithm() {

	}

	public CspSearchAlgorithm(CspProblem problem) {
		this.problem = problem;
	}

	public void setProblem(CspProblem problem) {
		this.problem = problem;
	}

	public abstract Path search();

	public List<SearchNode> expand(SearchNode searchNode, Frontier frontier, String variable, List<String> values) {
		// Start visualization
		SearchNodeMetadataObject.setExpandingSearchnode(searchNode);
		Debugger.pause("Expanding: " + this);
		// End visualization

		if (searchNode.getChildren() != null && !searchNode.getChildren().isEmpty() ) {
			return searchNode.getChildren(); // <-- only expand children if they don't already exist
		}

		CspState state = (CspState) searchNode.getState();
		CspProblem prob = (CspProblem)state.getProblem();

		for (String value : values) {
			String action = variable + "=" + value;
			State succState = prob.getSuccessor(state, action);
			// Create new SearchNode
			SearchNode succ = new SearchNode(searchNode, succState,
					searchNode.getPathCost() + prob.getCost(state, action, succState), action);
			searchNode.getChildren().add(succ);
			frontier.add(succ);
			Debugger.pause("EXPANSION: " + action);
		}

		// Start visualization
		SearchNodeMetadataObject.setExpandingSearchnode(null);
		GameObjectRegistry.registerForStateChange(searchNode);
		// End visualization
		return searchNode.getChildren();
	}
}
```

The next step you have to announce your algorithm to the framework by adding the algorithms name to the algorithm `SearchAndProblemRegister`.

```java
...
public static String[] searchAlgorithms = { //
		DepthFirstSearch.class.getName(), //
		DepthFirstSearchExplored.class.getName(), //
		RecursiveDepthSearch.class.getName(), //
		BreadthFirstSearch.class.getName(), //
		BidirectionalBreadthFirstSearch.class.getName(), //
		ManualSearch.class.getName(),//
};

public static String[] cspSearchAlgorithm = {
		BacktrackingArcConsistancy3Search.class.getName()//
};
...
```

## Results of MapColoringProblems

### MapColoringProblem Australia
![MapColoringProblem Australia]()

### MapColoringProblem General
![MapColoringProblem General]()


Start the framework and select your new problem and algorithm.
The selection sequence for the framework is shown in picture 2.


## To-Do's

* [ ] FIX: Sliding Tile Problem Visitors
* [ ] FIX: Overlapping search nodes in some trees 
* [ ] CHANGE: CSS Theme
* [ ] CHANGE: Userinterface
* [ ] ADD: More problems/games
* [ ] ADD: More CSP problems/games
* [ ] ADD: More MapColoringProblems/games
* [ ] ADD: More search algorithms 
* [ ] ADD: More CSP search algorithms

