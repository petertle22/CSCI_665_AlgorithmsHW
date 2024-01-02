import java.util.Scanner;
import java.math.*;
import java.io.*;

class DoubleKnapsackSolution {
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
        //System.out.println(memo[N][W1][W2]);
        
        BudgetArrayList backpack1 = new BudgetArrayList(N);
        BudgetArrayList backpack2 = new BudgetArrayList(N);
        
        int item = N; int w1 = W1; int w2 = W2;
        while(item > 0) {
            if(w1 - w[item - 1] >= 0 && memo[item][w1][w2] == memo[item - 1][w1 - w[item - 1]][w2] + c[item - 1]) {
                backpack1.add(item);
                w1 -= w[item - 1];
            } else if(w2 - w[item - 1] >= 0 && memo[item][w1][w2] == memo[item - 1][w1][w2 - w[item - 1]] + c[item - 1]) {
                backpack2.add(item);
                w2 -= w[item - 1];
            } 
           item--;
       }
       System.out.println(backpack1);
       System.out.println(backpack2);
    }
}

class BudgetArrayList {
    private int[] items;
    private int size;

    public BudgetArrayList(int size) {
        this.size = 0;
        items = new int[size];
    }

    public void add(int n) {
        items[size] = n;
        size++;
        return;
    }

    private void reverse() {
        int left = 0;
        int right = size - 1;
        while(left < right) {
            int temp = items[left];
            items[left] = items[right];
            items[right] = temp;
            left++;
            right--;
        }
    }

    @Override
    public String toString() {
        String s = "";
        reverse();
        for(int n : items) {
            if(n == 0) {
                break;
            }
            s += n + " ";
        }
        return s;
    }
}