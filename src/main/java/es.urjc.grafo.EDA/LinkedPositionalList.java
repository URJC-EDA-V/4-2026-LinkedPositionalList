package es.urjc.grafo.EDA;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of a positional list using a doubly linked list.
 * Provides methods to add, remove, and access elements by position.
 *
 * @param <E> the type of elements stored in the list
 */
public class LinkedPositionalList<E> implements PositionalList<E> {

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    /**
     * Constructs a new empty LinkedPositionalList.
     * The list is initialized with header and trailer sentinel nodes.
     */
    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNextNode(trailer);
    }

    /**
     * Validates that a given position is a proper node and is still in the list.
     *
     * @param p the position to validate
     * @return the node corresponding to the position
     * @throws IllegalArgumentException if the position is invalid or no longer in the list
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid position");
        Node<E> node = (Node<E>) p;
        if (node.getNextNode() == null) {
            throw new IllegalArgumentException("p is no longer in the list");
        }
        return node;
    }

    /**
     * Returns the position corresponding to a given node, or null if it is a sentinel.
     *
     * @param node the node to convert
     * @return the position, or null if node is header or trailer
     */
    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) {
            return null;
        }
        return node;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the list contains no elements.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Returns the first position in the list, or null if empty.
     *
     * @return the first position, or null if the list is empty
     */
    public Position<E> first() {
        return position(header.getNextNode());
    }

    /**
     * Returns the last position in the list, or null if empty.
     *
     * @return the last position, or null if the list is empty
     * @throws UnsupportedOperationException if not implemented
     */
    public Position<E> last() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the position before the given position, or null if p is first.
     *
     * @param p the position to search before
     * @return the position before p, or null if p is first
     * @throws IllegalArgumentException if p is invalid
     */
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPreviousNode());
    }

    /**
     * Returns the position after the given position, or null if p is last.
     *
     * @param p the position to search after
     * @return the position after p, or null if p is last
     */
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Adds an element between two given nodes and returns its position.
     *
     * @param e the element to add
     * @param predecessor the node before the new element
     * @param successor the node after the new element
     * @return the position of the new element
     */
    private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Inserts an element at the front of the list and returns its position.
     *
     * @param e the element to add
     * @return the position of the new element
     */
    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNextNode());
    }

    /**
     * Inserts an element at the end of the list and returns its position.
     *
     * @param e the element to add
     * @return the position of the new element
     * @throws UnsupportedOperationException if not implemented
     */
    public Position<E> addLast(E e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Inserts an element before the given position and returns its position.
     *
     * @param p the position before which to insert
     * @param e the element to add
     * @return the position of the new element
     * @throws IllegalArgumentException if p is invalid
     */
    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPreviousNode(), node);
    }

    /**
     * Inserts an element after the given position and returns its position.
     *
     * @param p the position after which to insert
     * @param e the element to add
     * @return the position of the new element
     * @throws UnsupportedOperationException if not implemented
     */
    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Replaces the element at the given position and returns the replaced element.
     *
     * @param p the position whose element is to be replaced
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is invalid
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E answer = node.getElement();
        node.setElement(e);
        return answer;
    }

    /**
     * Removes and returns the element at the given position.
     *
     * @param p the position to remove
     * @return the removed element
     * @throws IllegalArgumentException if p is invalid
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPreviousNode();
        Node<E> successor = node.getNextNode();
        predecessor.setNextNode(successor);
        successor.setPreviousNode(predecessor);
        size--;
        E removedElement = node.getElement();
        node.setElement(null); // help with garbage collection
        node.setNextNode(null);
        node.setPreviousNode(null);
        return removedElement;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return a string representation of the list
     */
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        StringBuilder result = new StringBuilder();
        result.append("[");
        Node<E> currentNode = header.getNextNode();
        while (currentNode.getNextNode() != trailer) {
            result.append(currentNode.getElement());
            currentNode = currentNode.getNextNode();
            if (currentNode.getNextNode() != trailer) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    /**
     * Returns an iterable representation of the list's positions.
     *
     * @return an iterable of positions in the list
     */
    public Iterable<Position<E>> positions() {
        return new PositionIterable(); // create a new instance of the inner class
    }

    /**
     * Returns an iterable representation of the list's elements.
     *
     * @return an iterable of elements in the list
     */
    public Iterable<E> elements() {
        return new ElementIterable(); // create a new instance of the inner class
    }

    /**
     * Returns an iterator of the positions stored in the list.
     *
     * @return an iterator of positions in the list
     */
    public Iterator<Position<E>> positionIterator() {
        return new PositionIterator();
    }

    /**
     * Returns an iterator of the elements stored in the list.
     *
     * @return an iterator of elements in the list
     */
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Node of a doubly linked list, which implements Position<E>.
     *
     * @param <E> the type of element stored
     */
    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> previousNode;
        private Node<E> nextNode;

        /**
         * Creates a node with the given element and next node.
         *
         * @param element      the element to be stored in the node
         * @param previousNode the previous node in the list
         * @param nextNode     the next node in the list
         */
        public Node(E element, Node<E> previousNode, Node<E> nextNode) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Returns the element stored in the node.
         *
         * @return the element stored in the node
         * @throws IllegalStateException if the node is no longer valid
         */
        public E getElement() throws IllegalStateException {
            if (nextNode == null) {
                throw new IllegalStateException("Position no longer valid");
            }
            return element;
        }

        /**
         * Sets the element stored in the node.
         * @param e the element to set
         */
        public void setElement(E e) {
            element = e;
        }

        /**
         * Returns the previous node in the list.
         *
         * @return the previous node in the list
         */
        public Node<E> getPreviousNode() {
            return previousNode;
        }

        /**
         * Sets the previous node in the list.
         *
         * @param p the new previous node
         */
        public void setPreviousNode(Node<E> p) {
            previousNode = p;
        }

        /**
         * Returns the next node in the list.
         *
         * @return the next node in the list
         */
        public Node<E> getNextNode() {
            return nextNode;
        }

        /**
         * Sets the next node in the list.
         *
         * @param n the new next node
         */
        public void setNextNode(Node<E> n) {
            nextNode = n;
        }
    }

    /**
     * Iterator for the positions in the list.
     */
    private class PositionIterator implements Iterator<Position<E>> {
        private Position<E> cursor = first(); // position of the next element to report
        private Position<E> recent = null; // position of last reported element

        /**
         * Tests whether the iterator has a next object.
         *
         * @return true if there is a next position, false otherwise
         */
        public boolean hasNext() {
            return (cursor != null);
        }

        /**
         * Returns the next position in the iterator.
         *
         * @return the next position
         * @throws NoSuchElementException if there are no more positions
         */
        public Position<E> next() throws NoSuchElementException {
            if (cursor == null) throw new NoSuchElementException("nothing left");
            recent = cursor; // element at this position might later be removed
            cursor = after(cursor);
            return recent;
        }

        /**
         * Removes the element returned by most recent call to next.
         *
         * @throws IllegalStateException if next has not yet been called or remove was already called after the last next
         */
        public void remove() throws IllegalStateException {
            if (recent == null) throw new IllegalStateException("nothing to remove");
            LinkedPositionalList.this.remove(recent); // remove from outer list
            recent = null; // do not allow remove again until next is called
        }
    }

    /**
     * Iterable for the positions in the list.
     */
    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() {
            return new PositionIterator();
        }
    }

    /**
     * Iterable for the elements in the list.
     */
    private class ElementIterable implements Iterable<E> {
        public Iterator<E> iterator() {
            return new ElementIterator();
        }
    }

    /**
     * Iterator for the elements in the list.
     */
    private class ElementIterator implements Iterator<E> {

        /**
         * Returns true if there are more elements to iterate over.
         *
         * @return true if there is a next element, false otherwise
         * @throws UnsupportedOperationException if not implemented
         */
        public boolean hasNext() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element
         * @throws UnsupportedOperationException if not implemented
         */
        public E next() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
