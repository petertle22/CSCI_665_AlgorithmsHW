import java.util.Scanner;
import java.math.*;
import java.io.*;

class LongestConvexSubseq {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        int N = input.nextInt();
        int[] nums = new int[N];
        for(int i = 0; i < N; i++) {
            nums[i] = input.nextInt();
        }

        if(N <= 2) {
            System.out.println(N);
            return;
        }
        int res = 0; int[][] memo = new int[N][N];

        for(int i = 0; i < N; i++) {
            memo[i][i] = 1;
        }

        for(int i = 1; i < N; i++) {
            for(int j = 0; j < i; j++) {
                memo[i][j] = 2;
                for(int k = 0; k < j; k++) {
                    if(memo[j][k] + 1 > memo[i][j] && ((nums[i] + nums[k]) > (2 * nums[j]))) {
                        memo[i][j] = memo[j][k] + 1;
                    }
                    res = Math.max(res, memo[i][j]);
                }
            }
        }
        System.out.println(res);
    }
}