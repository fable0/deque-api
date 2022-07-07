package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    public MaxArrayDeque(Comparator<T> c) {
    }

    private static class ValueComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer itemA, Integer itemB) {
            if (itemA > itemB) {
                return 1;
            } else if (itemA == itemB) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public Comparator<Integer> getValueComparator() {
        return new ValueComparator();
    }

    public T max() {
    }

    public T max(Comparator<T> c) {
    }

}
