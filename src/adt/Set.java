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

    //core functions
    @Override
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
    
    @Override
    public boolean addAll(T newEntry) {
        return add(newEntry);
    }

    @Override
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
    
    @Override
    public boolean containsAll(T entry) {
        return contains(entry);
    }
    
    @Override
    public boolean isEqual(SetInterface<T> entry) {
        if (this.size != entry.size()) return false;
        for (int i = 0; i < size; i++) {
            if (!entry.contains(set[i])) return false;
        }
        return true;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public boolean remove() {
        if (isEmpty()) return false;
        set[--size] = null;
        return true;
    }
    
    public boolean remove(T entry) {
        for (int i = 0; i < size; i++) {
            if (entry == null ? set[i] == null : entry.equals(set[i])) {
                set[i] = set[size - 1];
                set[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            set[i] = null;
        }
        size = 0;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(set, 0, result, 0, size);
        return result;
    }
    
    @Override
    public SetInterface<T> intersection(SetInterface<T> other) {
        Set<T> result = new Set<>(size);
        for (int i = 0; i < size; i++) {
            if (other.contains(set[i])) result.add(set[i]);
        }
        return result;
    }
    
    @Override
    public SetInterface<T> union(SetInterface<T> other) {
        Set<T> result = new Set<>(size + other.size());
        for (int i = 0; i < size; i++) result.add(set[i]);
        for (Object element : other.toArray()) result.add((T) element);
        return result;
    }
    
    @Override
    public SetInterface<T> difference(SetInterface<T> other) {
        Set<T> result = new Set<>(size);
        for (int i = 0; i < size; i++) {
            if (!other.contains(set[i])) result.add(set[i]);
        }
        return result;
    }
    
    @Override
    public SetInterface<T> isSubsetOf(SetInterface<T> other) {
        Set<T> result = new Set<>(size);
        for (int i = 0; i < size; i++) {
            if (other.contains(set[i])) result.add(set[i]);
        }
        return result;
    }
    
    
    //other functions
    private void resize() {
        T[] newSet = (T[]) new Object[set.length * 2];
        for (int i = 0; i < set.length; i++) {
            newSet[i] = set[i];
        }
        set = newSet;
    }
}
