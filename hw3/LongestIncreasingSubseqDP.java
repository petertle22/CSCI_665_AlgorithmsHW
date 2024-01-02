import java.util.Scanner;
import java.math.*;
import java.io.*;

class LongestIncreasingSubseqDP {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int[] nums = new int[N];

        for(int i = 0; i < N; i++) {
            nums[i] = input.nextInt();
        }
        
        System.out.println(lengthOfLIS(nums));
    }


    public static int lengthOfLIS(int[] nums) {
        int N = nums.length;
        int[] memo = new int[N];

        for(int j = 0; j < nums.length; j++) {
            memo[j] = 1;
            for(int k = 0; k < j; k++) {
                if (nums[k] < nums[j] && memo[j] <= memo[k]) {
                    memo[j] = memo[k] + 1;
                }
            }
        }

        int res = -1;
        for(int i = 0; i < nums.length; i++) {
            res = Math.max(res, memo[i]);
        }
        return res;
    }
}