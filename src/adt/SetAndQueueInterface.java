package adt;

public interface SetAndQueueInterface<T> {
    //set, all modules
    public boolean add(T newEntry);
    public boolean addAll(SetAndQueueInterface<T> otherSet);
    public boolean contains(T entry);
    public boolean containsAll(SetAndQueueInterface<T> otherSet);
    public boolean isEqual(SetAndQueueInterface<T> otherSet);
    public boolean isEmpty();
    public boolean remove(T entry);
    
    public void clearSet();
    public int size();
    public Object[] toArray(); //to translate set to array for displaying
    
    public SetAndQueueInterface<T> union(SetAndQueueInterface<T> otherSet);
    public SetAndQueueInterface<T> intersection(SetAndQueueInterface<T> otherSet);
    public SetAndQueueInterface<T> difference(SetAndQueueInterface<T> otherSet);
    
    //queue, patient only
    public void enqueue(T newEntry);
    public T dequeue();
    public T getFront();
    public boolean isQueueEmpty();
    public void clearQueue();
    public Object[] toQueueArray(); //to translate queue to array for displaying
}