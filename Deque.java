import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item  = null;
        private Node prevNode = null;
        private Node nextNode = null;
    }

    private Node firstNode;
    private Node lastNode;
    private int size;

    // construct an empty deque
    public Deque() {
        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();

        /*
        1.  save current first node
        2.  create a new node in the place of the current first node
        3.  set the item for the new first node
        4.1 if the queue is empty then set the last node to be equal to the first node
        4.2 if the queue isnt empty then set the new first node as the previous node for the old first node
            and set the old first node as the next node for the the new first node
        5.  increment the size of the queue
        */
        Node oldFirstNode = this.firstNode;
        firstNode = new Node();
        firstNode.item = item;

        if(this.isEmpty()) {
            this.lastNode = firstNode;
        } else {
            oldFirstNode.prevNode = firstNode;
            firstNode.nextNode = oldFirstNode;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();

        /*
        1.  save the old last node
        2.  create a new node in the place of the last node
        3.  set the item for the new last node
        4.1 if the queue is empty then set the first node to be equal to the new last node
        4.2 if the queue isnt empty then set the new last node as the next node for the old last node and
            set the old last node as the previous node for the new last node
        5.  increment the of the queue
        */
        Node oldLastNode = this.lastNode;
        lastNode = new Node();
        lastNode.item = item;

        if(this.isEmpty()) {
            this.firstNode = lastNode;
        } else {
            oldLastNode.nextNode = lastNode;
            lastNode.prevNode = oldLastNode;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();

        /*
        1.  save the item of the first node as we will have to remove it
        2.  decrement the size of the queue
        3.1 if the queue isnt empty then we set the current first node to its next node and now that
            our first node is the next node of the old first node we delete the old first node by
            setting the previous node of the current first node to null
        3.2 if the queue is empty then we delete both nodes by setting them to null
        */
        Item item = this.firstNode.item;
        this.size--;

        if (!this.isEmpty()) {
            this.firstNode = this.firstNode.nextNode;
            this.firstNode.prevNode = null;
        } else {
            this.firstNode = null;
            this.lastNode = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();

        /*
        1.  save the item of the last node as we will have to remove it
        2.  decrement the size of the queue
        3.1 if the queue isnt empty then we set the current last node to its previous node and now that
            our last node is the previous node of the old last node we delete the old last node by
            setting the next node of the current last node to null
        3.2 if the queue is empty then we delete both nodes by setting them to null
        */
        Item item = lastNode.item;
        this.size--;

        if (!this.isEmpty()) {
            this.lastNode = lastNode.prevNode;
            this.lastNode.nextNode = null;
        } else {
            this.firstNode = null;
            this.lastNode = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = firstNode;

        @Override
        public boolean hasNext() {
            return this.currentNode != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();

            /*
            1. save the current node's item
            2. advance to the next node available
            */
            Item item = this.currentNode.item;

            this.currentNode = this.currentNode.nextNode;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // testing (required)
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();

        d.addFirst("1");
        d.addFirst("3");
        d.addFirst("2");
        d.addLast("5");
        d.addLast("10");
        d.forEach(System.out::println);
        System.out.println("-----");
        System.out.println("Removed: " + d.removeFirst());
        d.forEach(System.out::println);
        System.out.println("-----");
        System.out.println("Removed: " + d.removeFirst());
        System.out.println("Removed: " + d.removeLast());
        d.forEach(System.out::println);
    }
}
