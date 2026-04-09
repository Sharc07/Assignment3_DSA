package TreePackage; 
    
/** An implementation of the ADT Binary Search Tree.
 *
 */
    
public class BinarySearchTree<T extends Comparable<? super T>>
                extends BinaryTree<T>
                implements SearchTreeInterface<T>
{
    public BinarySearchTree()
    {
        super();
    } // end default constructor
    
    public BinarySearchTree(T rootEntry)
    {
        super();
        setRootNode(new BinaryNode<T>(rootEntry));
    } // end constructor
    
    
    public void setTree(T rootData) // disable setTree (see Segment 25.6)
    {
        throw new UnsupportedOperationException();
    } // end setTree
    
    
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                        BinaryTreeInterface<T> rightTree)
    {
        throw new UnsupportedOperationException();
    } // end setTree
    
    
    public T getEntry(T entry)
    {
        return findEntry(getRootNode(), entry);
    } // end getEntry
    
    
    private T findEntry(BinaryNode<T> rootNode, T entry)
    {
        T result = null;
        if (rootNode != null)
        {
            T rootEntry = rootNode.getData();
            if (entry.equals(rootEntry))
                result = (T) rootEntry;
            else if (entry.compareTo(rootEntry) < 0)
                result = findEntry(rootNode.getLeftChild(), entry);
            else
                result = findEntry(rootNode.getRightChild(), entry);
        } // end if
        return result;
    } // end findEntry
    
    
    public boolean contains(T entry)
    {
        return getEntry(entry) != null;
    } // end contains
    
    
    public T add(T newEntry)
    {
        T result = null;
        
        if (isEmpty())
        {
            setRootNode(new BinaryNode<T>(newEntry));
            getRootNode().setParent(null);
        }
        else
            result = addEntry(newEntry);
        return result;
    } // end add
    
    /** Adds newEntry to the nonempty subtree rooted at rootNode. (non-recursive).
        @param newEntry An item to add to the search tree.
     */
    private T addEntry(T newEntry)
    {
        BinaryNode<T> currentNode = getRootNode();
        assert currentNode != null; 

        T result = null;
        boolean found = false;
        
        while (!found)
        {
            T currentEntry = currentNode.getData();
            int comparison = newEntry.compareTo(currentEntry);
            
            if (comparison == 0)
            { // newEntry matches currentEntry;
                // return and replace currentEntry
                found = true;
                result = currentEntry;
                currentNode.setData(newEntry);
            }
            else if (comparison < 0)
            {
                if (currentNode.hasLeftChild())
                {
                    currentNode = currentNode.getLeftChild();
                }
                else
                {
                    found = true;

                    BinaryNode<T> newNode = new BinaryNode<T>(newEntry);
                    currentNode.setLeftChild(newNode);
                    newNode.setParent(currentNode);

                    // Q thread: new node's successor is currentNode
                    newNode.setThread(currentNode);

                    // fix predecessor thread (ancestor that previously pointed to currentNode)
                    BinaryNode<T> ancestor = currentNode;
                    BinaryNode<T> parent = currentNode.getParent();

                    while (parent != null && parent.getLeftChild() == ancestor)
                    {
                        ancestor = parent;
                        parent = parent.getParent();
                    }

                    if (parent != null && parent.getRightChild() == ancestor)
                    {
                        parent.setThread(newNode);
                    }
                }
            }
            else
            {
                assert comparison > 0;
                if (currentNode.hasRightChild())
                {
                    currentNode = currentNode.getRightChild();
                }
                else
                {
                    found = true;

                    BinaryNode<T> newNode = new BinaryNode<T>(newEntry);
                    currentNode.setRightChild(newNode);
                    newNode.setParent(currentNode);

                    // thread: successor inherits currentNode's old thread
                    newNode.setThread(currentNode.getThread());

                    // currentNode now threads to newNode
                    currentNode.setThread(newNode);
                }
            }
        }
        
    return result;
    } // end addEntry
  
    
    
    
    
    /** Removes entry from the search tree (non-recursive).
     * @return The item if found, otherwise return null.
     */
    public T remove(T entry)
    {
        T result = null;
        boolean found = false;
        // Locate node (and its parent) that contains a match for entry
        NodePair pair = findNode(entry);
        BinaryNode<T> currentNode = pair.getFirst();
        BinaryNode<T> parentNode = pair.getSecond();
        
        if (currentNode != null) // Entry is found
        {
            result = currentNode.getData(); // Get entry to be removed
            // Case 1: currentNode has two children
            if (currentNode.hasLeftChild() && currentNode.hasRightChild())
            {
                pair = getNodeToRemove(currentNode);
                BinaryNode<T> nodeToRemove = pair.getFirst();
                parentNode = pair.getSecond();

                currentNode.setData(nodeToRemove.getData());
                currentNode = nodeToRemove;
            }
            
            removeNode(currentNode, parentNode);
        }
        return result;
    } // end remove

    
    private NodePair findNode(T entry)
    {
        BinaryNode<T> parentNode = null;
        BinaryNode<T> currentNode = getRootNode();
        
        NodePair result = new NodePair();
        boolean found = false;
        
        while (!found && currentNode != null)
        {
            T currentData = currentNode.getData();
            int comparison = entry.compareTo(currentData);
            
            if (comparison == 0)
            {
                found = true;
            }
            else if (comparison < 0)
            {
                parentNode = currentNode;
                currentNode = currentNode.getLeftChild();
            }
            else
            {
                parentNode = currentNode;
                currentNode = currentNode.getRightChild();
            }
        }

        if (found)
            result = new NodePair(currentNode, parentNode);
        return result;
    } // end findNode

    
    private NodePair getNodeToRemove(BinaryNode<T> currentNode)
    {
        BinaryNode<T> leftSubtreeRoot = currentNode.getLeftChild();
        BinaryNode<T> rightChild = leftSubtreeRoot;
        BinaryNode<T> priorNode = currentNode;
        
        while (rightChild.hasRightChild())
        {
            priorNode = rightChild;
            rightChild = rightChild.getRightChild();
        }
        
        return new NodePair(rightChild, priorNode);
    } // end getNodeToRemove
    

    private void removeNode(BinaryNode<T> nodeToRemove,
                            BinaryNode<T> parentNode)
    {
        BinaryNode<T> childNode;

        if (nodeToRemove.hasLeftChild())
        {
            childNode = nodeToRemove.getLeftChild();
        }
        else
        {
            childNode = nodeToRemove.getRightChild();
        }
            
        if (nodeToRemove == getRootNode())
        {
            setRootNode(childNode);
            if (childNode != null) childNode.setParent(null);
        }
        else if (parentNode.getLeftChild() == nodeToRemove)
        {
            parentNode.setLeftChild(childNode);
            if (childNode != null) childNode.setParent(parentNode);
        }
        else
        {
            parentNode.setRightChild(childNode);
            if (childNode != null) childNode.setParent(parentNode);
        }
    } // end removeNode

    
    
    private class NodePair
    {
        private BinaryNode<T> first;
        private BinaryNode<T> second;

        public NodePair()
        {
            first = null;
            second = null;
        }

        public NodePair(BinaryNode<T> myFirst, BinaryNode<T> mySecond)
        {
            first = myFirst;
            second = mySecond;
        }
        
        public BinaryNode<T> getFirst()
        {
            return first;
        }
        
        public BinaryNode<T> getSecond()
        {
            return second;
        }
    }
}