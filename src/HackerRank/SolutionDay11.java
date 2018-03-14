package HackerRank;

import java.util.Scanner;

public class SolutionDay11 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int arr[][] = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                arr[i][j] = in.nextInt();
            }
        }
        int maxCnt = -9 * 36;
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                int cnt = arr[i][k] + arr[i][k + 1] + arr[i][k + 2] +
                        arr[i + 1][k + 1] +
                        arr[i + 2][k] + arr[i + 2][k + 1] + arr[i + 2][k + 2];
                maxCnt = Math.max(cnt, maxCnt);
            }
        }
        System.out.println(maxCnt);
    }
}

