package solution;

import data_structures.SynchronizedMap;
import data_structures.SynchronizedQueue;
import domain.Entry;
import parallelism.ConsumerThread;
import parallelism.ProducerThread;
import parallelism.SupervisorThread;

import java.io.*;
import java.util.List;

public class Solution {
    private static int p;
    private static int producersNum;
    private static int consumersNum;
    private static int maxQueueSize;
    private static List<Entry> results;
    private static int Ta;
    private static int Tr;
    private static int Tv;
    private static int c;

//    public static void writeResultsToFile(List<ParticipantEntry> resultsList, String filePath) throws IOException {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
//
//        resultsList.forEach(entry -> {
//            try {
//                writer.write(String.valueOf(entry.getId()));
//                writer.write(' ');
//                writer.write(String.valueOf(entry.getScore()));
//                writer.write(' ');
//                writer.write(String.valueOf(entry.getCountryNum()));
//                writer.newLine();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        writer.close();
//    }
//
//    private static void compareResultsWithSequential() throws FileNotFoundException {
//        var filePath = "output\\Clasament_seq.txt";
//        Scanner scanner = new Scanner(new FileReader(filePath));
//
//        for (var entry : results) {
//            int id = scanner.nextInt();
//            int score = scanner.nextInt();
//
//            if (entry.getId() != id || entry.getScore() != score) {
//                System.err.println("Results do not match.");
//                return;
//            }
//        }
//    }

    private static void runParallel() throws InterruptedException, IOException {
        Thread[] producers = new Thread[p];
        Thread[] consumers = new Thread[c];

        SynchronizedQueue<Entry> queueDiff_1 = new SynchronizedQueue<>(maxQueueSize);
        SynchronizedQueue<Entry> queueDiff_2 = new SynchronizedQueue<>(maxQueueSize);
        SynchronizedQueue<Entry> queueDiff_3 = new SynchronizedQueue<>(maxQueueSize);

        SynchronizedMap resultsMap = new SynchronizedMap();

        for (int i = 0; i < p; i++) {
            producers[i] = new ProducerThread(i, Ta, queueDiff_1, queueDiff_2, queueDiff_3);
            producers[i].start();
        }

//        Thread superVisor = new SupervisorThread(Tv, queueDiff_1, queueDiff_2, queueDiff_3, resultsMap);
//        superVisor.start();

        for (int i = 0; i < 2; i++) {
            consumers[i] = new ConsumerThread(queueDiff_1, resultsMap, Tr);
            consumers[i].start();
        }

        for (int i = 2; i < 4; i++) {
            consumers[i] = new ConsumerThread(queueDiff_2, resultsMap, Tr);
            consumers[i].start();
        }

        for (int i = 4; i < c; i++) {
            consumers[i] = new ConsumerThread(queueDiff_3, resultsMap, Tr);
            consumers[i].start();
        }

        for (int i = 0; i < p; i++) {
            producers[i].join();
        }

        for (int i = 0; i < c; i++) {
            consumers[i].join();
        }

//        superVisor.join();

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
//        int maxQueueSize, producersNum, consumersNum;
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Max queue size: ");
//        maxQueueSize = scanner.nextInt();
//
//        System.out.println("Producers number: ");
//        producersNum = scanner.nextInt();
//
//        System.out.println("Consumers number: ");
//        consumersNum = scanner.nextInt();

//        p = Integer.parseInt(args[1]);
//        queueSize = Integer.parseInt(args[0]);
//        producerThreadsNum = Integer.parseInt(args[2]);
//        countriesNumber = Integer.parseInt(args[3]);
//        problemsNumber = Integer.parseInt(args[4]);
//        int checkResult = Integer.parseInt(args[5]);
        Solution.maxQueueSize = 20;
        Solution.p = 5;
        Solution.Ta = 20;
        Solution.Tr = 100;
        Solution.Tv = 30;
        Solution.c = 6;

        long start = System.nanoTime();

        runParallel();

        long end = System.nanoTime();
        System.out.println((double) (end - start) / 1E6);
//        if (checkResult == 1) {
//            compareResultsWithSequential();
//        }
    }
}
