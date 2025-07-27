package adt;

public interface SetAndQueueInterface<T> {
    //set
    public boolean add(T newEntry);
    public boolean addAll(SetAndQueueInterface<T> otherSet);
    public boolean contains(T entry);
    public boolean containsAll(SetAndQueueInterface<T> otherSet);
    public boolean isEqual(SetAndQueueInterface<T> otherSet);
    public boolean isEmpty();
    public boolean remove(T entry);
    
    public void clearSet();
    public int size();
    public Object[] toArray();  
    
    public SetAndQueueInterface<T> union(SetAndQueueInterface<T> otherSet);
    public SetAndQueueInterface<T> intersection(SetAndQueueInterface<T> otherSet);
    public SetAndQueueInterface<T> difference(SetAndQueueInterface<T> otherSet);
    public boolean isSubsetOf(SetAndQueueInterface<T> otherSet);
    
    //queue
    public void enqueue(T newEntry);
    public T dequeue();
    public T getFront();
    public boolean isQueueEmpty();
    public void clearQueue();
}