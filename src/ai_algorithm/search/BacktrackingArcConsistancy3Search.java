package ai_algorithm.search;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import ai_algorithm.problems.mapColoring.MapColoringProblem;
import ai_algorithm.problems.mapColoring.MapColoringState;
import ai_algorithm.problems.mapColoring.Pair;
import application.debugger.Debugger;

import java.util.*;

public class BacktrackingArcConsistancy3Search extends SearchAlgorithm{

    MapColoringProblem mcp;

    public BacktrackingArcConsistancy3Search() {
        super();
    }

    public BacktrackingArcConsistancy3Search(MapColoringProblem problem) { // KA OB NÖTIG
        super(problem);
    }

    @Override
    public Path search() {
        SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
        Frontier frontier = new Frontier();

        MapColoringProblem csp = new MapColoringProblem();

        if (this.problem instanceof MapColoringProblem) {
            csp = (MapColoringProblem) this.problem;
            System.out.println("Constraints: " + csp.getContraints());
        }







        Debugger.pause();
        if (this.problem.isGoalState(start.getState())) {
            return start.getPath();
        }
        frontier.add(start);
        Debugger.pause();
        while (!frontier.isEmpty()) {
            SearchNode node = frontier.removeFirst();
            Debugger.pause();
            System.out.println(node);
            if (problem.isGoalState(node.getState())) {
                return node.getPath();
            }
            for (SearchNode child : node.expand()) {
                State state = child.getState();
                if (problem.isGoalState(state)) {
                    Debugger.pause("Finished");
                    return child.getPath();
                }
                if (!contains2(node, state)) {
                    frontier.add(child);
                }
            }
        }

//        return backtrack(start);
        return null;
    }

    private Path backtrack(SearchNode node){

        return null;
    }



    public boolean contains2(SearchNode sn, State state) {
        return sn.getPath().getVisitedStates().contains(state);
    }

    private State selectUnassignedVariable(SearchNode node) {

        return null;
    }

    public void reduseDomains(List<Pair<String, String>> contraints,
                              Map<String, List<String>> domain, Map<String, String> assignments) {
        while (!contraints.isEmpty()) {
            Pair<String, String> arc = contraints.remove(0);
            if (revise(arc.getFirst(), arc.getSecond(), domain, assignments)) {
                int dIIndex = domain.get(arc.getFirst()).indexOf(assignments.get(arc.getFirst()));
                if (domain.get(arc.getFirst()).isEmpty()) {
                    System.out.println("No Solution");
                    return;
                }
                List<String> neighbors = new ArrayList<>((Collection) contraints.get(dIIndex));
                neighbors.remove(arc.getSecond());
                for (String xk : neighbors) {
                    contraints.add(new Pair<>(xk, arc.getFirst()));
                }
            }
        }
    }

    private boolean revise(String first, String second, Map<String,
            List<String>> domain, Map<String, String> assignments) {
        boolean revised = false;
        List<String> currDomain = domain.get(first);
        List<String> newValues = new ArrayList<>(currDomain.size());
        Map<String, String> assignment = new HashMap<>();
        for (String vi : currDomain) {
            assignment.put(first, vi);
            for (String vj : domain.get(second)) {
                assignment.put(second, vj);
                if (assignments.containsKey(first) && assignments.containsKey(second)) {
                    if (assignments.get(first).equals(vi) && assignments.get(second).equals(vj)) {
                        newValues.add(vi);
                        break;
                    }
                }
            }
        }
        if (newValues.size() < currDomain.size()) {
            domain.put(first, newValues);
            revised = true;
        }
        return revised;
    }

    // ############################# \/ Orientierung \/ #############################
//    public void runAC3() {
//        fillQueue(); // Fill the queue with the initial arcs
//        while (!arcs.isEmpty()) { // While the queue is not empty
//            List<String> arcVars = arcs.remove(0); // Remove the first arc from the queue
//            if (revise(arcVars.get(0), arcVars.get(1))) { // Revise the domain of the arc
//                int dIIndex = variables.indexOf(arcVars.get(0)); // Get the index of the variable in the domain
//                if (domain.get(dIIndex).isEmpty()) { // If the domain is empty
//                    System.out.println("No Solution");
//                    return;
//                }
//                List<String> neighbors = new ArrayList<>(constraints.get(dIIndex)); // Get the neighbors of the variable
//                neighbors.remove(arcVars.get(1)); // Remove the second variable from the neighbors
//                // TODO: Quellcode überarbeiten
//                for (String xk : neighbors) { // For each neighbor
//                    arcs.add(Arrays.asList(xk, arcVars.get(0))); // Add the neighbor and the first variable to the queue
//                }
//            }
//        }
//        setAssignments(assignments);
//    }

//    private boolean revise(String Xi, String Xj) {
//        boolean revised = false; // Initialize the revised flag
//        int Iindex = variables.indexOf(Xi); // Get the index of the first variable
//        for (String x : new ArrayList<>(domain.get(Iindex))) {
//            if (!constraints.get(Iindex).contains(Xj)) {
//                break;
//            }
//            if (!assignments.containsKey(Xj)) {
//                continue;
//            }
//            if (assignments.get(Xj).equals(x)) {
//                domain.get(Iindex).remove(x); // Remove the value from the domain
//                revised = true;
//            }
//        }
//        if (!revised && !domain.get(Iindex).isEmpty()) { // If the domain was not revised
//            assignments.put(Xi, domain.get(Iindex).get(0)); // Assign the first value from the domain
//        }
//        return revised;
//    }

}

/*
 * Copyright (c) 2024 Alexander Ultsch
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
