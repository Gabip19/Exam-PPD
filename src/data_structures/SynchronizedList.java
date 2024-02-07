package data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedList<TEntry> {
    private final MyLinkedList<TEntry> linkedList;
    private final Lock listLock = new ReentrantLock();

    public SynchronizedList() {
        this.linkedList = new MyLinkedList<>();
    }

    public void add(TEntry entry) {
        listLock.lock();
        try {
            linkedList.insertLast(entry);
        } catch (Exception e) {
            System.out.println("Could not add to the list: " + e.getMessage());
        } finally {
            listLock.unlock();
        }
    }

    public void remove(TEntry entry) {
        listLock.lock();
        try {
            linkedList.removeNode(entry);
        } catch (Exception e) {
            System.out.println("Could not add to the list: " + e.getMessage());
        } finally {
            listLock.unlock();
        }
    }

    public int size() {
        listLock.lock();
        int size = linkedList.getSize();
        listLock.unlock();
        return size;
    }

    public List<TEntry> getEntriesAsList() {
        var entriesList = new ArrayList<TEntry>();
        var tail = linkedList.getTail();
        var currentNode = linkedList.getFirstNode();

        while (currentNode != tail) {
            entriesList.add(currentNode.getEntry());
            currentNode = currentNode.getNext();
        }

        return entriesList;
    }
}
