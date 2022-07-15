package deque;

public class ArrayDeque<T> implements Deque<T> {
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

    private void resizeLeft(int capacity) {
        // To find the location of the latest first item, you reduce the "nextFirst" cursor back 1 element
        int firstElement = nextFirst + 1;
        T[] newArray = (T[]) new Object[capacity];
        if (nextFirst == size - 1) { // If the first item is at element 0
            System.arraycopy(array, 0, newArray, size, size);
        } else { // If the first item is at any other element
            // First it copies element starting at the latest front item and to end of the array
            // Since you can't specify the end of the array, using "size - firstElement" will provide you the length
            // from the front item to the end array
            System.arraycopy(array, firstElement, newArray, size, size - firstElement);
            // Then it copies items starting at element 0 to the front item element
            // "size + size - firstElement" provides the next ongoing element to start the copying process
            // "firstElement" will act as the leftover size of the original array
            System.arraycopy(array, 0, newArray, size + size - firstElement, firstElement);
        }
        array = newArray;
    }

    @Override
    public void addFirst(T item) {
        // Making array circular
        if  (nextFirst < 0) {
            nextFirst = array.length - 1; // If position zero is filled up, nextFirst loops to the end of the array
        }

        // Resizing array
        if (nextFirst == nextLast - 1 && size >= 1) { // If nextFirst is at the location of addLast item then the array is filled up
            resizeLeft(size * 2);
            nextFirst = size - 1;
            nextLast = array.length;
        }

        array[nextFirst] = item;
        nextFirst--;
        size++;
    }

    private void resizeRight(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        if (nextLast == 0) {
            System.arraycopy(array, 0, newArray, 0, size);
        } else {
            System.arraycopy(array, 0, newArray, size - nextLast, nextLast);
            System.arraycopy(array, nextLast, newArray, 0, size - nextLast);
        }
        array = newArray;
    }

    @Override
    public void addLast(T item) {
        // Making array circular
        if (nextLast == array.length) {
            nextLast = 0;
        }

        if (nextLast == nextFirst + 1 && size >= 1) {
            resizeRight(size * 2);
            nextLast = size;
            nextFirst = -1;
        }
        array[nextLast] = item;
        nextLast++;
        size++;
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
    }

    @Override
    public T removeFirst() {
        float desizeFactor = (float)size / array.length;
        if (isEmpty()) { // If the array is empty terminate the method
            return null;
        }
        // Resizing the array if the size if 1/4 of the total array length
        // ex: size = 8, length = 32, resize length to 16
        else if (array.length >= 16 && desizeFactor == 0.25) {
            desize(array.length / 2);
            nextFirst = array.length - 1;
            nextLast = size;
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
        float desizeFactor = (float)size / array.length;
        if (isEmpty()) { // If the array is empty terminate the method
            return null;
        }
        else if (array.length >= 16 && desizeFactor == 0.25) {
            desize(array.length / 2);
            nextFirst = array.length - 1;
            nextLast = size;
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
        // The first index of the array list
        int index = nextFirst + 1;
        // The last index
       int lastIndex = nextLast - 1;
        while (index != lastIndex) {
            // When index reaches the end of the array, move it back to the front of the array
            if (index > array.length) {
                index = 0;
            }
            System.out.print(array[index] + " ");
            index++;
        }
       System.out.print(array[index] + " ");
       System.out.println();
   }

    @Override
   public T get(int index) {
        int firstIndex = nextFirst + 1;
        if (index >= size || index < 0) { // return null if the index is negative or exceed the maximum index of array
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

    public int firstIndex() {
        if (nextFirst == array.length - 1) {
            return 0;
        }
        return nextFirst + 1;
    }

    public int lastIndex() {
        if (nextLast == 0) {
            return array.length - 1;
        }
        return nextLast - 1;
    }

    public T[] getArray() {
        return array;
    }
}
