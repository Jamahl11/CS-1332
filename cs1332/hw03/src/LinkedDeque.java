/**
 * Your implementation of a LinkedDeque.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid jgrant86
 * @GTID 903558339
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to deque");
        }

        LinkedNode<T> temp = new LinkedNode(data);
        if (size == 0) {
            head = temp;
            tail = temp;
            size++;

        } else {
            temp.setNext(head);
            head.setPrevious(temp);
            head = temp;
            size++;
        }

    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to deque");
        }

        LinkedNode<T> temp = new LinkedNode(data);
        if (size == 0) {
            head = temp;
            tail = temp;
            size++;

        } else {
            temp.setPrevious(tail);
            tail.setNext(temp);
            tail = temp;
            size++;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove"
                    + " an element because the deque is empty");
        } else if (size == 1) {
            LinkedNode<T> delete = head;
            head = null;
            tail = null;
            size--;
            return delete.getData();

        } else {
            LinkedNode<T> delete = head;
            head.getNext().setPrevious(null);
            head = head.getNext();
            size--;
            return delete.getData();

        }

    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove"
                    + " an element because the deque is empty");
        } else if (size == 1) {
            LinkedNode<T> delete = tail;
            head = null;
            tail = null;
            size--;
            return delete.getData();

        } else {
            LinkedNode<T> delete = tail;
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size--;
            return delete.getData();
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove"
                    + " an element because the deque is empty");
        }
        return head.getData();
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove"
                    + " an element because the deque is empty");
        }
        return tail.getData();
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
