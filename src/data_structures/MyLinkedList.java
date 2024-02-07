package data_structures;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLinkedList<TEntry> {

    public class Node {
        private TEntry entry;
        private Node next;
        private Node previous;
        private final Lock lock = new ReentrantLock();

        public Node(TEntry entry) {
            this.entry = entry;
            this.next = null;
            this.previous = null;
        }

        public Node(TEntry entry, Node next, Node previous) {
            this.entry = entry;
            this.next = next;
            this.previous = previous;
        }

        public TEntry getEntry() {
            return entry;
        }

        public void setEntry(TEntry entry) {
            this.entry = entry;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrevious() {
            return previous;
        }

        public Lock getLock() {
            return lock;
        }
    }

    public MyLinkedList() {
        head = new Node(null);
        tail = new Node(null);

        head.next = tail;
        tail.previous = head;
        size = 0;
    }

    private final Node head;
    private final Node tail;
    private int size;

    public Node getHead() {
        return head;
    }
    public Node getTail() {
        return tail;
    }
    public int getSize() {
        return size;
    }

    public Node getFirstNode() {
        if (size == 0) {
            return null;
        }
        return head.next;
    }

    public void insertAfterNode(Node node, TEntry value) {
        var nextNode = node.next;
        var newNode = new Node(value);

        node.next = newNode;
        newNode.previous = node;

        newNode.next = nextNode;
        nextNode.previous = newNode;

        size++;
    }

    public void insertBeforeNode(Node node, TEntry value) {
        var previousNode = node.previous;
        var newNode = new Node(value);

        node.previous = newNode;
        newNode.next = node;

        newNode.previous = previousNode;
        previousNode.next = newNode;

        size++;
    }

    public void removeNode(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;

        size--;
    }

    public void removeNode(TEntry entry) {
        var currentNode = getFirstNode();
        while (currentNode != tail && !currentNode.getEntry().equals(entry)) {
            currentNode = currentNode.next;
        }

        if (currentNode == tail) {
            return;
        }

        removeNode(currentNode);
    }

    public void updateNode(Node node, TEntry entry) {
        node.setEntry(entry);
    }

    public void insertLast(TEntry entry) {
        var newNode = new Node(entry);
        var prev = tail.previous;

        prev.next = newNode;
        newNode.previous = prev;

        tail.previous = newNode;
        newNode.next = tail;
    }

    public void insertFirst(TEntry entry) {
        var newNode = new Node(entry);
        var next = head.next;

        head.next = newNode;
        newNode.previous = head;

        next.previous = newNode;
        newNode.next = next;
    }
}
