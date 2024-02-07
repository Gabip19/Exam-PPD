package parallelism;

import data_structures.SynchronizedList;
import domain.Cursant;

public class Manager extends Thread {
    private final SynchronizedList<Cursant> list;

    public Manager(SynchronizedList<Cursant> list) {
        this.list = list;
    }

    @Override
    public void run() {
        list.observe();

        System.out.println("FINAL:");
        for (var cursant : list.getEntriesAsList()) {
            System.out.println(cursant + ", ");
        }
    }
}
