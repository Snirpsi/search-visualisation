package ai_algorithm.problems.mapColoring;

import ai_algorithm.problems.State;

import java.util.*;

/**
 * The MapColoringState class represents a state in the map coloring problem.
 * The state is represented by a map of variables to colors.
 * The class provides methods to access the problem and the assignments.
 * The class also provides methods to compare states and to generate hash codes.
 * The class is used in the MapColoringProblem class.
 *
 * @author Alexander
 */

public class MapColoringState extends State {

    /**
     *  reference to the problem
     */
    final private MapColoringProblem problem;


    final Map<String, List<String>> domain;

    /**
     *  reference to the assignments
     */
    final Map<String, String> assignments;


    /**
     * initializes state with problem and assignments
     *
     * @param problem
     * @param domain
     * @param assignments
     */
    public MapColoringState(MapColoringProblem problem, 
                            Map<String, List<String>> domain,
                            Map<String, String> assignments) {
        this.problem = problem;
        this.domain = new HashMap<>(domain);
        this.assignments = new HashMap<>(assignments);
    }

    /**
     * Assignments as representation in the form of a string
     *
     * @return string representation of the state
     */
    public Map<String, String> getAssignments() {
        return assignments;
    }

    public List<String> getDomain(String variable) {
        return new ArrayList<>(domain.get(variable));
    }

    /**
     *  reference to the problem
     *
     * @return problem
     */
    @Override
    public MapColoringProblem getProblem() {
        return this.problem;
    }

    /**
     * hash function
     *
     * @return hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(problem, assignments, domain);
    }

    /**
     * checked for equivalence
     *
     * @param obj
     * @return true if equal false if not equal or null
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MapColoringState other = (MapColoringState) obj;
        return Objects.equals(assignments, other.assignments) &&
                Objects.equals(problem, other.problem) &&
                Objects.equals(domain, other.domain);
    }

    /**
     * Assignements as representation in the form of a string
     *
     * @return string representation of the state
     */
    @Override
    public String toString() {
        return assignments.toString();
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
