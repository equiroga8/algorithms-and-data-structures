
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first = null;
    private Node last = null;

    public Deque() {
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (this.isEmpty()) {
            emptyInsert(item);
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            first.previous = null;
            oldFirst.previous = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (this.isEmpty()) {
            emptyInsert(item);
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        if (first.previous != null) {
            first.previous = null;
        }
        first = first.next;
        if (size == 1) last = null;
        size--;
        return item;
    }


    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        if (last.next != null) {
            last.next = null;
        }
        last = last.previous;
        if (size == 1) first = null;
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove method is not supported");
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException("No more items");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void emptyInsert(Item item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.previous = null;
        first = newNode;
        last = newNode;
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        String hello = "hello";
        String world = "world";
        String my = "my";
        deque.addLast(hello);
        System.out.println(deque.size);
        System.out.println(deque.first.item);
        System.out.println(deque.last.item);
        deque.removeLast();
        System.out.println(deque.size);
        System.out.println(deque.first.item);
        System.out.println(deque.last.item);
        System.out.println();
        Iterator<String> it = deque.iterator();
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());


    }
}
