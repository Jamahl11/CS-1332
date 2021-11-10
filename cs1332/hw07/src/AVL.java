import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * Your implementation of an AVL Tree.
 *
 * @author YOUR NAME HERE
 * @userid YOUR USER ID HERE (i.e. gburdell3)
 * @GTID YOUR GT ID HERE (i.e. 900000000)
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root = null;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot"
                    + "create AVL from null collection");
        }
        for (T node: data) {
            add(node);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove "
                    + "null data from BST");
        }
        root = helpAdd(data, root);
    }

    private AVLNode<T> helpAdd(T data, AVLNode<T> node) {
        if (node == null) {
            AVLNode<T> temp = new AVLNode<T>(data);
            size++;
            update(temp);
            return temp;

        } else if (data.compareTo(node.getData()) == 0) {
            return node;

        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(helpAdd(data, node.getLeft()));
            update(node);
            return checkBalance(node);

        } else {
            node.setRight(helpAdd(data, node.getRight()));
            update(node);
            return checkBalance(node);
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove "
                    + "null data from BST");
        } else if (data.compareTo(root.getData()) == 0) {
            T temp = root.getData();

            if (root.getLeft() == null && root.getRight() == null) {
                root = null;

            } else if (root.getLeft() == null && root.getRight() != null) {
                root = root.getRight();
                update(root);

            } else if (root.getLeft() != null && root.getRight() == null) {
                root = root.getLeft();
                update(root);

            }  else {
                AVLNode<T> parent = root;
                AVLNode<T> predecessor = root;
                predecessor = predecessor.getRight();
                boolean check = false;
                while (predecessor.getLeft() != null) {
                    check = true;
                    parent = predecessor;
                    predecessor = predecessor.getLeft();
                }
                root.setData(predecessor.getData());
                if (check) {
                    parent.setLeft(predecessor.getRight());

                } else {
                    parent.setRight(predecessor.getRight());
                }

            }
            return temp;
        } else if (data.compareTo(root.getData()) < 0) {
            AVLNode<T> delete = helpRemove(data, root, root.getLeft());
            return delete.getData();
        } else {
            AVLNode<T> delete = helpRemove(data, root, root.getRight());
            return delete.getData();
        }


    }

    /**
     * helper for remove method.
     * @param data the data searched for
     * @param curr the node being compared to
     * @param prev the parent of the node being checked
     * @return node that was deleted
     */
    private AVLNode<T> helpRemove(T data, AVLNode<T> prev, AVLNode<T> curr) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("The"
                    + " element does not exist in the AVL");

        } else if (data.compareTo(curr.getData()) > 0) {
            AVLNode<T> delete = helpRemove(data, curr, curr.getRight());
            removeUpdate(prev, curr);
            return delete;

        } else if (data.compareTo(curr.getData()) < 0) {
            AVLNode<T> delete = helpRemove(data, curr, curr.getLeft());
            removeUpdate(prev, curr);
            return delete;

        } else {
           AVLNode<T> delete = delete(prev, curr);
           return delete;
        }
    }

    /**
     * deletes the node passed in.
     * @param curr the node to be deleted
     * @param prev the parent of the node being deleted
     * @return the ndoe deleted
     */
    private AVLNode<T> delete(AVLNode<T> prev, AVLNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            if (prev.getData().compareTo(curr.getData()) < 0) {
                prev.setRight(null);


            } else {
                prev.setLeft(null);
            }
            update(prev);
            return curr;

        } else if (curr.getLeft() == null && curr.getRight() != null) {
            if (prev.getData().compareTo(curr.getData()) < 0) {
                prev.setRight(curr.getRight());

            } else {
                prev.setLeft(curr.getRight());
            }
            update(prev);
            return curr;

        } else if (curr.getLeft() != null && curr.getRight() == null) {
            if (prev.getData().compareTo(curr.getData()) < 0) {
                prev.setRight(curr.getLeft());

            } else {
                prev.setLeft(curr.getLeft());
            }
            update(prev);
            return curr;
        }  else {
            AVLNode<T> parent = curr;
            AVLNode<T> predecessor = curr;
            predecessor = predecessor.getRight();
            boolean check = false;
            while (predecessor.getLeft() != null) {
                check = true;
                parent = predecessor;
                predecessor = predecessor.getLeft();
            }
            curr.setData(predecessor.getData());
            if (predecessor.getRight() != null) {
                if (check) {
                    parent.setLeft(predecessor.getRight());

                } else {
                    parent.setRight(predecessor.getRight());
                }

            } else {
                if (check) {
                    parent.setLeft(null);

                } else {
                    parent.setRight(null);
                }
            }
            update(prev);
            return curr;
        }
    }

    /**
     * checks if node needs to rotated.
     * @param prev the parent of the node being checked
     * @param curr the node being checked for rotations
     */
    private void removeUpdate(AVLNode<T> prev, AVLNode<T> curr) {
        if (-1 <= curr.getBalanceFactor() && curr.getBalanceFactor() <= 1) {
            return;

        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                if (prev.getData().compareTo(curr.getData()) < 0) {
                    prev.setRight(leftRotate(curr));

                } else {
                    prev.setLeft(leftRotate(curr));
                }
                update(prev);

            } else {
                curr.setRight(rightRotate(curr.getRight()));
                if (prev.getData().compareTo(curr.getData()) < 0) {
                    prev.setRight(leftRotate(curr));

                } else {
                    prev.setLeft(leftRotate(curr));
                }
                update(prev);
            }

        } else {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                if (prev.getData().compareTo(curr.getData()) < 0) {
                    prev.setRight(rightRotate(curr));

                } else {
                    prev.setLeft(rightRotate(curr));
                }
                update(prev);
            } else {
                curr.setLeft(leftRotate(curr.getLeft()));
                if (prev.getData().compareTo(curr.getData()) < 0) {
                    prev.setRight(rightRotate(curr));

                } else {
                    prev.setLeft(rightRotate(curr));
                }
                update(prev);
            }
        }
    }


    /**
     * calculates and updates height and balance factor of AVL node.
     * @param curr the node to be updated
     */
    private void update(AVLNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            curr.setBalanceFactor(0);
            curr.setHeight(0);

        } else if (curr.getLeft() != null && curr.getRight() == null) {
            int leftHeight = curr.getLeft().getHeight();
            curr.setBalanceFactor(leftHeight + 1);
            curr.setHeight(leftHeight + 1);

        } else if (curr.getLeft() == null && curr.getRight() != null) {
            int rightHeight = curr.getRight().getHeight();
            curr.setBalanceFactor(-1 - rightHeight);
            curr.setHeight(rightHeight + 1);

        } else {
            int leftHeight = curr.getLeft().getHeight();
            int rightHeight = curr.getRight().getHeight();
            curr.setBalanceFactor(leftHeight - rightHeight);
            if (leftHeight >= rightHeight) {
                curr.setHeight(leftHeight + 1);

            } else {
                curr.setHeight(rightHeight + 1);
            }
        }
    }

    /**
     * performs a left rotation on node that is right heavy.
     * @param curr the node that requires rotation
     * @return the node to be set to parents child
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getRight();
        curr.setRight(curr.getRight().getLeft());
        temp.setLeft(curr);
        update(curr);
        update(temp);
        return temp;
    }

    /**
     * performs a right rotation on node that is left heavy.
     * @param curr the node that requires rotation
     * @return the node to be set to parents child
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft();
        curr.setLeft(curr.getLeft().getRight());
        temp.setRight(curr);
        update(curr);
        update(temp);
        return temp;
    }

    /**
     * checks the balance factor of a node and performs
     * rotations if necessary.
     * @param curr the node to be checked
     * @return the node to be set to parent child
     */
    private AVLNode<T> checkBalance(AVLNode<T> curr) {
        if (-1 <= curr.getBalanceFactor() && curr.getBalanceFactor() <= 1) {
            return curr;

        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                return leftRotate(curr);

            } else {
                curr.setRight(rightRotate(curr.getRight()));
                return leftRotate(curr);
            }

        } else {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                return rightRotate(curr);

            } else {
                curr.setLeft(leftRotate(curr.getLeft()));
                return rightRotate(curr);
            }
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        return helperGet(data, root);
    }

    /**
     * helper for get method.
     * @param data the data to be checked for
     * @param node the current node being checked
     * @return the data of the node searched for
     */
    private T helperGet(T data, AVLNode<T> node) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove "
                    + "null data from BST");

        } else {
            if (node == null) {
                throw new java.util.NoSuchElementException("The"
                        + " element does not exist in the AVL");

            } else if (data.compareTo(node.getData()) == 0) {
                return node.getData();

            } else if (data.compareTo(node.getData()) < 0) {
                return helperGet(data, node.getLeft());

            } else {
                return helperGet(data, node.getRight());
            }
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        return helperContain(data, root);
    }

    /**
     * helper for contain method.
     * @param data the data to be checked for
     * @param node the current node being checked
     * @return if data is in AVL
     */
    private boolean helperContain(T data, AVLNode<T> node) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove "
                    + "null data from BST");

        } else {
            if (node == null) {
                return false;

            } else if (data.compareTo(node.getData()) == 0) {
                return true;

            } else if (data.compareTo(node.getData()) < 0) {
                return helperContain(data, node.getLeft());

            } else {
                return helperContain(data, node.getRight());
            }
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> deep = new ArrayList<T>(size);
        if (size == 0) {
            throw new NullPointerException("The AVL"
                    + " is empty");
        }
        deep = helpDeep(root, deep, root.getHeight());
        return deep;
    }

    private List<T> helpDeep(AVLNode<T> curr, List<T> list, int height) {
        if (curr == null) {
            return list;

        } else {
            if (curr.getHeight() == height) {
                list.add(curr.getData());
            }
            helpDeep(curr.getLeft(), list, height - 1);
            helpDeep(curr.getRight(), list, height - 1);
        }
        return list;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("cannot"
                   + " pass in null data for data1/2");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("data 1"
                    + " cannot be greater than data2");
        }
        List<T> sort = new ArrayList<T>(size);
        sort = helpSort(root, sort, data1, data2);
        return sort;
    }

    /**
     * helper method for sortInBetween.
     * @param curr the node being checked
     * @param list the list of values in between data1 and data2
     * @param data1 the lower bound
     * @param data2 the upper bound
     * @return the list of data in between the two bounds
     */
    private List<T> helpSort(AVLNode<T> curr, List<T> list, T data1, T data2) {
        if (curr == null) {
            return list;

        } else {
            helpSort(curr.getLeft(), list, data1, data2);
            if (curr.getData().compareTo(data1) > 0
                    && curr.getData().compareTo(data2) < 0) {
                list.add(curr.getData());
            }
            helpSort(curr.getRight(), list, data1, data2);
        }
        return list;
    }
    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;

        } else {
            return root.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}