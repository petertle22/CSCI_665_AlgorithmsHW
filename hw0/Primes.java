import java.util.Scanner;
import java.math.*;
import java.io.*;

class Primes {
    static Scanner input = new Scanner(System.in);

    public static boolean isPrime(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = input.nextInt();
        for (int i = 2; i <= N; i++) {
            if (isPrime(i)) {
                System.out.println(i);
            }
        }
    }
}