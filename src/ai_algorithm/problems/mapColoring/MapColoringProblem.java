package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;

import java.util.*;

/**
 *
 *
 * @author Alexander
 */

public class MapColoringProblem extends Problem {

    static List<String> variables;
    static List<List<String>> constraints;
    static List<List<String>> domain;
    static Map<String, String> assignments;
    static List<List<String>> arcs;

    public MapColoringProblem() {
        super();
        // TODO: Add Randomization for the different variables of australia
//        this.variables = Arrays.asList("WA", "NT", "SA", "Q", "NSW", "V", "T");
//        this.variables = Arrays.asList("NT", "SA", "Q", "NSW", "V", "T", "WA");
        this.variables = Arrays.asList("SA", "Q", "NSW", "V", "T", "WA", "NT");
        this.constraints = Arrays.asList(
                Arrays.asList("NT", "SA"),
                Arrays.asList("WA", "SA", "Q"),
                Arrays.asList("WA", "NT", "Q"),
                Arrays.asList("SA", "NT", "NSW"),
                Arrays.asList("SA", "Q", "V"),
                Arrays.asList("NSW", "SA"),
                Arrays.asList("T"),
                Collections.emptyList()
        );
        this.domain = Arrays.asList(
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue")),
                new ArrayList<>(Arrays.asList("red", "green", "blue"))
        );
        this.assignments = new HashMap<>();
        this.arcs = new ArrayList<>();
    }

    public void runAC3() {
        fillQueue(); // Fill the queue with the initial arcs
        while (!arcs.isEmpty()) { // While the queue is not empty
            List<String> arcVars = arcs.remove(0); // Remove the first arc from the queue
            if (revise(arcVars.get(0), arcVars.get(1))) { // Revise the domain of the arc
                int dIIndex = variables.indexOf(arcVars.get(0)); // Get the index of the variable in the domain
                if (domain.get(dIIndex).isEmpty()) { // If the domain is empty
                    System.out.println("No Solution");
                    return;
                }
                List<String> neighbors = new ArrayList<>(constraints.get(dIIndex)); // Get the neighbors of the variable
                neighbors.remove(arcVars.get(1)); // Remove the second variable from the neighbors
                for (String xk : neighbors) { // For each neighbor
                    arcs.add(Arrays.asList(xk, arcVars.get(0))); // Add the neighbor and the first variable to the queue
                }
            }
        }
    }

    private boolean revise(String Xi, String Xj) {
        boolean revised = false; // Initialize the revised flag
        int Iindex = variables.indexOf(Xi); // Get the index of the first variable
        for (String x : new ArrayList<>(domain.get(Iindex))) {
            if (!constraints.get(Iindex).contains(Xj)) {
                break;
            }
            if (!assignments.containsKey(Xj)) {
                continue;
            }
            if (assignments.get(Xj).equals(x)) {
                domain.get(Iindex).remove(x); // Remove the value from the domain
                revised = true;
            }
        }
        if (!revised && !domain.get(Iindex).isEmpty()) { // If the domain was not revised
            assignments.put(Xi, domain.get(Iindex).get(0)); // Assign the first value from the domain
        }
        return revised;
    }

    private void fillQueue() {
        for (String var : variables) { // For each variable
            int varIndex = variables.indexOf(var); // Get the index of the variable
            for (String arc : constraints.get(varIndex)) { // For each constraint of the variable
                arcs.add(Arrays.asList(var, arc)); // Add the variable and the arc to the queue
            }
        }
    }

    public List<String> getVariables() {
        return variables;
    }

    public List<List<String>> getConstraints() {
        return constraints;
    }

    public List<List<String>> getDomain() {
        return domain;
    }

    public Map<String, String> getAssignments() {
        return assignments;
    }

    public List<List<String>> getArcs() {
        return arcs;
    }

    @Override
    public State getInitialState() {
        return new MapColoringState(this, assignments);
    }

    @Override
    public boolean isGoalState(State state) {
        return false;
    }

    @Override
    public List<String> getActions(State state) {
        return null;
    }

    @Override
    public State getSuccessor(State state, String action) {
        return null;
    }

    @Override
    public double getCost(State state, String action, State succ) {
        return 0;
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

