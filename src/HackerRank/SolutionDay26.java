package HackerRank;

import java.util.Scanner;

public class SolutionDay26 {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int actDay = sc.nextInt();
        int actMonth = sc.nextInt();
        int actYear = sc.nextInt();

        int expDay = sc.nextInt();
        int expMonth = sc.nextInt();
        int expYear = sc.nextInt();

        int fine = 0;
        if (actYear > expYear) fine = 10000;
        if (actYear == expYear) {
            if (actMonth == expMonth && actDay > expDay) fine = 15 * (actDay - expDay);
            if (actMonth > expMonth) fine = 500 * (actMonth - expMonth);
        }
        System.out.println(fine);
    }
}
