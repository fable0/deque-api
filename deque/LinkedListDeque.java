package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private Node sentinel;
    private int size;

    private class Node {
        private Node prev;
        private T item;
        private Node next;
        Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        sentinel.next = new Node(sentinel, item, sentinel.next);
        if (size > 0) {
            sentinel.next.next.prev = sentinel.next;
        } else {
            sentinel.prev = sentinel.next;
        }
        size++;
    }

    @Override
    public void addLast(T item) {
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        if (size > 0) {
            sentinel.prev.prev.next = sentinel.prev;
        } else {
            sentinel.next = sentinel.prev;
        }
        size++;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (T item : this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T itemRemoved = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return itemRemoved;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T itemRemoved = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return itemRemoved;
    }

    @Override
    public T get(int index) {
        Node node = sentinel.next;
        while (index > 0) {
            if (node.item == null) {
                return null;
            }
            node = node.next;
            index--;
        }
        return node.item;
    }

    private T getRecursiveHelper(Node node, int cnt) {
        if (node.item == null) {
            return null;
        } else if (cnt == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, cnt - 1);
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node pos;

        LinkedListDequeIterator() {
            pos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return pos != sentinel;
        }

        public T next() {
            T returnItem = pos.item;
            pos = pos.next;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque.LinkedListDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque<T> other = (Deque<T>) o;
        if (this.size() != other.size()) {
            return false;
        }
        Node point = sentinel.next;
        for (T item : (Iterable<T>) other) {
            if (!(item.equals(point.item))) {
                return false;
            }
            point = point.next;
        }
        return true;
    }

}
