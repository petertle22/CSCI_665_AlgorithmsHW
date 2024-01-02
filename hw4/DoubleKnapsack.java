import java.util.Scanner;
import java.math.*;
import java.io.*;

class DoubleKnapsack {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int W1 = input.nextInt(); int W2 = input.nextInt();

        int[] w = new int[N]; int[] c = new int[N];

        for(int i = 0; i < N; i++) {
            w[i] = input.nextInt();
            c[i] = input.nextInt();
        }

        int[][][] memo = new int[N + 1][W1 + 1][W2 + 1];

        for(int j = 0; j <= W1; j++) {
            for(int k = 0; k <= W2; k++) {
                memo[0][j][k] = 0;
            }
        }

        for(int i = 0; i <= N; i++ ) {
            memo[i][0][0] = 0;
        }

        for(int j = 1; j <= W1; j++) {
            for(int i = 1; i <= N; i++ ) {
                memo[i][j][0] = memo[i - 1][j][0];
                if(w[i - 1] <= j && (memo[i][j][0] < memo[i - 1][j - w[i - 1]][0] + c[i - 1])) {
                    memo[i][j][0] = memo[i - 1][j - w[i - 1]][0] + c[i - 1];
                } 
            }
        }

        for(int k = 1; k <= W2; k++) {
            for(int i = 1; i <= N; i++ ) {
                memo[i][0][k] = memo[i - 1][0][k];
                if(w[i - 1] <= k && (memo[i][0][k] < memo[i - 1][0][k - w[i - 1]] + c[i - 1])) {
                    memo[i][0][k] = memo[i - 1][0][k - w[i - 1]] + c[i - 1];
                }
            }
        }

        for(int k = 1; k <= W2; k++) {
            for(int j = 1; j <= W1; j++) {
                for(int i = 1; i <= N; i++) {
                    memo[i][j][k] = memo[i - 1][j][k];

                    if(w[i - 1] <= j && (memo[i][j][k] < memo[i - 1][j - w[i - 1]][k] + c[i - 1] )) {
                        memo[i][j][k] = memo[i - 1][j - w[i - 1]][k] + c[i - 1];
                    }

                    if(w[i - 1] <= k && (memo[i][j][k] < memo[i - 1][j][k - w[i - 1]] + c[i - 1])) {
                        memo[i][j][k] = memo[i - 1][j][k - w[i - 1]] + c[i - 1];
                    }
                }
            }
        } 

        System.out.println(memo[N][W1][W2]);
        
    }
}