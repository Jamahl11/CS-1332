/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Jamahl Grant
 * @version 1.0
 * @userid jgrant86 (i.e. gburdell3)
 * @GTID 903558339 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index you are trying to access is "
                    + "outside the bounds of the Doubly Linked List");
        } else if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into Doubly Linked List");
        }
        DoublyLinkedListNode <T> temp = new DoublyLinkedListNode(data);
        if ( size == 0 ) {
            head = temp;
            tail = temp;
            size++;

        } else if (index == 0) {
            addToFront(data);

        } else if (index == size) {
            addToBack(data);

        } else {
            DoublyLinkedListNode <T> current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            current.getNext().setPrevious(temp);
            temp.setNext(current.getNext());
            temp.setPrevious(current);
            current.setNext(temp);
            size++;

        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into Doubly Linked List");
        }
        DoublyLinkedListNode <T> temp = new DoublyLinkedListNode(data);
        if ( size == 0 ) {
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
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be inserted into Doubly Linked List");
        }
        DoublyLinkedListNode <T> temp = new DoublyLinkedListNode(data);
        if ( size == 0 ) {
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
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index you are trying to access is "
                    + "outside the bounds of the Doubly Linked List");
        } else if (index == 0) {
            return removeFromFront();

        } else if (index == size - 1) {
            return removeFromBack();

        } else {
            DoublyLinkedListNode <T> current = head;
            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            DoublyLinkedListNode <T> delete = current.getNext();
            current.setNext(current.getNext().getNext());
            current.getNext().setPrevious(current);
            size--;
            return delete.getData();
        }

    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove element from this "
                    + "DoublyLinkedList because it is empty");
        } else {
            DoublyLinkedListNode <T> delete = head;
            head.getNext().setPrevious(null);
            head = head.getNext();
            size--;
            return delete.getData();
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove element from this "
                    + "DoublyLinkedList because it is empty");
        } else if (size == 1) {
            DoublyLinkedListNode <T> delete = tail;
            head = null;
            tail = null;
            size--;
            return delete.getData();

        } else {
            DoublyLinkedListNode <T> delete = tail;
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size--;
            return delete.getData();
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index you are trying to access is "
                    + "outside the bounds of the Doubly Linked List");

        } else if (index == 0) {
            return head.getData();

        } else if (index == (size - 1)) {
            return tail.getData();

        } else {
            DoublyLinkedListNode <T> current = head;
            for (int i = 1; i <= index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be removed Doubly Linked List");
        }

        if (size == 0) {
            throw new java.util.NoSuchElementException("No element in the Doubly Linked list contains data corresponding "
                    + "to the parameter passed.");

        } else if (size == 1) {
            if (head.getData() == data) {
                T temp = head.getData();
                head = null;
                tail = null;
                size--;
                return temp;
            }

        } else if (tail.getData() == data) {
            DoublyLinkedListNode <T> delete = tail;
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size--;
            return delete.getData();

        }

        else {
            DoublyLinkedListNode <T> current = tail.getPrevious();
            while (current != null) {
                if (current.getData() == data) {
                    T temp;
                    if (current == head) {
                        temp = head.getData();
                        current.getNext().setPrevious(null);
                        head = current.getNext();
                        size--;
                        return temp;

                    } else {
                        temp = current.getData();
                        current.getPrevious().setNext(current.getNext());
                        current.getNext().setPrevious(current.getPrevious());
                        size--;
                        return temp;
                    }

                } else if (current == head) {
                    throw new java.util.NoSuchElementException("No element in the Doubly Linked list contains data corresponding "
                            + "to the parameter passed.");

                } else {
                    current = current.getPrevious();
                }
            }
        }
        throw new java.util.NoSuchElementException("No element in the Doubly Linked list contains data corresponding "
                + "to the parameter passed.");

    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] repArray = new Object[size];
        DoublyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            repArray[i] = current.getData();
            current = current.getNext();
        }
        return repArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
