package structures;

public class BinarySearchTree<T extends Comparable<T>> {
    private TreeNode<T> root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts a new element in the correct BST position.
     * Duplicates are not allowed.
     * @param data data to insert (must not be null)
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        root = addRecursive(root, data);
    }

    private TreeNode<T> addRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            size++;
            return new TreeNode<>(data);
        }

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            node.setLeft(addRecursive(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(addRecursive(node.getRight(), data));
        }

        return node;
    }

    /**
     * Removes the node with the given data from the BST.
     * Does nothing if the element does not exist.
     * @param data data to remove
     */
    public void delete(T data) {
        root = deleteRecursive(root, data);
    }

    private TreeNode<T> deleteRecursive(TreeNode<T> node, T data) {
        if (node == null) return null;

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(deleteRecursive(node.getRight(), data));
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                size--;
                return null;
            }

            if (node.getLeft() == null) {
                size--;
                return node.getRight();
            }

            if (node.getRight() == null) {
                size--;
                return node.getLeft();
            }

            TreeNode<T> successor = findMin(node.getRight());
            node.setData(successor.getData());
            node.setRight(deleteRecursive(node.getRight(), successor.getData()));
        }

        return node;
    }

    /**
     * Searches for a node with the given data.
     * @param data data to search
     * @return TreeNode if found, null otherwise
     */
    public TreeNode<T> search(T data) {
        return searchRecursive(root, data);
    }

    private TreeNode<T> searchRecursive(TreeNode<T> node, T data) {
        if (node == null || data == null) return null;

        int cmp = data.compareTo(node.getData());

        if (cmp == 0) return node;
        if (cmp < 0) return searchRecursive(node.getLeft(), data);
        return searchRecursive(node.getRight(), data);
    }

    private TreeNode<T> findMin(TreeNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }
}
