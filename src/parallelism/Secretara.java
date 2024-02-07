package parallelism;

import data_structures.SynchronizedList;
import domain.Cursant;

import java.util.List;

public class Secretara extends Thread {
    private final SynchronizedList<Cursant> list;
    private final List<Cursant> cursants;
    private final int startIndex;
    private final int endIndex;

    public Secretara(SynchronizedList<Cursant> list, List<Cursant> cursants, int startIndex, int endIndex) {
        this.list = list;
        this.cursants = cursants;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
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
        list.startProducer();

        System.out.println("started");

        for (int i = startIndex; i < endIndex; i++) {
            list.add(cursants.get(i));
            waitTime(20);
        }

        list.stopProducer();
    }
}
