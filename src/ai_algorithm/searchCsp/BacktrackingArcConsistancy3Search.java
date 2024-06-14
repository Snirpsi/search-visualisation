package ai_algorithm.searchCsp;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.CspProblem;
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
        frontier.add(start);
        Debugger.pause();
        while(!frontier.isEmpty()) {
            Path result = backtrack(start, frontier);
            if (result != null) {
                return result;
            }
        }
        Debugger.pause("No Sulution found");
        return null;
    }

    private Path backtrack(SearchNode s, Frontier frontier) {
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
                    State successor = this.problem.getSuccessor(s.getState(), var); // Hier wird irgendwie Safely Stopped und nicht weiter gemacht
//                    SearchNode child = new SearchNode(s, successor, 0, null);
                    SearchNode child = new SearchNode(null, successor, 0, null);
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
        Boolean isInAssignment = false;
        if (assignments.size() < this.problem.getVariables().size()) {
            List<String> domain = ((MapColoringState) this.problem.getInitialState()).getDomain(var);
            for (String value : domain) {
                if ((!assignments.containsValue(value)) && !isInAssignment){
                    isInAssignment = true;
                    resultDomain.add(value);
                }
            }
        }
        return resultDomain;
    }

    public boolean ArcConsistency3(List<Pair<String, String>> contraints,
                                   Map<String, List<String>> domain, Map<String, String> assignments) {
        while (!contraints.isEmpty()) {
            Pair<String, String> arc = contraints.remove(0);
            if (revise(arc.getFirst(), arc.getSecond(), domain, assignments, arc)) {
                if (domain.get(arc.getFirst()).isEmpty()) {
                    System.out.println("No Solution");
                    return false;
                }
                List<String> neighbors = problem.getNeighbors(arc.getFirst());
                for (String xk : neighbors) {
                    contraints.add(new Pair<>(xk, arc.getFirst()));
                }
            }
        }
        return true;
    }

    private boolean revise(String first, String second, Map<String,
            List<String>> domain, Map<String, String> assignment, Pair<String, String> arc) {
        List<String> currDomain = domain.get(first);
        List<String> newValues = new ArrayList<>(currDomain.size());
        Map<String, String> newAssignment = new HashMap<>(assignment);
        for (String vi : currDomain) {
            newAssignment.put(first, vi);
            for (String vj : domain.get(second)) {
                newAssignment.put(second, vj);
                if (isSatisfiedWith(newAssignment, arc)) {
                    newValues.add(vi);
                    break;
                }
            }
        }
        if (newValues.size() < currDomain.size()) {
            domain.put(first, newValues);
            return true;
        }
        return false;
    }

    public boolean isSatisfiedWith(Map<String, String> assignment, Pair<String, String> arc) {
        String val1 = assignment.get(arc.getFirst());
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
