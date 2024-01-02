import java.util.Scanner;
import java.math.*;
import java.io.*;

class SmallestTwo {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int nextNum = input.nextInt();
            if (min1 == Integer.MAX_VALUE) {
                min1 = nextNum;
            }
            if (nextNum < min1) {
                min2 = (min1 != min2) ? min1 : min2;
                min1 = nextNum;
            }
        }
        System.out.println(min1);
        System.out.println(min2);
    }
}