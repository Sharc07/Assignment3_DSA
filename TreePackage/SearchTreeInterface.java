package TreePackage;

import java.util.Iterator;

/** An interface for the ADT Search Tree.
 *
 * A search tree stores comparable elements in a structure
 * that allows efficient searching, insertion, and removal.
 */
public interface SearchTreeInterface<T extends Comparable<? super T>>
                extends TreeInterface<T>
{
    /** Searches for a specific entry in this tree.
     * @param entry An object to be found.
     * @return True if the object was found in the tree.
     */
    public boolean contains(T entry);
        
    /** Retrieves a specific entry in this tree.
     * @param entry An object to be found.
     * @return Either the object that was found in the tree or
     *         null if no such object exists.
     */  
    public T getEntry(T entry);
        
    /** Adds a new entry to this tree. If the entry already exists,
     *  replaces the existing entry with the new one.
     * @param newEntry An object to be added to the tree.
     * @return Either null if newEntry was not already in the tree,
     *         or the existing entry that was replaced.
     */
    public T add(T newEntry);
        
    /** Removes a specific entry from this tree.
     * @param entry An object to be removed.
     * @return Either the object that was removed from the tree or
     *         null if no such object exists.
     */
    public T remove(T entry);
}