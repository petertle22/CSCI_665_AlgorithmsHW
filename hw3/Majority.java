import java.util.Scanner;
import java.math.*;
import java.io.*;

class Majority {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isHalf = false; boolean isThird = false;
        int N = input.nextInt();
        int[] nums = new int[N]; int[] numCount = new int[123456790];

        for(int i = 0; i < N; i++) {
            nums[i] = input.nextInt();
            numCount[nums[i]]++;
        }

        for(int i = 0; i < numCount.length; i++) {
            if(numCount[i] > (nums.length / 2)) {
                isHalf = true;
            }

            if(numCount[i] > (nums.length / 3)) {
                isThird = true;
            }
        }
        System.out.println(( isHalf ? "YES" : "NO"));
        System.out.println(( isThird ? "YES" : "NO"));
    }
}