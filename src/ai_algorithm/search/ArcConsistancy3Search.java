package ai_algorithm.search;

import ai_algorithm.Frontier;
import ai_algorithm.Path;
import ai_algorithm.SearchNode;
import ai_algorithm.problems.Problem;
import ai_algorithm.problems.State;
import ai_algorithm.problems.mapColoring.MapColoringProblem; // ???
import application.debugger.Debugger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArcConsistancy3Search extends SearchAlgorithm{

    private MapColoringProblem mapColoringProblem;

    public ArcConsistancy3Search() {
        super();
    }

    public ArcConsistancy3Search(Problem problem) { // KA OB NÖTIG
        super(problem);
    }

    // ############################# \/ NOCH KA WIE GENAU IMPLEMENTIERT WERDEN SOLL \/ #############################
    @Override
    public Path search() {
        // TODO: Implement ArcConsistancy3Search
        SearchNode start = new SearchNode(null, problem.getInitialState(), 0, null);
        Frontier frontier = new Frontier();
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
            if (problem.isGoalState(node.getState())) {
                return node.getPath();
            }
            for (SearchNode child : node.expand()) {
                State state = child.getState();
                if (problem.isGoalState(state)) {
                    Debugger.pause("Finished");
                    return child.getPath();
                }
//                if (!contains2(node, state)) {
//                    frontier.add(child);
//                }
            }
        }


//        mapColoringProblem = (MapColoringProblem) problem;

        Debugger.pause("No Sulution found");
        return null;
    }
    // ############################# /\ NOCH KA WIE GENAU IMPLEMENTIERT WERDEN SOLL /\ #############################

    private boolean ac3() {


        return true;
    }

    // ############################# \/ SOLLLTE SO SEIN \/ #############################
    public boolean revise(String Xi, String Xj) {
        boolean revised = false;
        int lindex = mapColoringProblem.getVariables().indexOf(Xi);
        for(String x : new ArrayList<>(mapColoringProblem.getDomain().get(lindex))) {
            if(!mapColoringProblem.getConstraints().get(lindex).contains(Xj)) {
                break;
            }
            if(!mapColoringProblem.getAssignments().containsKey(Xj)) {
                continue;
            }
            if(mapColoringProblem.getAssignments().get(Xj).equals(x)) {
                mapColoringProblem.getDomain().get(lindex).remove(x);
                revised = true;
            }
        }
        if(!revised && !mapColoringProblem.getDomain().get(lindex).isEmpty()){
            mapColoringProblem.getAssignments().put(Xi, mapColoringProblem.getDomain().get(lindex).get(0));
        }
        return revised;
    }
    // ############################# /\ SOLLLTE SO SEIN /\ #############################


    private Path backtrackingSearch() {

        return null;
    }

    private Path bachtracking(SearchNode node) {

        return null;
    }

    private boolean isConsistent(String var, String value) {

        return true;
    }

    private String selectUnassignedVariable() {

        return null;
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
