import java.util.Scanner;
import java.math.*;
import java.io.*;

class MatrixChainParenthesize {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int[] nums = new int[N + 1];
        for(int i = 0; i < N + 1; i++) {
            nums[i] = input.nextInt();
        } 

        int[][] OPT = new int[N + 1][N + 1];

        for(int d = 1; d < N; d++) {
            for(int i = 1; i < N - d + 1; i++) {
                int j = i + d;
                OPT[i][j] = 9999999;
                for(int k = i; k < j; k++) {
                    int temp = OPT[i][k] + OPT[k + 1][j] + nums[i - 1] * nums[k] * nums[j];
                    if(temp < OPT[i][j]) {
                        OPT[i][j] = temp;
                    }
                }
            }
        }
        System.out.println(OPT[1][N]);
        System.out.println(countParenthesis(nums, OPT, 1, N));
    }

    public static String countParenthesis(int[] nums, int[][] OPT, int left, int right) {
        if(left + 1 == right) {
            return "( A" + left + " x A" + right + " )";
        }
        if(left == right) {
            return "A" + right;
        }
        String ans = "";
        for(int k = left; k < right; k++) {
            if(OPT[left][right] == OPT[left][k] + OPT[k + 1][right] + nums[left - 1] * nums[k] * nums[right]) {
                ans += countParenthesis(nums, OPT, left, k) + " x " + countParenthesis(nums, OPT, k + 1, right);
                break;
            }
        }
        return  "( " + ans + " )";
    }

}