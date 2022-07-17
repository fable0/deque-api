package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] array;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    }

    private int firstIndex() {
        if (nextFirst == array.length - 1) {
            return 0;
        }
        return nextFirst + 1;
    }

    private int lastIndex() {
        if (nextLast == 0) {
            nextLast = array.length - 1;
        }
        return nextLast - 1;
    }

    private float getUsageFactor() {
        return (float) size / array.length;
    }


    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        if (firstIndex() > lastIndex()) {
            int diff = array.length - firstIndex();
            System.arraycopy(array, firstIndex(), newArray, 0, diff);
            System.arraycopy(array, 0, newArray, diff, size - diff);
        } else {
            System.arraycopy(array, firstIndex(), newArray, 0, size);
        }
        array = newArray;
        nextFirst = array.length - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[nextFirst] = item;
        nextFirst--;
        size++;
        if (nextFirst < 0) {
            nextFirst = array.length - 1;
        }
    }

    @Override
    public void addLast(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[nextLast] = item;
        nextLast++;
        size++;
        if (nextLast == array.length) {
            nextLast = 0;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (array.length >= 16 && getUsageFactor() == 0.25) {
            resize(array.length / 2);
        }
        T removedValue = get(firstIndex());
        array[firstIndex()] = null;
        size--;
        nextFirst++;
        if (nextFirst >= array.length) {
            nextFirst = 0;
        }
        return removedValue;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (array.length >= 16 && getUsageFactor() == 0.25) {
            resize(array.length / 2);
        }
        T removedValue = get(lastIndex());
        array[lastIndex()] = null;
        size--;
        nextLast--;
        if (nextLast < 0) {
            nextLast = array.length - 1;
        }
        return removedValue;
    }

    @Override
    public void printDeque() {
        for (T item : this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else if ((firstIndex() + index) >= array.length) {
            return array[(firstIndex() + index) - array.length];
        } else {
            return array[firstIndex() + index];
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        ArrayDequeIterator() {
            pos = 0;
        }
        @Override
        public boolean hasNext() {
            return pos != size;
        }

        public T next() {
            T returnItem = get(pos);
            pos++;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
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
        int point = nextFirst + 1;
        for (T item : (Iterable<T>) other) {

            if (point == array.length) {
                point = 0;
            }
            if (!(item.equals(array[point]))) {
                return false;
            }
            point++;
        }
        return true;
    }
}
