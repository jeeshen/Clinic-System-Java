package adt;

import java.util.Iterator;

public class SetAndQueue<T> implements SetAndQueueInterface<T> {
    //set
    private T[] elements;
    private int setSize;
    
    //queue
    private T[] array;
    private int frontIndex;
    private int backIndex;
    private int queueSize;
    
    //shared 
    private static final int DEFAULT_CAPACITY = 100;
    
    public SetAndQueue() {
        this(DEFAULT_CAPACITY);
    }
    
    public SetAndQueue(int initialCapacity) {
        //set
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        setSize = 0;
        
        //queue
        array = (T[]) new Object[initialCapacity];
        frontIndex = 0;
        backIndex = 0;
        queueSize = 0;
    }
    
    //set functions
    @Override
    public boolean add(T newEntry) {
        if (contains(newEntry)) {
            return false;
        }

        if (setSize == elements.length) {
            resize();
        }

        elements[setSize++] = newEntry;
        return true;
    }
    
    private void resize() {
        T[] newArray = (T[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, newArray, 0, setSize);
        elements = newArray;
    }
    
    @Override
    public boolean contains(T entry) {
        for (int i = 0; i < setSize; i++) {
            if (elements[i].equals(entry)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean remove(T entry) {
        for (int i = 0; i < setSize; i++) {
            if (elements[i].equals(entry)) {
                elements[i] = elements[setSize - 1]; //replace with last element
                elements[setSize - 1] = null;
                setSize--;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return setSize == 0;
    }
    
    @Override
    public void clearSet() {
        for (int i = 0; i < setSize; i++) {
            elements[i] = null;
        }
        setSize = 0;
    }
    
    @Override
    public int size() {
        return setSize;
    }
    
    @Override
    public T[] toArray() {
        T[] result = (T[]) new Object[setSize];
        System.arraycopy(elements, 0, result, 0, setSize);
        return result;
    }
    
    @Override
    public boolean containsAll(SetAndQueueInterface<T> otherSet) {
        T[] otherElements = (T[]) otherSet.toArray();
        for (T entry : otherElements) {
            if (!contains(entry)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(SetAndQueueInterface<T> otherSet) {
        boolean changed = false;
        T[] otherElements = (T[]) otherSet.toArray();
        for (T entry : otherElements) {
            if (add(entry)) {
                changed = true;
            }
        }
        return changed;
    }
    
    @Override
    public boolean isEqual(SetAndQueueInterface<T> otherSet) {
        return this.size() == otherSet.size() && this.containsAll(otherSet);
    }
    
    @Override
    public SetAndQueueInterface<T> union(SetAndQueueInterface<T> otherSet) {
        SetAndQueue<T> result = new SetAndQueue<>();
        result.addAll(this);
        result.addAll(otherSet);
        return result;
    }
    
    @Override
    public SetAndQueueInterface<T> intersection(SetAndQueueInterface<T> otherSet) {
        SetAndQueue<T> result = new SetAndQueue<>();
        for (int i = 0; i < setSize; i++) {
            T entry = elements[i];
            if (otherSet.contains(entry)) {
                result.add(entry);
            }
        }
        return result;
    }
    
    @Override
    public SetAndQueueInterface<T> difference(SetAndQueueInterface<T> otherSet) {
        SetAndQueue<T> result = new SetAndQueue<>();
        for (int i = 0; i < setSize; i++) {
            T entry = elements[i];
            if (!otherSet.contains(entry)) {
                result.add(entry);
            }
        }
        return result;
    }
    
    @Override
    public boolean isSubsetOf(SetAndQueueInterface<T> otherSet) {
        T[] thisElements = this.toArray();
        for (T entry : thisElements) {
            if (!otherSet.contains(entry)) {
                return false;
            }
        }
        return true;
    }
    
    //queue functions
    @Override
    public void enqueue(T newEntry) {
        if (!isArrayFull()) {
          frontIndex = (frontIndex + 1) % array.length; //this is for circular indexing, so we can reuse empty spots
          array[backIndex] = newEntry;
          queueSize++;
        }
    }
    
    @Override
    public T getFront() {
        if (isQueueEmpty()) return null; 
        return array[frontIndex];
    }
    
    @Override
    public T dequeue() {
        if (isQueueEmpty()) return null;
        T front = array[frontIndex]; //shift remaining array items forward one position 
        array[frontIndex] = null;
        frontIndex = (frontIndex + 1) % array.length;
        queueSize--;
        return front;
    }

    @Override
    public boolean isQueueEmpty() {
      return queueSize == 0;
    }

    @Override
    public void clearQueue() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        frontIndex = 0;
        backIndex = -1;
        queueSize = 0;
    }

    private boolean isArrayFull() {
      return queueSize == array.length;
    }
  
    public Iterator<T> getIterator() {
      return new QueueIterator();
    }
  
    private class QueueIterator implements Iterator<T> {
        private int count;
        private int currentIndex;
        
        private QueueIterator() {
            count = 0;
            currentIndex = frontIndex;
        }

        @Override
        public boolean hasNext() {
            return count < queueSize;
        }

        @Override
        public T next() {
            if (!hasNext()) return null;
            T result = array[currentIndex];
            currentIndex = (currentIndex + 1) % array.length;
            count++;
            return result;
        }
    }
}