package parallelism;

import data_structures.SynchronizedList;
import data_structures.SynchronizedQueue;
import domain.ParticipantEntry;

public class ConsumerThread extends Thread {
    private final SynchronizedQueue<ParticipantEntry> queue;
    private final SynchronizedList<ParticipantEntry> resultList;

    public ConsumerThread(SynchronizedQueue<ParticipantEntry> queue, SynchronizedList<ParticipantEntry> resultList) {
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
