package parallelism;

import data_structures.SynchronizedMap;
import data_structures.SynchronizedQueue;
import domain.Entry;

import java.util.Random;

public class SupervisorThread extends Thread {
    private final SynchronizedQueue<Entry> queue1;
    private final SynchronizedQueue<Entry> queue2;
    private final SynchronizedQueue<Entry> queue3;
    private final SynchronizedMap map;
    private int Tv;

    public SupervisorThread(int Tv, SynchronizedQueue<Entry> queue1, SynchronizedQueue<Entry> queue2, SynchronizedQueue<Entry> queue3, SynchronizedMap map) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
        this.map = map;
        this.Tv = Tv;
    }

    private void waitTime(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("Thread could not sleep.");
        }
    }

    @Override
    public void run() {
        while (true) {
            var size1 = queue1.getSize();
            var size2 = queue2.getSize();
            var size3 = queue3.getSize();

            if (size1 == -1 && size2 == -1 && size3 == -1) {
                break;
            }

            size1 = size1 == -1 ? 0 : size1;
            size2 = size2 == -1 ? 0 : size2;
            size3 = size3 == -1 ? 0 : size3;

            System.out.println("1: " + size1 + " 2: " + size2 + " 3: " + size3);
            waitTime(3);
        }
    }
}
