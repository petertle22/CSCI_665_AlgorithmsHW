import java.util.Scanner;
import java.math.*;
import java.io.*;

class SizeDoubleS {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt(); double[] nums = new double[N];
        for(int i = 0; i < nums.length; i++) {
            nums[i] = input.nextDouble();
        } 

        double[] library = new double[N * N]; int idx = 0;
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j < nums.length; j++) {
                library[idx++] = nums[i] + nums[j];
            }
        }

        mergeSort(library);
        
        double maxNum = 0xfff0000000000000L; int diffNums = 0;
        for(int i = 0; i < library.length; i++) {
            if(library[i] > maxNum) {
                diffNums++; 
                maxNum = library[i];
            }
        }
        System.out.println(diffNums);
    }

    public static void mergeSort(double[] array) {
        if(array.length == 1) {
            return;
        }
        int mid = array.length / 2;

        double[] left = new double[mid];
        for(int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }

        double[] right = new double[array.length - mid];
        for(int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }

        mergeSort(left);
        mergeSort(right);
        merge(left, right, array);
        return;
    }

    public static void merge(double[] left, double[] right, double[] res) {
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