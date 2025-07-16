package adt;

public interface SetInterface<T> {
    public boolean add(T newEntry);
    public boolean addAll(T newEntry);
    public boolean contains(T entry);
    public boolean containsAll(T entry);
    public boolean isEqual(SetInterface<T> entry);
    public boolean isEmpty();
    public boolean remove();
    
    public void clear();
    public int size();
    public Object[] toArray();  
    
    public SetInterface<T> union(SetInterface<T> entry);
    public SetInterface<T> intersection(SetInterface<T> entry);
    public SetInterface<T> difference(SetInterface<T> entry);
    public SetInterface<T> isSubsetOf(SetInterface<T> entry);
}