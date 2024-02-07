package parallelism;

import data_structures.SynchronizedList;
import data_structures.SynchronizedQueue;
import domain.Cursant;

public class ConsumerThread extends Thread {
    private final SynchronizedQueue<Cursant> queue;
    private final SynchronizedList<Cursant> resultList;

    public ConsumerThread(SynchronizedQueue<Cursant> queue, SynchronizedList<Cursant> resultList) {
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
