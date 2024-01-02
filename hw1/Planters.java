import java.util.Scanner;
import java.math.*;
import java.io.*;

class Planters {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int P = input.nextInt();   int T = input.nextInt();


        int[] plants = new int[P]; 
        for(int i = 0; i < plants.length; i++) {
            plants[i] = -input.nextInt();
        }

        mergeSort(plants);

        Heap maxHeap = new Heap( T + 1 );
        for(int i = 0; i < T; i++) {
            maxHeap.add( -input.nextInt() ) ;
        }
        
        for(int i = 0; i < plants.length; i++) {
            if(plants[i] <= maxHeap.peek()) {
                System.out.println("NO");
                return;
            } else {
                int temp = plants[i];
                plants[i] = maxHeap.poll();
                maxHeap.add(temp);
            }
        }
        System.out.println("YES");

    }

    public static void mergeSort(int[] array) {
        if(array.length == 1) {
            return;
        }
        int mid = array.length / 2;

        int[] left = new int[mid];
        for(int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }

        int[] right = new int[array.length - mid];
        for(int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }

        mergeSort(left);
        mergeSort(right);
        merge(left, right, array);
        return;
    }

    public static void merge(int[] left, int[] right, int[] res) {
        int i = 0, j = 0, k = 0;
        while(i < left.length &&  j < right.length) {
            if(left[i] <= right[j]) {
                res[k] = left[i++];
            } else {
                res[k] = right[j++];
            }
            k++;
        }
        while(i < left.length) {
            res[k++] = left[i++];
        }
        while(j < right.length) {
            res[k++] = right[j++];
        }
    }

}

class Heap {
    private int capacity;
    private int size = 0;
    public int[] array;

    public Heap(int size) {
        this.array = new int[size];
    }

    public int size() {
        return size;
    }

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return 2 * parentIndex + 2;
    }

    private int getParentIndex(int currentIndex) {
        return (currentIndex - 1) / 2;
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalArgumentException("No elments to peek");
        }
        return array[0];
    }

    public int poll() {
        if (size == 0) {
            throw new IllegalArgumentException("No elments to peek");
        }
        int root = array[0];
        array[0] = array[size - 1];
        size--;
        heapifyDown(0);
        return root;
    }

    public void add(int element) {
        array[size] = element;
        size++;
        heapifyUp(size - 1);
    }

    public void heapifyDown(int currentIndex) {
        int N = size - 1;
        while( ( getLeftChildIndex( currentIndex ) <= N ) && ( array[currentIndex] > array[getLeftChildIndex(currentIndex)] ) ||
               ( getRightChildIndex( currentIndex ) <= N ) && ( array[currentIndex] > array[getRightChildIndex(currentIndex)] ) ) {
            int j;
            if( getRightChildIndex(currentIndex) > N || ( array[getLeftChildIndex(currentIndex)] < array[getRightChildIndex( currentIndex )] ) ) {
                j = getLeftChildIndex( currentIndex );
            } else {
                j = getRightChildIndex( currentIndex );
            }
            int temp = array[currentIndex];
            array[currentIndex] = array[j];
            array[j] = temp;
            currentIndex = j;
        }
    }

    public void heapifyUp(int currentIndex) {
        while((currentIndex > 0) && (array[currentIndex] < array[getParentIndex( currentIndex ) ] ) ) {
            int temp = array[currentIndex];
            array[currentIndex] = array[getParentIndex( currentIndex )];
            array[getParentIndex(currentIndex)] = temp;
            currentIndex = getParentIndex( currentIndex );
        }
    }
}