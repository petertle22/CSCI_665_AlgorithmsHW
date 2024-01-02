import java.util.Scanner;
import java.math.*;
import java.io.*;

class MrBrilliant {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int[] tiles = {6,7,8,9,1,2};
        MrBrilliantGreedy2(tiles);

    }

     public static void MrBrilliantGreedy2(int[] nums) {
        int maxLength = 0; java.util.ArrayList<Integer> maxList = new java.util.ArrayList<Integer>();

        for(int L = 0; L < nums.length; L++) {
            int i = L; 
            java.util.ArrayList<Integer> list = new java.util.ArrayList<Integer>();
            list.add(nums[i]);
            for(int j = i + 1; j < nums.length; j++) {
                if(nums[i] < nums[j]) {
                    i = j;
                    list.add(nums[i]);
                }
            }
            if(list.size() > maxLength) {
                maxLength = list.size();
                maxList = list;
            }
        }
        for(int n : maxList) {
            System.out.print(n + "\t");
        }
     }








    public static void MrBrilliantGreedy1(int[] nums) {
        int L = 0;
        while(L < nums.length) {
            L = findSmallestIndex(L, nums);
            System.out.print(nums[L] + ",\t");
            L++;
        }

    }

    public static int findSmallestIndex(int offset, int[] nums) {
        if(offset >= nums.length) {
            return -1;
        }
        int minIndex = offset; int minElement = nums[offset];
        for(int i = offset + 1; i < nums.length; i++) {
            if(nums[i] < minElement) {
                minIndex = i;
                minElement = nums[i];
            }
        }
        return minIndex;
    }
}