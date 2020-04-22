import java.util.Iterator;
import java.util.NoSuchElementException;

//import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[])(new Object[10]);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        //resize the array if we reached max capacity
        if (this.n == this.items.length) {
            this.resize(this.items.length * 2);
        }

        this.items[this.n] = item;
        this.n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        //get random index from [0, n[ ensuring the random item we are dequeuing is null
        int idx = StdRandom.uniform(this.n);
        //save the item at the random index
        Item returned = this.items[idx];

        /*
        1. decrement n as we want the last non null item from the queue array
        2. as we saved the item at idx we can override the item that position with the item at n,
            this way we dont have nulls in the range of [0, n[
        3. set the item at n to null, not really needed as we work in the range of [0, n[
        */
        this.n--;
        this.items[idx] = this.items[this.n];
        this.items[n] = null;

        //shrinking an arrays to half when they are 1/4 full is the best is the most efficient way to preserve memory
        if (this.n == this.items.length / 4) {
            resize(items.length / 2);
        }

        return returned;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        return this.items[StdRandom.uniform(this.n)];
    }

    private void resize(int capacity) {
        Item[] enlarged = (Item[])(new Object[capacity]);

        for (int i = 0; i < this.n; i++) {
            enlarged[i] = this.items[i];
        }

        items = enlarged;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Item> {
        private Item[] iteratorItems = (Item[])(new Object[n]);
        private int iteratorN = n;

        public CustomIterator() {
            /*
                "The order of two or more iterators to the same randomized queue must be mutually independent;
                each iterator must maintain its own random order."
            */
            for (int i = 0; i < n; i++) {
                this.iteratorItems[i] = items[i];
            }

            StdRandom.shuffle(iteratorItems);
        }

        @Override
        public boolean hasNext() {
            return this.iteratorN != 0;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();

            this.iteratorN--;
            return this.iteratorItems[this.iteratorN];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        System.out.println(rq.dequeue());
        System.out.println(rq.sample());
        rq.forEach(System.out::print);
        System.out.println(rq.size());
        rq.dequeue();
        System.out.println(rq.iterator().hasNext());
        rq.dequeue();
        System.out.println(rq.iterator().hasNext());
    }
}