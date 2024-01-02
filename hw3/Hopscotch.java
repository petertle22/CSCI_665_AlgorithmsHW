import java.util.Scanner;
import java.math.*;
import java.io.*;

class Hopscotch {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int[] tiles = new int[N];
        for(int i = 0; i < N; i++) {
            tiles[i] = input.nextInt();
        }


        int[] memo  = new int[tiles.length];
        memo[tiles.length - 1] = tiles[tiles.length - 1];
        memo[tiles.length - 2] = tiles[tiles.length - 2];
        memo[tiles.length - 3] = tiles[tiles.length - 3] + tiles[tiles.length - 1];

        for(int i = tiles.length - 4; i >= 0; i--) {
            memo[i] = tiles[i] + Math.max(memo[i + 2], memo[i + 3]);
        }

        System.out.println(memo[0]);


    }
}