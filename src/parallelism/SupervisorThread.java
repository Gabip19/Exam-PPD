package parallelism;

import data_structures.SynchronizedMap;
import data_structures.SynchronizedQueue;
import domain.Entry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class SupervisorThread extends Thread {
    private final SynchronizedQueue<Entry> queue1;
    private final SynchronizedQueue<Entry> queue2;
    private final SynchronizedQueue<Entry> queue3;
    private final SynchronizedMap map;
    private int Tv;
    private final BufferedWriter writer;

    public SupervisorThread(int Tv, SynchronizedQueue<Entry> queue1, SynchronizedQueue<Entry> queue2, SynchronizedQueue<Entry> queue3, SynchronizedMap map) {
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.queue3 = queue3;
        this.map = map;
        this.Tv = Tv;
        try {
            writer = new BufferedWriter(new FileWriter("C:\\Users\\Asus\\Desktop\\FACULTATE\\Anul III\\PPD\\ExamPPD\\output\\log.txt", true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        while (true) {
            var size1 = queue1.getSize();
            var size2 = queue2.getSize();
            var size3 = queue3.getSize();

            var values = map.getValues();

            if (size1 == -1 && size2 == -1 && size3 == -1) {
                break;
            }

            size1 = size1 == -1 ? 0 : size1;
            size2 = size2 == -1 ? 0 : size2;
            size3 = size3 == -1 ? 0 : size3;

//            System.out.println("1: " + size1 + " 2: " + size2 + " 3: " + size3);

            var string = LocalDateTime.now() + " ---- queue 1: " + size1 + " queue 2: " + size2 + " queue 3: " + size3;
            StringBuilder secondString = new StringBuilder();

            for (var value : values.entrySet()) {
                secondString.append("Ag: ");
                secondString.append(value.getValue());
                secondString.append(" - ");
                secondString.append(value.getKey());
                secondString.append(", ");
            }

            try {
                writer.write(string);
                writer.write("   ");
//                writer.write(secondString.toString());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            waitTime(Tv);
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
