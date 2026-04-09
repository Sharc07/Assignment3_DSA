package TreePackage;

import java.util.*;

public class BinaryTree<T> implements BinaryTreeInterface<T>
{

    private BinaryNode<T> root;

    public BinaryTree() {
        root = null;
    } // end default constructor

    public BinaryTree(T rootData) {
        root = new BinaryNode<T>(rootData);
    } // end constructor

    public BinaryTree(T rootData, BinaryTree<T> leftTree,
            BinaryTree<T> rightTree) {
        privateSetTree(rootData, leftTree, rightTree);
    } // end constructor

    public void setTree(T rootData) {
        root = new BinaryNode<T>(rootData);
    } // end setTree

    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
            BinaryTreeInterface<T> rightTree) {
        privateSetTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
    } // end setTree

    private void privateSetTree(T rootData,
            BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
        root = new BinaryNode<T>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.getRootNode());
            root.getLeftChild().setParent(root);
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree) {
                root.setRightChild(rightTree.getRootNode());
            } else {
                root.setRightChild(rightTree.getRootNode().copy(root));
            }
            root.getRightChild().setParent(root);

            // SET THE THREAD OUT OF THE ROOT
            root.setThread(root.getRightChild().getLeftmostInSubtree());
        } // end if

        if ((leftTree != null) && (this != leftTree)) {
            leftTree.clear();
        }

        if ((rightTree != null) && (this != rightTree)) {
            rightTree.clear();
        }

    } // end privateSetTree

    public T getRootData() {
        if (isEmpty()) {
            throw new EmptyTreeException("Empty tree for operation getRootData");
        } else {
            return root.getData();
        }
    } // end getRootData

    public boolean isEmpty() {
        return root == null;
    } // end isEmpty

    public void clear() {
        root = null;
    } // end clear

    protected void setRootData(T rootData) {
        root.setData(rootData);
    } // end setRootData

    protected void setRootNode(BinaryNode<T> rootNode) {
        root = rootNode;
    } // end setRootNode

    protected BinaryNode<T> getRootNode() {
        return root;
    } // end getRootNode

    public int getHeight() {
        if (root == null) {
            return 0;
        } else {
            return root.getHeight();
        }
    } // end getHeight

    public int getNumberOfNodes() {
        if (root == null) {
            return 0;
        } else {
            return root.getNumberOfNodes();
        }
    } // end getNumberOfNodes

    public void inorderTraverse() {
        inorderTraverse(root);
    } // end inorderTraverse

    private void inorderTraverse(BinaryNode<T> node) {
        if (node != null) {
            inorderTraverse(node.getLeftChild());
            System.out.println(node.getData());
            inorderTraverse(node.getRightChild());
        } // end if
    } // end inorderTraverse

    // iterator now uses threads instead of stack
    private class InorderIterator implements Iterator<T> {

        private BinaryNode<T> currentNode;

        public InorderIterator() {
            currentNode = root;
            if (currentNode != null) {
                while (currentNode.getLeftChild() != null) {
                    currentNode = currentNode.getLeftChild();
                }
            }
        } // end constructor

        public boolean hasNext() {
            return currentNode != null;
        } // end hasNext

        public T next() {
            if (currentNode == null) {
                throw new NoSuchElementException();
            }

            T result = currentNode.getData();

            if (currentNode.hasThread()) {
                currentNode = currentNode.getThread();
            } else {
                currentNode = null;
            }

            return result;
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove

    } // end InorderIterator

    public Iterator<T> getInorderIterator() {
        return new InorderIterator();
    }

    public Iterator<T> getPreorderIterator() {
        throw new RuntimeException("Pre order iterators not yet supported by this class");
    }

    public Iterator<T> getPostorderIterator() {
        throw new RuntimeException("Post order iterators not yet supported by this class");
    }

    public Iterator<T> getLevelOrderIterator() {
        throw new RuntimeException("Level order iterators not yet supported by this class");
    }

    // ADD IN METHODS FOR ACCESSING THE TREE

}