import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

/**
 * Your implementation of a BST.
 *
 * @author Jamahl Grant
 * @version 1.0
 * @userid jgrant86
 * @GTID 903558339
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: w3schools.com, geeksforgeeks.org
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        for (T node: data) {
            if (node == null) {
                throw new IllegalArgumentException("Cannot add null "
                       + "data to BST");

            } else if (root == null) {
                root = new BSTNode<T>(node);
                size++;

            } else {
                BSTNode<T> current = root;
                BSTNode<T> newNode = new BSTNode<T>(node);
                boolean sort = true;
                while (sort) {
                    if (newNode.getData().compareTo(current.getData()) < 0) {
                        if (current.getLeft() == null) {
                            current.setLeft(newNode);
                            size++;
                            sort = false;
                        } else {
                            current = current.getLeft();
                        }


                    } else if (newNode.getData().compareTo(current.getData())
                            > 0) {
                        if (current.getRight() == null) {
                            current.setRight(newNode);
                            size++;
                            sort = false;
                        } else {
                            current = current.getRight();
                        }

                    } else {
                        break;
                    }
                }

            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove "
                    + "null data from BST");

        }
        root = helpAdd(data, root);
    }

    private BSTNode<T> helpAdd(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode(data);

        } else if (data.compareTo(node.getData()) == 0) {
            return node;

        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(helpAdd(data, node.getLeft()));
            return node;

        } else {
            node.setRight(helpAdd(data, node.getRight()));
            return node;
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove"
                    + " null data from BST");

        } else if (data.compareTo(root.getData()) == 0) {
            if (root.getLeft() == null && root.getRight() == null) {
                T delete = root.getData();
                root = null;
                size--;
                return delete;

            } else if (root.getLeft() == null && root.getRight() != null) {
                T delete = root.getData();
                root = root.getRight();
                size--;
                return delete;

            } else if (root.getLeft() != null && root.getRight() == null) {
                T delete = root.getData();
                root = root.getLeft();
                size--;
                return delete;
            } else {
                BSTNode<T> current = root.getLeft();
                while (current.getRight() != null) {
                    current = current.getRight();
                }
                root.setData(current.getData());
                return helpRemove(current.getData(), root);
            }
        } else {
            return helpRemove(data, root);
        }
    }
    private T helpRemove(T data, BSTNode<T> node) {
        if (data.compareTo(node.getLeft().getData()) == 0) {
            if (node.getLeft().getLeft() == null
                    && node.getLeft().getRight() == null) {
                T delete = node.getLeft().getData();
                node.setLeft(null);
                size--;
                return delete;

            } else if (node.getLeft().getLeft() == null
                    && node.getLeft().getRight() != null) {
                T delete = node.getLeft().getData();
                node.setLeft(node.getLeft().getRight());
                size--;
                return delete;

            }  else if (node.getLeft().getLeft() != null
                    && node.getLeft().getRight() == null) {
                T delete = node.getLeft().getData();
                node.setLeft(node.getLeft().getLeft());
                size--;
                return delete;

            }   else {
                BSTNode<T> current = node.getLeft().getLeft();
                while (current.getRight() != null) {
                    current = current.getRight();
                }
                node.getLeft().setData(current.getData());
                return helpRemove(current.getData(), node.getLeft());
            }

        } else if (data.compareTo(node.getRight().getData()) == 0) {
            if (node.getRight().getLeft() == null
                    && node.getRight().getRight() == null) {
                T delete = node.getRight().getData();
                node.setRight(null);
                size--;
                return delete;

            } else if (node.getRight().getLeft() == null
                    && node.getRight().getRight() != null) {
                T delete = node.getRight().getData();
                node.setRight(node.getRight().getRight());
                size--;
                return delete;

            } else if (node.getRight().getLeft() != null
                    && node.getRight().getRight() == null) {
                T delete = node.getRight().getData();
                node.setLeft(node.getRight().getLeft());
                size--;
                return delete;

            } else {
                BSTNode<T> current = node.getRight().getLeft();
                while (current.getRight() != null) {
                    current = current.getRight();
                }
                node.getRight().setData(current.getData());
                return helpRemove(current.getData(), node.getRight());
            }
        } else {
            if (data.compareTo(node.getData()) < 0) {
                if (node.getLeft() != null) {
                    return helpRemove(data, node.getLeft());

                } else {
                    throw new java.util.NoSuchElementException("You cannot "
                            + " remove an element that is not in the BST");
                }


            } else {
                if (node.getRight() != null) {
                    return helpRemove(data, node.getRight());

                } else {
                    throw new java.util.NoSuchElementException("You cannot "
                            + " remove an element that is not in the BST");
                }

            }
        }
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove"
                    + " null data from BST");

        } else {
            return helpGet(data, root);
        }
    }

    private T helpGet(T data, BSTNode<T> node) {
        if (data.compareTo(node.getData()) == 0) {
            return node.getData();

        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                throw new java.util.NoSuchElementException("This element is not"
                        + " in the BST");

            } else {
                return helpGet(data, node.getLeft());
            }
        } else {
            if (node.getRight() == null) {
                throw new java.util.NoSuchElementException("This element is not"
                        + " in the BST");

            } else {
                return helpGet(data, node.getRight());
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot"
                   + " remove null data from BST");

        } else {
            return helpContain(data, root);
        }
    }

    private boolean helpContain(T data, BSTNode<T> node) {
        if (data.compareTo(node.getData()) == 0) {
            return true;

        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                throw new java.util.NoSuchElementException("This element is not"
                        + " in the BST");

            } else {
                return helpContain(data, node.getLeft());
            }
        } else {
            if (node.getRight() == null) {
                throw new java.util.NoSuchElementException("This element is not"
                        + " in the BST");

            } else {
                return helpContain(data, node.getRight());
            }
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> order = new ArrayList<T>();
        return helpPreOrder(root, order);
    }

    private List<T> helpPreOrder(BSTNode<T> node, List<T> order) {
        if (node == null) {
            return order;

        } else {
            order.add(node.getData());
            helpPreOrder(node.getLeft(), order);
            helpPreOrder(node.getRight(), order);
            return order;
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> order = new ArrayList<T>();
        return helpInOrder(root, order);
    }

    private List<T> helpInOrder(BSTNode<T> node, List<T> order) {
        if (node == null) {
            return order;

        } else {
            helpInOrder(node.getLeft(), order);
            order.add(node.getData());
            helpInOrder(node.getRight(), order);
            return order;
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> order = new ArrayList<T>();
        return helpPostOrder(root, order);
    }

    private List<T> helpPostOrder(BSTNode<T> node, List<T> order) {
        if (node == null) {
            return order;

        } else {
            helpPostOrder(node.getLeft(), order);
            helpPostOrder(node.getRight(), order);
            order.add(node.getData());
            return order;
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> newList = new ArrayList<>();
        LinkedList<BSTNode<T>> order = new LinkedList<>();
        order.add(root);
        while (!order.isEmpty()) {
            BSTNode<T> temp = order.removeFirst();
            if (temp != null) {
                newList.add(temp.getData());
                order.add(temp.getLeft());
                order.add(temp.getRight());
            }
        }
        return newList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else if (size == 1) {
            return 0;
        } else {
            return heightR(root);
        }
    }

    /**
     * Recursive helper for height method.
     *
     * @param current the current node
     * @return int representing height of tree
     */
    private int heightR(BSTNode<T> current) {
        if (current == null) {
            return -1;
        }
        int leftHeight = heightR(current.getLeft());
        int rightHeight = heightR(current.getRight());

        if (leftHeight > rightHeight) {
            return 1 + leftHeight;
        } else {
            return 1 + rightHeight;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        LinkedList<T> list = new LinkedList<>();
        if (k > size) {
            throw new IllegalArgumentException("Your int must"
                    + " be less than the size of the BST");
        } else {
            helperKLargest(root, k, list);
        }
        return list;
    }

    /**
     * Recursive helper for kLargest method.
     *
     * @param curr the current node
     * @param k the number of largest integers to be returned
     * @param list the list of k largest integers to be returned
     */
    private void helperKLargest(BSTNode<T> curr, int k, LinkedList<T> list) {
        if (curr != null) {
            helperKLargest(curr.getRight(), k, list);
            if (k > list.size()) {
                list.addFirst((curr.getData()));
            }
            helperKLargest(curr.getLeft(), k, list);
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
