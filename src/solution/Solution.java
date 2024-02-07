package solution;

import data_structures.SynchronizedList;
import data_structures.SynchronizedQueue;
import domain.ParticipantEntry;
import parallelism.ConsumerThread;
import parallelism.ProducerThread;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Solution {
    private static int p;
    private static int producersNum;
    private static int consumersNum;
    private static int maxQueueSize;
    private static List<ParticipantEntry> results;

    public static void writeResultsToFile(List<ParticipantEntry> resultsList, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        resultsList.forEach(entry -> {
            try {
                writer.write(String.valueOf(entry.getId()));
                writer.write(' ');
                writer.write(String.valueOf(entry.getScore()));
                writer.write(' ');
                writer.write(String.valueOf(entry.getCountryNum()));
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        writer.close();
    }

    private static void compareResultsWithSequential() throws FileNotFoundException {
        var filePath = "output\\Clasament_seq.txt";
        Scanner scanner = new Scanner(new FileReader(filePath));

        for (var entry : results) {
            int id = scanner.nextInt();
            int score = scanner.nextInt();

            if (entry.getId() != id || entry.getScore() != score) {
                System.err.println("Results do not match.");
                return;
            }
        }
    }

    private static void runParallel() throws InterruptedException, IOException {
        Thread[] producers = new Thread[producersNum];
        Thread[] consumers = new Thread[consumersNum];

        SynchronizedList<ParticipantEntry> resultsList = new SynchronizedList<>();
        SynchronizedQueue<ParticipantEntry> queue = new SynchronizedQueue<>(maxQueueSize);

        for (int i = 0; i < producersNum; i++) {
            producers[i] = new ProducerThread(queue);
        }

        for (int i = 0; i < consumersNum; i++) {
            consumers[i] = new ConsumerThread(queue, resultsList);
        }

        for (int i = 0; i < producersNum; i++) {
            producers[i].join();
        }

        for (int i = 0; i < consumersNum; i++) {
            consumers[i].join();
        }

//        if (success) {
//            results = resultsList.getEntriesAsList();
//            results.sort((t1, t2) -> {
//                if (t1.getScore() == t2.getScore()) {
//                    return t1.getId() - t2.getId();
//                } else if (t1.getScore() < t2.getScore()) {
//                    return 1;
//                }
//                return -1;
//            });
//            writeResultsToFile(results, "output\\Clasament.txt");
//        }
    }

//    private static ExecutorService startReadTasksExecutor(int noProducerThreads, SynchronizedQueue queue) {
//        var executor = Executors.newFixedThreadPool(noProducerThreads);
//
//        for (int i = 1; i <= countriesNumber; i++) {
//            for (int j = 1; j <= problemsNumber; j++) {
//                Runnable task = new ReadTask(i, j, setNum, queue);
//                executor.execute(task);
//            }
//        }
//
//        executor.shutdown();
//        return executor;
//    }

//    private static void startConsumerThreads(int noConsumerThreads, Thread[] threads, SynchronizedQueue queue, RankingList resultsList) {
//        for (int i = 0; i < noConsumerThreads; i++) {
//            threads[i] = new ProcessingThread(queue, resultsList);
//            threads[i].start();
//        }
//    }

    public static void Solve(String[] args) throws IOException, InterruptedException {
        int maxQueueSize, producersNum, consumersNum;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Max queue size: ");
        maxQueueSize = scanner.nextInt();

        System.out.println("Producers number: ");
        producersNum = scanner.nextInt();

        System.out.println("Consumers number: ");
        consumersNum = scanner.nextInt();

//        p = Integer.parseInt(args[1]);
//        queueSize = Integer.parseInt(args[0]);
//        producerThreadsNum = Integer.parseInt(args[2]);
//        countriesNumber = Integer.parseInt(args[3]);
//        problemsNumber = Integer.parseInt(args[4]);
//        int checkResult = Integer.parseInt(args[5]);

        Solution.maxQueueSize = maxQueueSize;
        Solution.producersNum = producersNum;
        Solution.consumersNum = consumersNum;
        Solution.p = producersNum + consumersNum;

        long start = System.nanoTime();

        runParallel();

        long end = System.nanoTime();
        System.out.println((double) (end - start) / 1E6);

//        if (checkResult == 1) {
//            compareResultsWithSequential();
//        }
    }
}
