package adt;

public class Set<T> implements SetInterface<T> {
    private T[] set;
    private int size;
    private static final int DEFAULT_CAPACITY = 100;
    
    public Set() {
        this(DEFAULT_CAPACITY);
    }
    
    public Set(int defaultCapacity) {
        set = (T[]) new Object[defaultCapacity];
        size = 0;
    }

    public boolean add(T newEntry) {
        if (contains(newEntry)) {
            return false;
        }
        if (size == set.length) {
            resize();
        }
        set[size++] = newEntry;
        return true;
    }

    public boolean contains(T entry) {
        for (int i = 0; i < size; i++) {
            if (entry == null) {
                if (set[i] == null) {
                    return true;
                }
            } else {
                if (entry.equals(set[i])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void resize() {
        T[] newSet = (T[]) new Object[set.length * 2];
        for (int i = 0; i < set.length; i++) {
            newSet[i] = set[i];
        }
        set = newSet;
    }
}
