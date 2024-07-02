package ai_algorithm.problems.mapColoring.general;

import ai_algorithm.problems.mapColoring.AbstractMapColoringProblem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapColoringProblemGeneral extends AbstractMapColoringProblem{

    public MapColoringProblemGeneral(){
        variables = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        neighbors = Map.of(
                "A", List.of("B", "C"), // Constraint from WA
                "B", List.of("A", "C", "D"), // Constraint from NT
                "C", List.of("A", "B", "D", "E", "F"), // Constraint from SA
                "D", List.of("C", "B", "E"), // Constraint from Q
                "E", List.of("C", "D", "F"), // Constraint from NSW
                "F", List.of("E", "C"), // Constraint from V
                "G", List.of(), // Constraint from T
                "H", List.of("B", "D") // Constraint from H
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
