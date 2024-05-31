package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import javafx.scene.shape.Circle;

import java.util.*;

/**
 * The MapColoring problem is a problem where a map must be colored in such a way
 * that adjacent regions have different colors. The map consists of various regions
 * (variables) that can be assigned specific colors (domains). There are constraints
 * that dictate that neighboring regions cannot share the same color.
 *
 * The agents must assign colors to the regions in a way that all constraints are satisfied.
 * There are various classes that make the problem available.
 *
 * These are: {@link MapColoringProblem}, {@link MapColoringState}, and {@link MapColoringPath}.
 * In these classes, only the functions relevant to artificial intelligence have been implemented.
 * Each of these classes also has its own visitor functions, which equip them with components
 * for visualization.
 *
 * @author Alexander
 */
public class MapColoringProblem extends Problem {

    public final int GAMESIZE; // Number of all variables

    List<String> variables; // Variables of the problem
    Map<String, List<String>> neighbors; // Neighbors for each variable
    Map<String, List<String>> domain; // Domain of the problem
    Map<String, String> assignments; // Assignments of the problem

    List<List<String>> variableConstraintsEdges = new ArrayList<>(); // List of all variable constraints Edges

//    List<List<String>> variableConstraintsDomain = new ArrayList<>();

    Map<String, Circle> variableToCircleMap = new HashMap<>(); // Assignment of all variables to their respective circles

    List<Pair<String, String>> contraints; // List of all constraints / arcs

    String start;

    String goal;

    /**
     * Initializes the map coloring problem with the variables, constraints, domain, assignements and arcs.
     * Initializes the fillQueue method.
     * Initializes the start variable.
     */
    public MapColoringProblem() {
        super();
        variables = Arrays.asList("WA", "NT", "SA", "Q", "NSW", "V", "T");
        GAMESIZE = variables.size();
        neighbors = Map.of(
                "WA", List.of("NT", "SA"), // Constraint from WA
                "NT", List.of("WA", "SA", "Q"), // Constraint from NT
                "SA", List.of("WA", "NT", "Q", "NSW", "V"), // Constraint from SA
                "Q", List.of("SA", "NT", "NSW"), // Constraint from Q
                "NSW", List.of("SA", "Q", "V"), // Constraint from NSW
                "V", List.of("NSW", "SA"), // Constraint from V
                "T", List.of() // Constraint from T
        );

        // Assignment of all variables to their respective circles
        domain = new HashMap<>();
        for (String var : variables) {
            domain.put(var, Arrays.asList("Red", "Green", "Blue"));
        }

        contraints = new ArrayList<>();
        fillQueue();

//        start = bundesstaatenListe.get(0).getVariable();
//        start = variables.get(0);
    }

//    public void runAC3() {
////        fillQueue(); // Fill the queue with the initial arcs
////        while (!arcs.isEmpty()) { // While the queue is not empty
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
////        }
//        setAssignments(assignments);
//    }
//
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

    /**
     * Fills the queue with the edges and assigns each variable an edge between two nodes.
     */
    public void fillQueue() {
        // Create constraint list out of neighbors
        for( String var : variables) {
            List<String> constraint = neighbors.get(var);
            for (String other : constraint) {
                contraints.add(new Pair<>(var, other));
            }
        }
//        System.out.println("Arcs: " + arcs);
        // Not necessary anymore
//        for (int i = 0; i < domain.size(); i++) {
//            variableConstraintsDomain.add(Arrays.asList(variables.get(i), domain.get(i).toString()));
//        }
    }

    /**
     * Returns the list of all variables.
     *
     * @return list of all variables
     */
    public List<String> getVariables() {
        return variables;
    }

//    public String getVariable(SearchNode node) {
//        // Such mir die Variable aus der Variablenliste heraus die am Node hängt
//        return variables.get());
//
//    }

    /**
     * Returns the list of all domains.
     *
     * @return list of all domains
     */
    public Map<String, List<String>> getDomain() {
        return domain;
    }

    /**
     * Returns the domain of a specific variable.
     *
     * @param variable variable
     * @return domain of the variable
     */
    public List<String> getDomainOfVariable(String variable) {
        if(!variables.contains(variable)) {
            return null;
        }else {
            int index = variables.indexOf(variable);
            return domain.get(index);
        }
    }

//    public void setAssignments(Map<String, String> assignment) {
//        this.assignments = assignment;
//    }

    /**
     * Returns the assignments.
     *
     * @return assignments
     */
    public Map<String, String> getAssignments() {
        return assignments;
    }

    /**
     * Returns the arcs.
     *
     * @return arcs
     */
    public List<Pair<String, String>> getContraints() {
        return contraints;
    }

    /**
     * Sets the variable to circle map.
     * Contains information about the circles assigned to a specific variable
     *
     * @param variableToCircleMap
     */
    public void setVariableToCircleMap(Map<String, Circle> variableToCircleMap) {
        this.variableToCircleMap = variableToCircleMap;
    }

    /**
     * Returns the variable to circle map.
     *
     * @return variable to circle map
     */
    public Map<String, Circle> getVariableToCircleMap() {
        return variableToCircleMap;
    }

//    public List<List<String>> getVariableConstraintsDomain() {
//        return variableConstraintsDomain;
//    }

    /**
     * Returns the start variable.
     *
     * @return start variable
     */
    @Override
    public State getInitialState() {
        Map<String, String> initialAssignments = new HashMap<>();
        Map<String, List<String>> initialDomain = new HashMap<>();

        for (String variable : variables) {
            initialDomain.put(variable, new ArrayList<>(domain.get(variable)));
        }
        return new MapColoringState(this, initialDomain, initialAssignments);
    }

    /**
     * Returns the goal variable.
     *
     * @return goal variable
     */
    @Override
    public State getGoalState() {
        return null;
    }

    /**
     * Checks whether a given state is a target state.
     *
     * @param state state to be tested
     * @return true if state is goal state else false
     */
    @Override
    public boolean isGoalState(State state) {
        MapColoringState stateM = (MapColoringState) state;

        for (String variable : variables) {
            if (!stateM.getAssignments().containsKey(variable)) {
                return false;
            }
        }
        for (Pair<String, String> constraint : contraints ) {
            String var1 = constraint.getFirst();
            String var2 = constraint.getSecond();
            String val1 = stateM.getAssignments().get(var1);
            String val2 = stateM.getAssignments().get(var2);
            if (val1.equals(val2)){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the actions of a given state.
     *
     * @param state state
     * @return List l of actions of the state
     */
    @Override
    public List<String> getActions(State state) {

        MapColoringState stateM = (MapColoringState) state;
        List<String> actions = new ArrayList<>();

        for (String variable : variables) {
            if (stateM.getAssignments().containsKey(variable)) {
                continue;
            }
            List<String> varDomain = stateM.getDomain(variable);
            for (String color : varDomain) {
                actions.add(variable + "=" + color);
            }
        }
        return actions;
    }

    /**
     * Returns the successor of a given state and action.
     *
     * @param state state
     * @param action action
     * @return successor of the state
     */
    @Override
    public State getSuccessor(State state, String action) {
        MapColoringState stateM = (MapColoringState) state;
        Map<String, String> newAssignments = new HashMap<>(stateM.getAssignments());

        String[] parts = action.split("=");
        String variable = parts[0];
        String value = parts[1];

        if (!stateM.getDomain(variable).contains(value)) {
            return null;
        }
        newAssignments.put(variable, value);

        Map<String, List<String>> newDomain = new HashMap<>();
        for (String var : variables) {
            if(variable.equals(var)) {
                newDomain.put(var, Arrays.asList(value));
            } else {
                newDomain.put(var, new ArrayList<>(stateM.getDomain(var)));
            }
        }
        return new MapColoringState(this, newDomain, newAssignments);
    }

    /**
     * Returns the cost of a given state, action and successor.
     *
     * @param state state
     * @param action action
     * @param succ successor
     * @return cost of the state, action and successor
     */
    @Override
    public double getCost(State state, String action, State succ) {
        return 1;
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

