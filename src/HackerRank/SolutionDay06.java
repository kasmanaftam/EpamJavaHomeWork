package HackerRank;

import java.util.Scanner;

public class SolutionDay06 {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        String[] str = new String[n];
        for (int i = 0; i < n; i++) {
            str[i] = scan.nextLine();
        }
        scan.close();
        for (String s : str) {
            String odd = "";
            String even = "";
            for (int i = 0; i < s.length(); i++) {
                if (i % 2 == 0) even += s.substring(i, i + 1);
                else odd += s.substring(i, i + 1);
            }
            System.out.println(even + " " + odd);
        }
    }
}
