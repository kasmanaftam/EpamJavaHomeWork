package HackerRank;

import java.util.Arrays;
import java.util.Scanner;

public class SolutionDay28 {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String[] data = new String[n];
        for (int i = 0; i < n; i++) {
            data[i] = sc.nextLine();
        }
        Arrays.sort(data);
        for (String s : data) {
            if (s.contains("@gmail")) {
                System.out.println(s.split("\\s")[0]);
            }
        }
    }
}
