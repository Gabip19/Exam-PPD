package parallelism;

import data_structures.SynchronizedList;
import data_structures.SynchronizedQueue;
import domain.Entry;

public class ConsumerThread extends Thread {
    private final SynchronizedQueue<Entry> queue;
    private final SynchronizedList<Entry> resultList;

    public ConsumerThread(SynchronizedQueue<Entry> queue, SynchronizedList<Entry> resultList) {
        this.queue = queue;
        this.resultList = resultList;
    }

    @Override
    public void run() {
        queue.startConsumer();

        while (true) {
            try {
                var entry = queue.dequeue();

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
