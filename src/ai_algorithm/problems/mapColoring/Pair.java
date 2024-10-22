package ai_algorithm.problems.mapColoring;

import java.util.Objects;

/**
 * @author Ravi Mohan
 * @author Mike Stampone
 * @author Ruediger Lunde
 *
 */
public class Pair<X, Y> {
    private final X a;

    private final Y b;

    /**
     * Constructs a Pair from two given elements
     *
     * @param a
     *            the first element
     * @param b
     *            the second element
     */
    public Pair(X a, Y b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Returns the first element of the pair
     *
     * @return the first element of the pair
     */
    public X getFirst() {
        return a;
    }

    /**
     * Returns the second element of the pair
     *
     * @return the second element of the pair
     */
    public Y getSecond() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && getClass() == o.getClass()) {
            Pair<?, ?> p = (Pair<?, ?>) o;
            return Objects.equals(a, p.a) && Objects.equals(b, p.b);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (a == null ? 0 : 7 * a.hashCode()) + 31 * (b == null ? 0 : b.hashCode());
    }

    @Override
    public String toString() {
        return "< " + getFirst().toString() + " , " + getSecond().toString() + " > ";
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