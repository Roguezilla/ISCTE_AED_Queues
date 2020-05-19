import java.util.Iterator;
import java.util.NoSuchElementException;

//import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[])(new Object[2]);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size() == 0;
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
        1. set the value at idx to the value of the last item in the array
        2. set the item at n - 1 to null as we moved to another place
        3. decrement the size of our queue
        */
        this.items[idx] = this.items[n - 1];
        this.items[n - 1] = null;

        this.n--;

        //shrinking an array to half when it's 1/4 full is the best and the most efficient way to preserve memory or something
        //also check if size if bigger than 0 as 0 == 0/4
        if (this.n > 0 && this.n == this.items.length / 4) {
            resize(this.items.length / 2);
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

        this.items = enlarged;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQueueIterator();
    }

    private class RQueueIterator implements Iterator<Item> {
        private Item[] iteratorItems = (Item[])(new Object[n]);
        private int iteratorN = n;

        public RQueueIterator() {
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
    }
}
