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
        // TODO: implement
    }

    /**
     * Removes the node with the given data from the BST.
     * Does nothing if the element does not exist.
     * @param data data to remove
     */
    public void delete(T data) {
        // TODO: implement
    }

    /**
     * Searches for a node with the given data.
     * @param data data to search
     * @return TreeNode if found, null otherwise
     */
    public TreeNode<T> search(T data) {
        // TODO: implement
        return null;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }
}
