import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[])(new Object[1]);
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

        if (!this.isEmpty()) {
            if (this.n == this.items.length) {
                this.resize(this.items.length * 2);
            }

        }

        this.items[this.n] = item;
        this.n++;
    }
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int idx = StdRandom.uniform(this.n);
        Item returned = this.items[idx];

        this.n--;
        this.items[idx] = this.items[this.n];
        this.items[n] = null;

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
            for (int i = 0; i < n; i++) {
                this.iteratorItems[i] = items[i];
            }

            StdRandom.shuffle(iteratorItems);
        }

        @Override
        public boolean hasNext() {
            return this.iteratorN > 0;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) throw new NoSuchElementException();

            return this.iteratorItems[--this.iteratorN];
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
    }
}