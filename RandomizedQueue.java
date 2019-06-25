import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size = 0;

    public RandomizedQueue(int capacicty) {
        queue = (Item[]) new Object[capacicty];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (size == queue.length) resize(2 * queue.length);
        queue[size++] = item;
    }

    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException("queue is empty");
        int index = StdRandom.uniform(size);
        Item item = queue[index];
        for (int i = index; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }
        queue[size - 1] = null;
        size--;
        if (size > 0 && size == queue.length / 4) resize(queue.length / 2);
        return item;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException("queue is empty");
        int index = StdRandom.uniform(size);
        return queue[index];
    }

    private void resize(int capacicty) {
        Item[] queueCopy = (Item[]) new Object[capacicty];
        for (int i = 0; i < size; i++) {
            queueCopy[i] = queue[i];
        }
        queue = queueCopy;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    private class ListIterator implements Iterator<Item> {

        public Item[] shuffledQueue = queue;
        public int current = 0;

        public ListIterator() {
            StdRandom.shuffle(shuffledQueue);
        }

        public boolean hasNext() {
            return current != size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove method is not supported");
        }

        public Item next() {
            if (current == size) throw new NoSuchElementException("No more items");
            Item item = shuffledQueue[current];
            current++;
            return item;

        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rQueue = new RandomizedQueue<>(2);
        String hello = "hello";
        String world = "world";
        String my = "my";
        rQueue.enqueue(world);
        rQueue.enqueue(hello);
        rQueue.enqueue(my);
        System.out.println(rQueue.sample());
        System.out.println(rQueue.sample());
        System.out.println(rQueue.sample());
        System.out.println(rQueue.size);
        System.out.println();
        System.out.println(Arrays.toString(rQueue.queue));
        System.out.println();
        Iterator<String> it = rQueue.iterator();
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());


    }
}
