import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node prevNode;
        private Node nextNode;
    }

    private Node firstNode = null, lastNode = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

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

        Node oldFirstNode = this.firstNode;
        firstNode = new Node();
        firstNode.item = item;

        if(this.isEmpty()) {
            this.lastNode = firstNode;
        } else {
            oldFirstNode.prevNode = firstNode;
            firstNode.nextNode = oldFirstNode;
            firstNode.prevNode = null;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();

        Node oldLastNode = this.lastNode;
        lastNode = new Node();
        lastNode.item = item;

        if(this.isEmpty()) {
            this.firstNode = lastNode;
        } else {
            oldLastNode.nextNode = lastNode;
            lastNode.nextNode = null;
            lastNode.prevNode = oldLastNode;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();

        Item item = firstNode.item;
        this.size--;

        if (!this.isEmpty()) {
            this.firstNode = firstNode.nextNode;
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
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Item> {
        private Node currentNode = firstNode;

        @Override
        public boolean hasNext() {
            return this.currentNode != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();

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
