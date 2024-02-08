package parallelism;

import data_structures.SynchronizedQueue;
import domain.Entry;

import java.util.Random;

public class ProducerThread extends Thread {
    private final SynchronizedQueue<Entry> queue1;
    private final SynchronizedQueue<Entry> queue2;
    private final SynchronizedQueue<Entry> queue3;
    private final int id;
    private final int Ta;

    public ProducerThread(int id, int Ta, SynchronizedQueue<Entry> queue1, SynchronizedQueue<Entry> queue2, SynchronizedQueue<Entry> queue3) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
        this.id = id;
        this.Ta = Ta;
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
        queue1.startProducer();
        queue2.startProducer();
        queue3.startProducer();

        for (int i = 0; i < 100; i++) {
            Random random = new Random();

            var idApel = id * 100 + random.nextInt(1, 100);
            var dificultate = random.nextInt(1, 4);

            if (dificultate == 1) {
                queue1.enqueue(new Entry(id, idApel, dificultate));
            } else if (dificultate == 2) {
                queue2.enqueue(new Entry(id, idApel, dificultate));
            } else if (dificultate == 3) {
                queue3.enqueue(new Entry(id, idApel, dificultate));
            }

//            System.out.println("ID: " + id + " apel: " + idApel + " diff: " + dificultate);
            waitTime(Ta);
        }

        queue1.stopProducer();
        queue2.stopProducer();
        queue3.stopProducer();
    }
}
