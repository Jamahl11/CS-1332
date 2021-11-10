import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author Jamahl Grant
 * @version 1.0
 * @userid jgrant86
 * @GTID 903558339
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        Comparable[] comp = new Comparable[INITIAL_CAPACITY];
        backingArray = (T[]) comp;
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to Heap");
        }
        Comparable[] comp = new Comparable[(data.size() * 2) + 1];
        backingArray = (T[]) comp;
        for (int i = 1; i <= data.size(); i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("Cannot "
                       + "add null data to Heap");
            } else {
                backingArray[i] = data.get(i - 1);
                size++;
            }
        }
        int num = data.size() / 2;
        for (int i = num; i > 0; i--) {
            downHeap(i);
        }
    }
    /**
     * Performs down heap on data at index given.
     * @param index the index of the data to be downHeaped
     */
    private void downHeap(int index) {
        while (index * 2 <= backingArray.length
                && backingArray[index * 2] != null) {
            if (backingArray[index * 2] != null
                   && backingArray[(index * 2) + 1] == null) {
                if (backingArray[index * 2].compareTo(backingArray[index])
                        < 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[index * 2];
                    backingArray[index * 2] = temp;
                    index = index * 2;

                } else {
                    return;
                }

            } else {
                if (backingArray[index * 2].compareTo(
                        backingArray[(index * 2) + 1]) < 0) {
                    if (backingArray[index * 2].compareTo(
                            backingArray[index]) < 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[index * 2];
                        backingArray[index * 2] = temp;
                        index = index * 2;

                    } else {
                        return;
                    }

                } else if (backingArray[(index * 2) + 1].compareTo(
                        backingArray[index * 2]) < 0) {
                    if (backingArray[(index * 2) + 1].compareTo(
                            backingArray[index]) < 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[(index * 2) + 1];
                        backingArray[(index * 2) + 1] = temp;
                        index = (index * 2) + 1;

                    } else {
                        return;
                    }

                } else {
                    if (backingArray[index * 2].compareTo(
                            backingArray[index]) < 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[index * 2];
                        backingArray[index * 2] = temp;
                        index = index * 2;

                    } else {
                        return;
                    }
                }
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot"
                   + " add null data to minHeap");

        } else {
            if (size + 1 == backingArray.length) {
                Comparable[] comp = new Comparable[backingArray.length * 2];
                for (int i = 1; i <= size; i++) {
                    comp[i] = backingArray[i];
                }
                backingArray = (T[]) comp;
            }
            backingArray[size + 1] = data;
            upHeap(size + 1);
            size++;
        }
    }

    /**
     * performs up heat on data at index given.
     * @param index of the data to be upHeaped
     */
    private void upHeap(int index) {
        while (index / 2 >= 1 && backingArray[index].compareTo(
                backingArray[index / 2]) < 0) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = temp;
            index = index / 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (backingArray[1] == null) {
            throw new java.util.NoSuchElementException("You cannot retrieve"
                    + " min element because MinHeap is empty");

        } else {
            T temp = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            downHeap(1);
            return temp;
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("You cannot retrieve"
                    + " min element because MinHeap is empty");

        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[1] == null;

    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        Comparable[] comp = new Comparable[INITIAL_CAPACITY];
        backingArray = (T[]) comp;
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
