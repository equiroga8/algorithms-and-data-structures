import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        int commandArg = Integer.parseInt(args[0]);

        while (true) {
            try {
                String input = StdIn.readString();
                queue.enqueue(input);
            }
            catch (NoSuchElementException e) {
                break;
            }
        }

        for (int i = 0; i < commandArg; i++) {
            System.out.println(queue.dequeue());
        }


    }
}
