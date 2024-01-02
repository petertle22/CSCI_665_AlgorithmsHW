import java.util.Scanner;
import java.math.*;
import java.io.*;

class AllWhiteSquare {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        char[][] matrix = new char[N][N];
        for(int i = 0; i < N; i++) {
            String line = input.nextLine();
            for(int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
            }
        }
        System.out.println(maximalSquare(matrix));

    }
    public static int maximalSquare(char[][] matrix) {
        int[][] memo = new int[matrix.length][matrix[0].length];
        int M = memo.length;
        int N = memo[0].length;
        int res = 0;
        for(int i = 0; i < memo.length; i++) {
            if(matrix[i][N - 1] == 'w') {
                memo[i][N - 1] = 1;
                res = 1;
            }
        }

        for(int j = 0; j < memo[0].length; j++) {
            if(matrix[M - 1][j] == 'w') {
                memo[M - 1][j] = 1;
                res = 1;
            }
        }

        for(int i = M - 2; i >= 0; i--)  {
            for(int j = N - 2; j >= 0; j--) {
                if(matrix[i][j] == 'w') {
                    memo[i][j] = Math.min(memo[i + 1][j + 1], Math.min(memo[i + 1][j], memo[i][j + 1] ) ) + 1;
                    res = Math.max(memo[i][j], res);
                }
            }
        }
        return res;
    }
}