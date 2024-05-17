package ai_algorithm.search;

import ai_algorithm.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArcConsistancy3Search extends SearchAlgorithm{

    // TODO: Implement ArcConsistancy3Search

    // Orientierung \/
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

    @Override
    public Path search() {
        return null;
    }

}
