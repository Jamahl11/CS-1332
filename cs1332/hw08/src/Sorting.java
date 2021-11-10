import java.util.*;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array "
                    + "and/or with null comparator ");
        }
        boolean swap;
        for (int n = 1; n < arr.length; n++) {
            for (int i = n; i > 0; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    T temp = arr[i - 1];
                    arr[i - 1] = arr[i];
                    arr[i] = temp;

                } else {
                    break;
                }
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array "
                    + "and/or with null comparator ");
        }
        boolean swap = true;
        int start = 0;
        int end = arr.length - 1;
        int tempEnd = end;
        int tempStart = start;
        while (swap) {
            swap = false;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                    swap = true;
                    tempEnd = i;
                }
            }
            end = tempEnd;
            if (swap) {
                swap = false;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        T temp = arr[i - 1];
                        arr[i - 1] = arr[i];
                        arr[i] = temp;
                        swap = true;
                        tempStart = i;
                    }
                }
            }
            start = tempStart;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot sort null array "
                    + "and/or with null comparator ");
        }
        if (arr.length <= 1) {
            return;
        }
        int mid = arr.length / 2;
        T[] left = Arrays.copyOfRange(arr, 0, mid);
        T[] right = Arrays.copyOfRange(arr, mid, arr.length);
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < left.length && rightIndex < right.length) {
            if (comparator.compare(left[leftIndex], right[rightIndex]) <= 0) {
                arr[leftIndex + rightIndex] = left[leftIndex];
                leftIndex++;

            } else {
                arr[leftIndex + rightIndex] = right[rightIndex];
                rightIndex++;
            }
        }
        while (leftIndex < left.length) {
            arr[leftIndex + rightIndex] = left[leftIndex];
            leftIndex++;
        }
        while (rightIndex < right.length) {
            arr[leftIndex + rightIndex] = right[rightIndex];
            rightIndex++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Cannot sort null array "
                    + "and/or with null comparator.");
        }
        helpQuickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * helper method for quickSort method.
     * @param arr the array to be sorted
     * @param start the start index of the array
     * @param end the end index of the array
     * @param comparator the comparator object passed in
     * @param rand the random object passed in
     */
    public static <T> void helpQuickSort(T[] arr, int start, int end,
                                        Comparator<T> comparator, Random rand) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotValue = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivotValue;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotValue) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotValue) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        arr[start] = arr[j];
        arr[j] = pivotValue;
        helpQuickSort(arr, start, j - 1, comparator, rand);
        helpQuickSort(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array "
                    + "and/or with null comparator.");
        }
        LinkedList<Integer>[] buckets =
                (LinkedList<Integer>[]) new LinkedList[19];
        int max = 0;
        for (int element: arr) {
            if (element > max) {
                max = element;
            }
        }
        int pass = 1;
        int baseTen = 10;
        while (max >= baseTen)  {
            baseTen *= 10;
            pass++;
        }
        int div = 1;
        for (int i = 0; i < pass; i++) {
            for (int num: arr) {
                int digit = ((num / div) % 10) + 9;
                if (buckets[digit] == null) {
                    buckets[digit] = new LinkedList<Integer>();

                }
                buckets[digit].add(num);
            }
            div *= 10;
            int index = 0;
            for (LinkedList<Integer> list: buckets) {
                while ((list != null) && !list.isEmpty()) {
                    arr[index] = list.removeFirst();
                    index++;
                }
            }
            index = 0;
        }

    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot sort null array "
                    + "and/or with null comparator.");
        }
        PriorityQueue<Integer> database = new PriorityQueue<Integer>(data);
        int[] result = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = database.poll();
        }
        return result;
    }
}
