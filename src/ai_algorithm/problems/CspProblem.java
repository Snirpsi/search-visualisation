package ai_algorithm.problems;

import ai_algorithm.problems.mapColoring.Pair;

import java.util.List;
import java.util.Map;

/**
 * Abstract class template for a CSP Problem.
 * The class provides abstract methods to get the variables, the domain and the constraints of the problem.
 *
 * @author Alexander
 */
public abstract class CspProblem extends Problem {

    protected List<String> variables; // Variables of the problem

    protected Map<String, List<String>> domain; // Domain of the problem

    protected List<Pair<String, String>> contraints; // List of all constraints / arcs

    protected Map<String, List<String>> neighbors; // Neighbors for each variable

    public abstract List<String> getVariables();

    public abstract Map<String, List<String>> getDomain();

    public abstract List<Pair<String, String>> getContraints();

    public abstract List<String> getNeighbors(String variable);

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