package ai_algorithm.specific_algorithm_logic.csp.csp_content.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/util/math/permute/CombinationGenerator.java
 *
 * This class generates all possible combinations of a given list of elements.
 *
 * @author samagra
 * @author Alexander (Comments adjusted)
 */
public class CombinationGenerator {
    /**
     * Generates all possible combinations of a given list of elements.
     *
     * @param list The list of elements to generate combinations from.
     * @param r The number of elements in each combination.
     * @param <T> The type of elements in the list.
     * @return An iterable object that can be used to iterate through all possible combinations.
     */
    public static <T> Iterable<List<T>> generateCombinations(List<T> list, int r) {
        return () -> new Iterator<List<T>>() {
            int index = -1; // Index of the current combination.
            int total = (int) CombinationGenerator.nCr(list.size(), r); // Total number of possible combinations.
            int[] currCombination = new int[r]; // Current combination of elements.

            /**
             * Checks if there are more combinations to generate.
             *
             * @return True if there are more combinations, false otherwise.
             */
            @Override
            public boolean hasNext() {
                index++;
                return index < total;
            }

            /**
             * Generates the next combination of elements.
             *
             * @return The next combination of elements.
             */
            @Override
            public List<T> next() {
                if (index == 0) { // If this is the first combination, initialize the current combination.
                    for (int i = 0; i < currCombination.length; i++) {
                        currCombination[i] = i + 1;
                    }
                } else
                    currCombination = CombinationGenerator.generateNextCombination(currCombination, list.size(), r); // Generate the next combination.
                List<T> result = new ArrayList<>(); // Create a list to store the elements of the combination.
                for (int aCurrCombination : currCombination) { // Add each element of the combination to the list.
                    result.add(list.get(aCurrCombination - 1));
                }
                return result;
            }
        };
    }

    /**
     * Calculates the number of possible combinations of r elements from a set of n elements.
     *
     * @param n The total number of elements.
     * @param r The number of elements in each combination.
     * @return The number of possible combinations.
     */
    public static double nCr(int n, int r) {
        int rfact = 1, nfact = 1, nrfact = 1, temp1 = n - r, temp2 = r; // Initialize variables for calculating the number of combinations.
        if (r > n - r) { // Swap r and n-r if r is greater than n-r.
            temp1 = r;
            temp2 = n - r;
        }
        for (int i = 1; i <= n; i++) { // Calculate the factorial of n, r, and n-r.
            if (i <= temp2) { // Calculate the factorial of r and n-r.
                rfact *= i;
                nrfact *= i;
            } else if (i <= temp1) { // Calculate the factorial of n-r.
                nrfact *= i;
            }
            nfact *= i; // Calculate the factorial of n.
        }
        return nfact / (double) (rfact * nrfact); // Calculate and return the number of possible combinations.
    }

    /**
     * Generates the next combination of elements.
     *
     * @param temp The current combination of elements.
     * @param n The total number of elements.
     * @param r The number of elements in each combination.
     * @return The next combination of elements.
     */
    public static int[] generateNextCombination(int[] temp, int n, int r) {
        int m = r; // The index of the first element that can be incremented.
        int maxVal = n; // The maximum value of an element in the combination.
        while (temp[m - 1] == maxVal) { // Find the first element that can be incremented.
            m = m - 1;
            maxVal--;
        }
        temp[m - 1]++;
        for (int j = m; j < r; j++) { // Increment the remaining elements.
            temp[j] = temp[j - 1] + 1; // Increment the current element by 1.
        }
        return temp;
    }
}