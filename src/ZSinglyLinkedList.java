/**
 * Data Structure: Singly Linked List
 * File: ZSinglyLinkedList.java
 * Author: Zack Sai
 *
 * This file demonstrates the Singly Linked List data structure
 * Best Case Efficiency:
 *      get: O(1) – get at front
 *      set: O(1) – set at front
 *      remove: O(1) – remove at front
 *
 * Worst Case Efficiency:
 *      get: O(n) – retrieves at position passed (n)
 *      set: O(n) – stores at position passed (n)
 *      remove: O(n) – removes from position passed (n)
 *
 * Reallocate() method is used to expand the array when it is full and an
 * element must be added
 *
 * @param <E> generic data type of the list created
 */
public class ZSinglyLinkedList<E> {

    // First, the inner class node will be declared:
    // Then, a declaration of the properties and behaviors of the single linked list

    /**
     * Inner class node is the backbone of the Single Linked List data structure
     * A node contains a piece of data (Entry) and a link to the next node (nextNode)
     * A node must contain an entry and can be initialized with the constructors below
     *
     * @param <E> generic data type the node will contain
     */
    private static class Node<E> {

        // Properties: a node contains an entry and a link to the next entry
        private E entry;
        private Node<E> nextNode;

        // Behaviors:

        /**
         * 1-param constructor initializes a node to a passed entry value
         * Links this entry to a null next entry
         *
         * @param entryValue
         */
        private Node(E entryValue) {
            entry = entryValue;
            nextNode = null;
        }

        /**
         * 2-param constructor initializes a node with an entry value
         * Links this entry to the given linkedNode
         *
         * @param entryValue
         */
        private Node(E entryValue, Node<E> newNextNode) {
            entry = entryValue;
            nextNode = newNextNode;
        }

    }   // End inner class Node, Resume ZSingleLinkedList class:

    // Properties: a singly-linked list class has a head node (head) and a size (currentSize)
    private Node<E> head = null;
    private int currentSize = 0;

    /**
     * addFirst adds a node to the beginning of the list
     * head is initialized as a new node with the entry value passed and a next entry of the previous head
     * this keeps the rest of the list intact bc the previous head is linked to all the other nodes and is now
     * the second node
     *
     * @param entryValue
     */
    public void addFirst(E entryValue) {

        // Make the head a new node with the given value, pointing to the previous head
        head = new Node<>(entryValue, head);

        // Update size
        currentSize++;
    }

    /**
     * addAfter adds a node after a given node passed
     *
     * @param node       is the node after which to add this new entry value
     * @param entryValue is the value of the new node to be added
     */
    private void addAfter(Node<E> node, E entryValue) {

        // Take the given node, set its next node to a new node with the given value pointing to the prior next node
        node.nextNode = new Node<>(entryValue, node.nextNode);

        // Update size
        currentSize++;
    }

    /**
     * removeAfter method removes the node that follows a given node
     *
     * @param givenNode is the node to be removed after
     * @return the entry of the node to be removed
     */
    private E removeAfter(Node<E> givenNode) {

        // First, store the node to be removed (it's the given node's next node)
        Node<E> toBeRemoved = givenNode.nextNode;

        // Next, check if end of list has been reached (in which case there's nothing to remove, so return null_
        if (toBeRemoved == null) return null;

        // Otherwise, simply connect the given node to the following node (the one after the one being removed)
        givenNode.nextNode = toBeRemoved.nextNode;

        // Update size and return the stored entry
        currentSize--;
        return toBeRemoved.entry;
    }


    /**
     * removeFirst removes the head node from the list
     * Handles case of empty list
     *
     * @return the value of the head node
     */
    private E removeFirst() {

        // If list is empty, simply return null
        if (head == null) return null;

        // Otherwise, store the node to be removed so you can return it later
        Node<E> toBeRemoved = head;

        // Next, make the head it's next node, then pdate size and return the stored entry
        head = head.nextNode;
        currentSize--;
        return toBeRemoved.entry;
    }

    /**
     * getNode returns node at a given index
     *
     * @param index the position of the node being returned
     * @return the node at the index
     */
    private Node<E> getNode(int index) {

        // First, start at head node by storing it into new currentNode
        Node<E> currentNode = head;

        // Iterate through the list until you reach the index (or a null node signaling end of the list)
        for (int i = 0; i < index && currentNode != null; i++) {
            currentNode = currentNode.nextNode;
        }

        // currentNode is now at the index given, so return it!
        return currentNode;
    }

    /**
     * set method sets the entry of a given node to a new value
     *
     * @param index    is the index of the desired node
     * @param newValue is the new value to be stored in the desired node
     * @return the value that was previously in the desired node
     */
    public E set(int index, E newValue) {

        // First, ensure index is legal
        if (index < 0 || index >= currentSize) throw new IndexOutOfBoundsException(Integer.toString(index));

        // Now, find the node using get() method
        Node<E> desiredNode = getNode(index);

        // Store the data in that node to be returned in a moment
        E returnValue = desiredNode.entry;

        // Update the entry of the desired node
        desiredNode.entry = newValue;

        // Return the value that we overrode
        return returnValue;

    }

    /**
     * 2-param add method adds a new node at a given index
     *
     * @param index      is the position at which to add
     * @param entryValue is the value of the new node to be added
     */
    public void add(int index, E entryValue) {

        // First, ensure index is legal
        if (index < 0 || index >= currentSize) throw new IndexOutOfBoundsException(Integer.toString(index));

        // Next, if item is at the beginning of the list just use addFirst
        if (index == 0) addFirst(entryValue);
        else {
            // Otherwise, use addAfter to add after the node just before the given index
            addAfter(getNode(index - 1), entryValue);
        }

    }

    /**
     * this add method appends a new node with a given value to the end of the lis t
     *
     * @param entryValue is the value of the new node to be added
     * @return true upon successful add (always)
     */
    public boolean add(E entryValue) {

        add(currentSize, entryValue);
        return true;
    }

    /**
     * size method iterates through the list to tally the number of nodes
     * alternately, you can just return currentSize
     *
     * @return total nodes in the list
     */
    public int size() {

        //  OPTIONAl: return currentSize;

        // First, create a returnValue that starts at 0
        int nodesCounted = 0;

        // First, start at head node by storing it into new currentNode
        Node<E> currentNode = head;

        // Iterate through the list until you reach the end of the list
        for (int i = 0; currentNode != null; i++) {

            // Increment nodesCounted each iteration
            nodesCounted++;
            currentNode = currentNode.nextNode;
        }

        // Return the nodesCounted
        return nodesCounted;
    }

    /**
     * indexOf finds the first occurrence of an entry
     *
     * @param targetEntry is the value being searched for
     * @return the position of the entry
     */
    public int indexOf(E targetEntry) {

        // First, create a position value that starts at 0
        int position = 0;

        // Also, start at head node by storing it into new currentNode
        Node<E> currentNode = head;

        // Iterate through the list until you reach the node that contains the entry or the end of the list
        for (int i = 0; currentNode.entry != targetEntry && currentNode != null; i++) {

            // Increment position each iteration
            position++;
            currentNode = currentNode.nextNode;
        }

        // Return the position of the entry in the list
        return position;

    }

    /**
     * remove method removes the node at a given index and returns its entry value
     *
     * @param index is the position of the node to be removed
     * @return the entry value in the node removed
     */
    public E remove(int index) {

        // Get the node just before this index and remove the node after it
        // Remember, removeAfter will return the entry of the node removed
        return removeAfter(getNode(index - 1));
    }
}






































