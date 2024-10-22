package ai_algorithm.problems.mapColoring.general;

import ai_algorithm.problems.mapColoring.AbstractMapColoringProblem;
import ai_algorithm.problems.mapColoring.AbstractMapColoringState;

import java.util.List;
import java.util.Map;

/**
 * The MapColoringState class represents a state in the map coloring problem.
 * The state is represented by a map of variables to colors.
 * The class provides methods to access the problem and the assignments.
 * The class also provides methods to compare states and to generate hash codes.
 * The class is used in the MapColoringProblem class.
 *
 * @author Alexander
 */
public class MapColoringStateGeneral extends AbstractMapColoringState {

    /**
     * initializes state with problem, domain and assignments
     *
     * @param problem
     * @param domain
     * @param assignments
     */
    public MapColoringStateGeneral(AbstractMapColoringProblem problem, Map<String, List<String>> domain, Map<String, String> assignments) {
        super(problem, domain, assignments);
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