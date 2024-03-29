package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque() {}

    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (T item : this) {
            if (comparator.compare(maxItem, item) < 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (T item : this) {
            if (c.compare(maxItem, item) < 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
}
