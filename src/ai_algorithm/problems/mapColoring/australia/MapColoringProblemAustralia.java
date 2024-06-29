package ai_algorithm.problems.mapColoring.australia;

import ai_algorithm.problems.mapColoring.AbstractMapColoringProblem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapColoringProblemAustralia extends AbstractMapColoringProblem {

    public MapColoringProblemAustralia(){
        variables = Arrays.asList("WA", "NT", "SA", "Q", "NSW", "V", "T");
        neighbors = Map.of(
                "WA", List.of("NT", "SA"), // Constraint from WA
                "NT", List.of("WA", "SA", "Q"), // Constraint from NT
                "SA", List.of("WA", "NT", "Q", "NSW", "V"), // Constraint from SA
                "Q", List.of("SA", "NT", "NSW"), // Constraint from Q
                "NSW", List.of("SA", "Q", "V"), // Constraint from NSW
                "V", List.of("NSW", "SA"), // Constraint from V
                "T", List.of() // Constraint from T
        );
        domain = new HashMap<>();
        for (String variable : variables) {
            domain.put(variable, Arrays.asList("Red", "Green", "Blue"));
        }
        fillQueue();
    }

}
