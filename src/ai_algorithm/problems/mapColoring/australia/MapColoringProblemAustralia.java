package ai_algorithm.problems.mapColoring.australia;

import ai_algorithm.problems.mapColoring.AbstractMapColoringProblem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * The MapColoringProblemAustralia class represents a map coloring problem for the map of Australia.
 */
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
            domain.put(variable, Arrays.asList("R", "G", "B"));
        }
        fillQueue();

        variableToCircleMap = new HashMap<>();
        variableTextMap = new HashMap<>();
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
