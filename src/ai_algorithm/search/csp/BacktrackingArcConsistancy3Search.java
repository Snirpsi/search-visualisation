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

/**
 * Backtracking Suche nach Russell and Norvig
 *
 * @author Alexander
 */
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

    /**
     * Expand the search node with actions for the given variable
     * Makes the inference check using the Arc Consistancy 3 algorithm by calling the function arcConsistency3
     * Selection of a variable that has not yet been assigned in the assignments list by calling up the function selectUnassignedVariable
     * Call the function orderDomainValues to get a list of domains of this variable
     * For each value in the domain, a new search node is created and added to the frontier
     * Recursive call of backtrack with the new search node
     *
     * @param searchNode
     * @param frontier
     * @return list of search nodes
     */
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

    /**
     * Selects the first unassigned variable from the list of variables
     *
     * @param assignments
     * @return result of variable name as string
     */
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

    /**
     * Orders the domain values of the variable
     * If allowOnlyValidValues is true, only the valid values are returned
     * Otherwise, all values are returned
     *
     * @param var
     * @param state
     * @param allowOnlyValidValues
     * @return the ordered domain values
     */
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

    /**
     * Arc Consistency 3 algorithm
     * Calls the revise function to decrease the domain of variable Xi
     *
     * @param contraints
     * @param domains
     * @return true if the problem is arc consistent
     */
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

    /**
     * Revise the domain of the variable Xi
     *
     * @param arc
     * @param domain
     * @return true if the domain was revised
     */
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

    /**
     * Check if the assignment is satisfied
     *
     * @param assignment
     * @param arc
     * @return true if the assignment is satisfied
     */
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
 * Copyright (c) 2024 Alexander Ultsch, Florian Mittag
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
