import java.util.Scanner;
import java.math.*;
import java.io.*;

class LongestIncreasingSubseqRecursive {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int[] nums = new int[N];

        for(int i = 0; i < N; i++) {
            nums[i] = input.nextInt();
        }

        int[] memo = new int[nums.length];

        int res = 0;
        for(int i = 0; i < memo.length; i++ ){
            res = Math.max(res, incrSubseqRecursive(i, nums, memo));
        }
        System.out.println(res);
    }

    public static int incrSubseqRecursive(int j, int[] nums, int[] memo) {
        if (j == -1) {
            return 0;
        } else if (memo[j] != 0) {
            return memo[j];
        } else {
            int maxLength = 1;
            for(int i = j - 1; i >= 0; i--) {
                if (nums[j] > nums[i]) {
                    int lengthFromI = incrSubseqRecursive(i, nums, memo) + 1;
                    maxLength = Math.max(maxLength, lengthFromI);
                }
            }
            memo[j] = maxLength;
            return memo[j];
        }
    }
}