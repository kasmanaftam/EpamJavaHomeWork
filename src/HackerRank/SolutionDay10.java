package HackerRank;

import java.util.Scanner;

public class SolutionDay10 {
    public static int consOnes(int n) {
        int maxCnt = 0;
        int cnt = 0;
        for (int i = 0; i < 31; i++) {
            if (n % 2 == 1) {
                cnt++;
            } else {
                maxCnt = Math.max(maxCnt, cnt);
                cnt = 0;
            }
            n /= 2;
        }
        return maxCnt;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int result = consOnes(n);
        System.out.println(result);
        in.close();
    }
}
