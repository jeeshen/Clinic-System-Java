package utility;

public class BubbleSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        int n = array.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    //swap elements
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            
            //if no swapping occurred, array is sorted
            if (!swapped) {
                break;
            }
        }
    }
} 