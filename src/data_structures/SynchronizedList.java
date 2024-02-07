package data_structures;

import domain.Cursant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedList<TEntry> {
    private final MyLinkedList<TEntry> linkedList;
    private final AtomicInteger activeProducers = new AtomicInteger(0);
    private final List<Cursant> underFive = new ArrayList<>();

    public SynchronizedList() {
        this.linkedList = new MyLinkedList<>();
    }

    public synchronized void add(TEntry entry) {
        linkedList.insertLast(entry);

        Cursant cursant = (Cursant) entry;
        if (cursant.getMedie() < 5) {
            synchronized (underFive) {
                underFive.add(cursant);
                underFive.notifyAll();
            }
        }
    }

    public synchronized int size() {
        return linkedList.getSize();
    }

    public void startProducer() {
        activeProducers.incrementAndGet();
    }

    public void stopProducer() {
        var value = activeProducers.decrementAndGet();
        if (value == 0) {
            synchronized (underFive) {
                underFive.notifyAll();
            }
        }
    }

    public void observe() {
        synchronized (underFive) {
            int lastSize = 0;

            do {
                try {
                    if (lastSize != underFive.size()) {
                        System.out.println("MEDIE SUB 5: ");
                        lastSize = underFive.size();

                        for (Cursant cursant : underFive) {
                            System.out.print(cursant + ", ");
                        }
                        System.out.println();
                    }

                    underFive.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (activeProducers.get() != 0);
        }
    }

    public List<TEntry> getEntriesAsList() {
        var entriesList = new ArrayList<TEntry>();

        synchronized (linkedList) {
            var tail = linkedList.getTail();
            var currentNode = linkedList.getFirstNode();

            while (currentNode != tail) {
                entriesList.add(currentNode.getEntry());
                currentNode = currentNode.getNext();
            }
        }

        return entriesList;
    }
}
