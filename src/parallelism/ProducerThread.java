package parallelism;

import data_structures.SynchronizedQueue;
import domain.Cursant;

public class ProducerThread extends Thread {
    private final SynchronizedQueue<Cursant> queue;

    public ProducerThread(SynchronizedQueue<Cursant> queue) {
        this.queue = queue;
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
        queue.startProducer();
//        var filePath = "input\\set" + setNum + "\\RezultateC" + countryNum + "_P" + problemNum + ".txt";
//        Scanner scanner;
//        try {
//            scanner = new Scanner(new FileReader(filePath));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        while (scanner.hasNextInt()) {
//            int id = scanner.nextInt();
//            int score = scanner.nextInt();
//
//            queue.enqueue(new ParticipantEntry(id, score, countryNum));
//        }
        queue.stopProducer();
    }
}
