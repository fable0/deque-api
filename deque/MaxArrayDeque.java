package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque() {
        super();
    }
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T[] array = this.getArray();
        int maxIndex = this.firstIndex();
        int lastIndex = this.lastIndex();

        for (int i = maxIndex + 1; i != lastIndex + 1; i++) {
            if (i == array.length) {
                i = 0;
            }
            if (comparator.compare(array[maxIndex], array[i]) < 0) {
                maxIndex = i;
            }
        }
        return array[maxIndex];
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T[] array = this.getArray();
        int maxIndex = this.firstIndex();
        int lastIndex = this.lastIndex();

        for (int i = maxIndex + 1; i != lastIndex + 1; i++) {
            if (i == array.length) {
                i = 0;
            }
            if (c.compare(array[maxIndex], array[i]) < 0) {
                maxIndex = i;
            }
        }
        return array[maxIndex];
    }
}
