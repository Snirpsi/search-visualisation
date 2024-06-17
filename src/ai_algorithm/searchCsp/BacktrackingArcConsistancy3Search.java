package ai_algorithm.searchCsp;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.CspProblem;
import ai_algorithm.problems.CspState;
import ai_algorithm.problems.State;
import ai_algorithm.problems.mapColoring.MapColoringState;
import ai_algorithm.problems.mapColoring.Pair;
import application.debugger.Debugger;

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
//        frontier.add(start);
        Debugger.pause();
//        while(!frontier.isEmpty()) {
            Path result = backtrackNew(start, frontier);
            if (result != null) {
                return result;
            }
//        }
        Debugger.pause("No Solution found");
        return null;
    }

    private Path backtrack(SearchNode s, Frontier frontier) {
        if (this.problem.isGoalState(s.getState())) {
            return s.getPath();
        }
        // Innerhalb dieser backtracking Funktion muss eine Variable aus der Frontier entfertn werden um einen neue Node zu bekommen
        Map<String, String> assignments = ((MapColoringState) s.getState()).getAssignments();
        String var = selectUnassignedVariable(assignments);
        if (!var.isEmpty()) {
            for (String value : orderDomainValues(var, assignments)) {
                assignments.put(var, value);
                boolean inference = ArcConsistency3(this.problem.getContraints(), this.problem.getDomain(), assignments);
                if (inference) {
                    // Übergabe eines Strings an die Funktion "getSuccessor" sie erwartet jedoch eine Action die gesplittet werden muss
                    // var bei getSuccessor müsste eine Action sein // TODO: Überprüfen + Anpassen
                    State successor = this.problem.getSuccessor(s.getState(), var + "=" + value); // Hier wird irgendwie Safely Stopped und nicht weiter gemacht
                    SearchNode child = new SearchNode(s, successor, 0, null);
//                    SearchNode child = new SearchNode(null, successor, 0, null);
                    frontier.add(child);
                    Debugger.pause();
                    if (problem.isGoalState(successor)) {
                        Debugger.pause("Finished");
                        return child.getPath();
                    }
                    return backtrack(child, frontier);
                } else {
                    assignments.remove(var);
                }
            }
        }
        Debugger.pause("No Sulution found");
        return null; // !!! Noch Falsch !!!
    }

    private Path backtrackNew(SearchNode searchNode, Frontier frontier) {
        if (this.problem.isGoalState(searchNode.getState())) {
            Debugger.pause("Finished");
            return searchNode.getPath();
        }
        CspState cspState = (CspState) searchNode.getState();
        Map<String, String> assignments = cspState.getAssignments();
        boolean inference = ArcConsistency3(this.problem.getContraints(), cspState.getDomains(), assignments);
        if( !inference ) {
            return null;
        }

        // For every "action"
        String var = selectUnassignedVariable(assignments);
        for (String value : orderDomainValues(var, assignments)) {
            String action = var + "=" + value;
            State succState = this.problem.getSuccessor(cspState, action); // Hier wird irgendwie Safely Stopped und nicht weiter gemacht
            SearchNode child = new SearchNode(
                    searchNode,
                    succState,
                    searchNode.getPathCost() + this.problem.getCost(cspState, action, succState),
                    action);
            searchNode.getChildren().add(child);
            Debugger.pause();
            Path result = backtrackNew(child, frontier);
            // If a solution was found, return it
            if( result != null ) {
                return result;
            }
            // Otherwise, try next value
        }
        Debugger.pause("No Sulution found");
        return null; // !!! Noch Falsch !!!
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

    private List<String> orderDomainValues(String var, Map<String, String> assignments) {
        List<String> resultDomain = new ArrayList<>();
//        Boolean isInAssignment = false;
        if (assignments.size() < this.problem.getVariables().size()) {
            List<String> domain = ((MapColoringState) this.problem.getInitialState()).getDomain(var);
            for (String value : domain) {
//                if ((!assignments.containsValue(value)) && !isInAssignment){
//                    isInAssignment = true;
                    resultDomain.add(value);
//                }
            }
        }
        return resultDomain;
    }

    public boolean ArcConsistency3(List<Pair<String, String>> contraints,
                                   Map<String, List<String>> domains, Map<String, String> assignments) {
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
