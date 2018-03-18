package HackerRank;

import java.util.Scanner;

public class SolutionDay29 {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] data = new int[n][];
        for (int i = 0; i < n; i++) {
            data[i] = new int[]{sc.nextInt(), sc.nextInt()};
        }
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = 1; j < data[i][0]; j++) {
                for (int k = j + 1; k <= data[i][0]; k++) {
                    int band = j & k;
                    if (band < data[i][1]) {
                        max = Math.max(max, band);
                    }
                }
            }
            System.out.println(max);
        }
    }
}
