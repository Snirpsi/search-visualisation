package ai_algorithm.specific_algorithm_logic.csp.csp_content.solver.framework;

import java.util.*;

/**
 * Link to the original source from AIMA:
 * https://github.com/aimacode/aima-java/blob/AIMA3e/aima-core/src/main/java/aima/core/search/framework/QueueFactory.java
 *
 * Factory class for queues. Changes made here will affect all queue based
 * search algorithms of this library.
 *
 * @author Ruediger Lunde
 * @author Alexander (Comments adjusted)
 */
public class QueueFactory {

    /**
     * Returns a standard java {@link PriorityQueue}. Note that the smallest
     * element comes first!
     *
     * @param comparator The comparator to be used for ordering the elements.
     * @return A priority queue.
     */
    public static <E> Queue<E> createPriorityQueue(Comparator<? super E> comparator) {
        return new PriorityQueue<E>(11, comparator);
    }

    /**
     * Returns a Last-in-first-out (Lifo) view on a {@link LinkedList}.
     */
    public static <E> Queue<E> createLifoQueue() {
        return Collections.asLifoQueue(new LinkedList<E>());
    }

    /**
     * Returns a {@link LinkedList}.
     */
    public static <E> Queue<E> createFifoQueue() {
        return new LinkedList<E>();
    }

    /**
     * Returns a {@link LinkedList} which is extended by a {@link HashSet} for efficient containment checks. Elements
     * are only added if they are not already present in the queue. Use only queue methods for access!
     */
    public static <E> Queue<E> createFifoQueueNoDuplicates() {
        return new FifoQueueWithHashSet<E>();
    }

    /**
     * A {@link LinkedList} which is extended by a {@link HashSet} for efficient containment checks. Elements
     * are only added if they are not already present in the queue. Use only queue methods for access!
     */
    private static class FifoQueueWithHashSet<E> extends LinkedList<E> implements Queue<E> {
        private HashSet<E> elements = new HashSet<>(); // HashSet for efficient containment checks

        /**
         * Adds the specified element to the queue if it is not already present.
         *
         * @param e The element to be added.
         * @return true if the element was added, false otherwise.
         */
        @Override
        public boolean add(E e) {
            if (!elements.contains(e)) { // Check if the element is already present
                elements.add(e); // Add the element to the HashSet
                return super.add(e);
            }
            return false; // Return false if the element is already present
        }

        @Override
        public boolean offer(E e) {
            if (!elements.contains(e)) { // Check if the element is already present
                elements.add(e); // Add the element to the HashSet
                return super.offer(e);
            }
            return false;
        }

        /**
         * Removes the head of the queue and returns it.
         *
         * @return The head of the queue.
         */
        @Override
        public E remove() {
            E result = super.remove(); // Remove the head of the queue
            elements.remove(result); // Remove the element from the HashSet
            return result; // The head of the queue
        }

        /**
         * Retrieves and removes the head of the queue, or returns null if the queue is empty.
         *
         * @return The head of the queue, or null if the queue is empty.
         */
        @Override
        public E poll() {
            E result = super.poll(); // Retrieve and remove the head of the queue
            if (result != null) // Check if the result is not null
                elements.remove(result); // Remove the element from the HashSet
            return result;
        }

        /**
         * Returns true if the queue contains the specified element.
         *
         * @param e The element to be checked.
         * @return true if the queue contains the element, false otherwise.
         */
        @Override
        public boolean contains(Object e) {
            return elements.contains(e); // Check if the element is present in the HashSet
        }
    }
}