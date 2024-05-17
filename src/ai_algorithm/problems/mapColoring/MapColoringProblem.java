package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import javafx.scene.shape.Circle;

import javax.net.ssl.SSLContext;
import java.util.*;

/**
 *
 *
 * @author Alexander
 */

public class MapColoringProblem extends Problem {

    public final int GAMESIZE; // Number of all variables

    List<String> variables;
    List<List<String>> constraints;
    List<List<String>> domain;
    Map<String, String> assignments;

    List<Bundesstaaten> bundesstaatenListe;

    List<List<String>> variableConstraintsEdges = new ArrayList<>();

//    List<List<String>> variableConstraintsDomain = new ArrayList<>();

    Map<String, Circle> variableToCircleMap = new HashMap<>();

    List<List<String>> arcs;

    String start;

    String goal;

    public MapColoringProblem() {
        super();

        // TODO: Add Randomization for the different variables of australia
        // "Red", "Green", "Blue"
        Bundesstaaten wa = new Bundesstaaten("WA", Arrays.asList("Red", "Green"), Arrays.asList("NT", "SA"));
        Bundesstaaten nt = new Bundesstaaten("NT", Arrays.asList("Green"), Arrays.asList("WA", "SA", "Q"));
        Bundesstaaten sa = new Bundesstaaten("SA", Arrays.asList("Blue", "Green"), Arrays.asList("WA", "NT", "Q", "NSW", "V"));
        Bundesstaaten q = new Bundesstaaten("Q", Arrays.asList("Green", "Red", "Blue"), Arrays.asList("SA", "NT", "NSW"));
        Bundesstaaten nsw = new Bundesstaaten("NSW", Arrays.asList("Green"), Arrays.asList("SA", "Q", "V"));
        Bundesstaaten v = new Bundesstaaten("V", Arrays.asList("Blue", "Green"), Arrays.asList("NSW", "SA"));
        Bundesstaaten t = new Bundesstaaten("T", Arrays.asList("Red", "Green"), Collections.emptyList());
        bundesstaatenListe = (Arrays.asList(wa, nt, sa, q, nsw, v, t));

        variables = Arrays.asList("WA", "NT", "SA", "Q", "NSW", "V", "T");
        GAMESIZE = variables.size();
        constraints = Arrays.asList(
                Arrays.asList("NT", "SA"), // Constraint from WA
                Arrays.asList("WA", "SA", "Q"), // Constraint from NT
                Arrays.asList("WA", "NT", "Q", "NSW", "V"), // Constraint from SA
                Arrays.asList("SA", "NT", "NSW"), // Constraint from Q
                Arrays.asList("SA", "Q", "V"), // Constraint from NSW
                Arrays.asList("NSW", "SA"), // Constraint from V
                Collections.emptyList() // Constraint from T
        );

        domain = new ArrayList<>();
        for (int i = 0; i < GAMESIZE; i++) {
            domain.add(Arrays.asList("Red", "Green", "Blue"));
        }
        assignments = new HashMap<>();
        arcs = new ArrayList<>();

        fillQueue();

//        start = bundesstaatenListe.get(0).getVariable();
        start = variables.get(0);
    }

    public void runAC3() {
//        fillQueue(); // Fill the queue with the initial arcs
//        while (!arcs.isEmpty()) { // While the queue is not empty
            List<String> arcVars = arcs.remove(0); // Remove the first arc from the queue
            if (revise(arcVars.get(0), arcVars.get(1))) { // Revise the domain of the arc
                int dIIndex = variables.indexOf(arcVars.get(0)); // Get the index of the variable in the domain
                if (domain.get(dIIndex).isEmpty()) { // If the domain is empty
                    System.out.println("No Solution");
                    return;
                }
                List<String> neighbors = new ArrayList<>(constraints.get(dIIndex)); // Get the neighbors of the variable
                neighbors.remove(arcVars.get(1)); // Remove the second variable from the neighbors
                // TODO: Quellcode überarbeiten
                for (String xk : neighbors) { // For each neighbor
                    arcs.add(Arrays.asList(xk, arcVars.get(0))); // Add the neighbor and the first variable to the queue
                }
            }
//        }
        setAssignments(assignments);
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

    public void fillQueue() {
        for (int i = 0; i < constraints.size(); i++) {
            List<String> constraint = constraints.get(i);
            String var = variables.get(i);
            if (constraint.isEmpty()) {
                continue; // Skip empty constraints
            }
            for (String arc : constraint) {
                variableConstraintsEdges.add(Arrays.asList(var, arc));
                arcs.add(Arrays.asList(var, arc));
            }
        }
//        System.out.println("Arcs: " + arcs);

        // Not necessary anymore
//        for (int i = 0; i < domain.size(); i++) {
//            variableConstraintsDomain.add(Arrays.asList(variables.get(i), domain.get(i).toString()));
//        }
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

    public List<String> getDomainOfVariable(String variable) {
        if(!variables.contains(variable)) {
            return null;
        }else {
            int index = variables.indexOf(variable);
            return domain.get(index);
        }
    }

    public void setAssignments(Map<String, String> assignment) {
        this.assignments = assignment;
    }

    public Map<String, String> getAssignments() {
        return assignments;
    }

    public List<List<String>> getArcs() {
        return arcs;
    }

    public List<List<String>> getVariableConstraintsEdges() {
        System.out.println("Variable Constraint with Node [Vaiable, Node]: " + variableConstraintsEdges);
        return this.variableConstraintsEdges;
    }

    public void setVariableToCircleMap(Map<String, Circle> variableToCircleMap) {
        this.variableToCircleMap = variableToCircleMap;
    }

    public Map<String, Circle> getVariableToCircleMap() {
        return variableToCircleMap;
    }

//    public List<List<String>> getVariableConstraintsDomain() {
//        return variableConstraintsDomain;
//    }

    public List<Bundesstaaten> getBundesstaatenListe() {
        return bundesstaatenListe;
    }

    @Override
    public State getInitialState() {
        return new MapColoringState(this, start);
    }

    @Override
    public State getGoalState() {
        return new MapColoringState(this, goal);
    }

    @Override
    public boolean isGoalState(State state) {
        MapColoringState stateM = (MapColoringState) state;
        // TODO: Nacharbeiten. Für jede Domäne darf es nur eine Variable geben außer für Variablen ohne Constraints wie bei T

        List<Boolean> isGoal = new ArrayList<>();
        int index = 0;

        for (int i = 0; i < bundesstaatenListe.size(); i++){
            isGoal.add(false);
        }

        for (Bundesstaaten bs : bundesstaatenListe){
            if (bs.getDomain().size() == 1){
                isGoal.set(index, true);
            } else {
                isGoal.set(index, false);
            }
            index++;
        }

        int counter = 0;
        for (Boolean b : isGoal){
            if (b){
                counter++;
            }
        }

        if(counter == isGoal.size()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getActions(State state) {
        LinkedList<String> l = new LinkedList<String>();

        //rpstate.getPosition -> hoffentlich Schritt 1 WA

        if (state instanceof MapColoringState rpState){
            for (List<String> vce : arcs){
                if (vce.get(0).equals(rpState.getPosition())){

                    l.add(vce.get(1));
                }
            }
        }

        // Mit folgendem Zeilenaufruf funktioniert die Baumvisualisierung nicht mehr ganz
//        runAC3(); // TODO: ???

        return l;
    }

    @Override
    public State getSuccessor(State state, String action) {
        MapColoringState stateM = (MapColoringState) state;
        String oldPos = stateM.getPosition();
        String neuPos = oldPos;

        MapColoringState result = new MapColoringState(this, neuPos);
        //        String neuPos = stateM.get
        //        Möglicherweise action = NT

        neuPos = action;
        result.setPosition(neuPos);

        return result;
    }

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

