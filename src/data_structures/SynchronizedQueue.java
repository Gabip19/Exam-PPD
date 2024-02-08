package data_structures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedQueue<TEntry> {
    private final Queue<TEntry> queue = new LinkedList<>();
    private int activeConsumers = 0;
    private int activeProducers = 0;
    private int size = 0;
    private final int maxSize;
    private final Object locker = new Object();

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public SynchronizedQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void enqueue(TEntry element) {
        lock.lock();
        try {
            while (size == maxSize && activeConsumers != 0) {
                notFull.await();
            }

            if (activeConsumers == 0) {
                return;
            }

            queue.offer(element);
            size++;
//            System.out.println("size: " + size);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public TEntry dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty() && activeProducers != 0) {
                notEmpty.await();
                if (queue.isEmpty() && activeProducers == 0) {
                    return null;
                }
            }

            var elem = queue.poll();
            size--;
            notFull.signalAll();

            return elem;
        } finally {
            lock.unlock();
        }
    }

    public void startProducer() {
        lock.lock();
        activeProducers++;
        lock.unlock();
    }

    public void stopProducer() {
        lock.lock();
        activeProducers--;
        if (activeProducers == 0) {
            notEmpty.signalAll();
        }
        lock.unlock();
    }

    public void startConsumer() {
        lock.lock();
        activeConsumers++;
        lock.unlock();
    }

    public void stopConsumer() {
        lock.lock();
        activeConsumers--;
        if (activeConsumers == 0) {
            synchronized (locker) {
                locker.notifyAll();
            }
        }
        lock.unlock();
    }

    public int getSize() {
        lock.lock();
        var value = 0;
        if (activeConsumers == 0 && activeProducers == 0) {
            value = -1;
        } else {
            value = size;
        }
        lock.unlock();
        return value;
    }

    public void awaitTermination() throws InterruptedException {
        synchronized (locker) {
            if (activeConsumers != 0 && activeProducers != 0) {
                locker.wait();
            }
        }
    }
}
