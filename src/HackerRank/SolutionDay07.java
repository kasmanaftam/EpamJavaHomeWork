package HackerRank;

import java.util.Scanner;

public class SolutionDay07 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        in.close();
        if (n > 0) System.out.print(arr[n - 1]);
        for (int i = n - 2; i >= 0; i--) {
            System.out.print(" " + arr[i]);
        }
    }
}
