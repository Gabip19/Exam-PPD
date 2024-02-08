package parallelism;

import data_structures.SynchronizedMap;
import data_structures.SynchronizedQueue;
import domain.Entry;

public class ConsumerThread extends Thread {
    private final SynchronizedQueue<Entry> queue;
    private final SynchronizedMap resultList;
    private final int Tr;

    public ConsumerThread(SynchronizedQueue<Entry> queue, SynchronizedMap resultList, int tr) {
        this.queue = queue;
        this.resultList = resultList;
        this.Tr = tr;
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
        queue.startConsumer();

        while (true) {
            try {
                var entry = queue.dequeue();
//                System.out.println("Am scos: " + entry);
                waitTime(Tr);

                if (entry == null) {
                    break;
                }

                resultList.add(entry);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        queue.stopConsumer();
    }
}
