import java.util.Scanner;
import java.math.*;
import java.io.*;

class Brickwall {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int[] availableBricks = {input.nextInt(), input.nextInt(), input.nextInt()};
        int[] brickWall = new int[N];

        for(int i = 0; i < N; i++) {
            brickWall[i] = input.nextInt();
        }




        int cementSum = 0;
        for(int i = 0; i < brickWall.length; i++) {
            cementSum += brickWall[i];
        }

        boolean[] isGap = new boolean[cementSum + 1];

        int sum = 0;
        for(int i = 0; i < brickWall.length; i++) {
            sum += brickWall[i];
            isGap[sum] = true;
        }
        isGap[isGap.length - 1] = false;

        boolean[][][] memo = new boolean[availableBricks[0] + 1][availableBricks[1] + 1][availableBricks[2] + 1];
        for(int c1 = 0; c1 <= availableBricks[0]; c1++) {
            for(int c2 = 0; c2 <= availableBricks[1]; c2++) {
                for(int c3 = 0; c3 <= availableBricks[2]; c3++) {
                    int totalBricks = c1 * 1 + c2 * 2 + c3 * 3;
                    if(totalBricks == cementSum) {
                        memo[c1][c2][c3] = true;
                    }
                }
            }
        }

        
        for(int c1 = availableBricks[0]; c1 >= 0; c1--) {
            for(int c2 = availableBricks[1]; c2 >= 0; c2--) {
                for(int c3 = availableBricks[2]; c3 >= 0; c3--) {
                    if((c3 + 1 <= availableBricks[2]) && (c1 * 1 + c2 * 2 + (c3 + 1) * 3 <= cementSum) && !isGap[c1 * 1 + c2 * 2 + (c3 + 1) * 3]) {
                        memo[c1][c2][c3] |= memo[c1][c2][c3 + 1];
                    }
                    if((c2 + 1 <= availableBricks[1]) && (c1 * 1 + (c2 + 1) * 2 + c3 * 3 <= cementSum) && !isGap[c1 * 1 + (c2 + 1) * 2 + c3 * 3]) {
                        memo[c1][c2][c3] |= memo[c1][c2 + 1][c3];
                    }
                    if((c1 + 1 <= availableBricks[0]) && ((c1 + 1) * 1 + c2 * 2 + c3 * 3 <= cementSum) && !isGap[(c1 + 1) * 1 + c2 * 2 + c3 * 3]) {
                        memo[c1][c2][c3] |= memo[c1 + 1][c2][c3];
                    }
                }
            }
        }
        if (memo[0][0][0]) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}