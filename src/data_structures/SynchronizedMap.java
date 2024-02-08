package data_structures;

import domain.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SynchronizedMap {
    private final HashMap<Integer, List<Entry>> map;

    public SynchronizedMap() {
        this.map = new HashMap<>();
    }

    public synchronized void add(Entry entry) {
        if (map.containsKey(entry.getId_agent())) {
            var list = new ArrayList<>(map.get(entry.getId_agent()));
            list.add(entry);
            map.put(
                entry.getId_agent(),
                list.stream().sorted(
                    (Entry e1, Entry e2) -> -Integer.compare(e1.getDificultate(), e2.getDificultate())
                ).toList()
            );
        } else {
            var list = new ArrayList<Entry>();
            list.add(entry);
            map.put(entry.getId_agent(), list);
        }
    }

//    public synchronized void

//    public void add(TEntry entry) {
//        listLock.lock();
//        try {
//            linkedList.insertLast(entry);
//        } catch (Exception e) {
//            System.out.println("Could not add to the list: " + e.getMessage());
//        } finally {
//            listLock.unlock();
//        }
//    }
//
//    public void remove(TEntry entry) {
//        listLock.lock();
//        try {
//            linkedList.removeNode(entry);
//        } catch (Exception e) {
//            System.out.println("Could not add to the list: " + e.getMessage());
//        } finally {
//            listLock.unlock();
//        }
//    }
//
//    public int size() {
//        listLock.lock();
//        int size = linkedList.getSize();
//        listLock.unlock();
//        return size;
//    }
//
//    public List<TEntry> getEntriesAsList() {
//        var entriesList = new ArrayList<TEntry>();
//
//        listLock.lock();
//
//        var tail = linkedList.getTail();
//        var currentNode = linkedList.getFirstNode();
//
//        while (currentNode != tail) {
//            entriesList.add(currentNode.getEntry());
//            currentNode = currentNode.getNext();
//        }
//
//        listLock.unlock();
//
//        return entriesList;
//    }
}
