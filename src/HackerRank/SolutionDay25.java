package HackerRank;

import java.util.Scanner;

public class SolutionDay25 {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        for (int i : nums) {
            if (isPrime(i)) {
                System.out.println("Prime");
            } else {
                System.out.println("Not prime");
            }
        }

    }

    static boolean isPrime(int a) {
        if (a == 1) return false;
        int lim = (int) Math.sqrt(a);
        for (int i = 2; i <= lim; i++) {
            if (a % i == 0) return false;
        }
        return true;
    }
}
