package solution;

import data_structures.SynchronizedList;
import domain.Cursant;
import parallelism.Manager;
import parallelism.Secretara;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solution {
    private static int p;
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

    private static ArrayList<Cursant> generateList(int n) {
        var list = new ArrayList<Cursant>();

        for (int i = 0; i < n; i++) {
            Random random = new Random();
            var id = 1 + random.nextInt(n);
            var medie = random.nextFloat(1,10.1f);

            list.add(new Cursant(id, medie));
        }

        return list;
    }

    private static void runParallel() throws InterruptedException, IOException {
        Thread[] secretare = new Thread[p];
        int n = 103;

        ArrayList<Cursant> cursantsList = generateList(n);

        SynchronizedList<Cursant> resultsList = new SynchronizedList<>();

        Thread manager = new Manager(resultsList);
        manager.start();

        int batchSize = n / p;
        int remainder = n % p;
        int start = 0;

        for (int i = 0; i < p; i++) {
            int end = start + batchSize;
            if (remainder != 0) {
                end++;
                remainder--;
            }

            secretare[i] = new Secretara(resultsList, cursantsList, start, end);
            secretare[i].start();

            start = end;
        }

        for (int i = 0; i < p; i++) {
            secretare[i].join();
        }

        manager.join();
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
//
//        p = Integer.parseInt(args[1]);
//        queueSize = Integer.parseInt(args[0]);
//        producerThreadsNum = Integer.parseInt(args[2]);
//        countriesNumber = Integer.parseInt(args[3]);
//        problemsNumber = Integer.parseInt(args[4]);
//        int checkResult = Integer.parseInt(args[5]);

//        Solution.maxQueueSize = maxQueueSize;
//        Solution.producersNum = producersNum;
//        Solution.consumersNum = consumersNum;
        Solution.p = 5;

        long start = System.nanoTime();

        runParallel();

        long end = System.nanoTime();
        System.out.println((double) (end - start) / 1E6);

//        if (checkResult == 1) {
//            compareResultsWithSequential();
//        }
    }
}
