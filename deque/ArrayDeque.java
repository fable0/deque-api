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

    private void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        if (nextLast == 0) {
            System.arraycopy(array, 0, newArray, 0, size);
        } else {
            System.arraycopy(array, 0, newArray, size - nextLast, nextLast);
            System.arraycopy(array, nextLast, newArray, 0, size - nextLast);
        }
        array = newArray;
        nextFirst = array.length - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        // Resizing array
        if (size == array.length) {
            // If nextFirst is at the location of addLast item then the array is filled up
            resize(size * 2);
        }
        array[nextFirst] = item;
        nextFirst--;
        size++;
        // Making array circular
        if  (nextFirst < 0) {
            // If position zero is filled up, nextFirst loops to the end of the array
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
        // Making array circular
        if (nextLast == array.length) {
            nextLast = 0;
        }
    }

    private void desize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int firstIndex = nextFirst + 1;
        int lengthRemove = array.length - firstIndex;
        // This condition occur whenever there are values that are looped over
        if (lengthRemove <= 8) {
            System.arraycopy(array, firstIndex, newArray, 0, lengthRemove);
            if (size - lengthRemove > 0) {
                System.arraycopy(array, 0, newArray, lengthRemove, size - lengthRemove);
            }
        } else {
            System.arraycopy(array, firstIndex, newArray, 0, nextLast - firstIndex);
        }
        array = newArray;
        nextFirst = array.length - 1;
        nextLast = size;
    }

    @Override
    public T removeFirst() {
        float desizeFactor = (float) size / array.length;
        if (isEmpty()) { // If the array is empty terminate the method
            return null;
        } else if (array.length >= 16 && desizeFactor == 0.25) {
            // Resizing the array if the size if 1/4 of the total array length
            // ex: size = 8, length = 32, resize length to 16
            desize(array.length / 2);
        }
        nextFirst += 1;
        if (nextFirst >= array.length) {
            nextFirst = 0;
        }
        T removedValue = array[nextFirst];
        array[nextFirst] = null;
        size--;
        return removedValue;
    }

    @Override
    public T removeLast() {
        float desizeFactor = (float) size / array.length;
        if (isEmpty()) { // If the array is empty terminate the method
            return null;
        } else if (array.length >= 16 && desizeFactor == 0.25) {
            desize(array.length / 2);
        }
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast = array.length - 1;
        }
        T removedValue = array[nextLast];
        array[nextLast] = null;
        size--;
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
        int firstIndex = nextFirst + 1;
        // return null if the index is negative or exceed the maximum index of array
        if (index >= size || index < 0) {
            return null;
        } else if ((firstIndex + index) >= array.length) {
            return array[(firstIndex + index) - array.length];
        } else {
            return array[firstIndex + index];
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
