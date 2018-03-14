package HackerRank;

import java.util.Scanner;

public class SolutionDay16 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String S = in.next();
        int i = 0;
        try {
            i = Integer.parseInt(S);
            System.out.println(i);
        } catch (NumberFormatException pe) {
            System.out.println("Bad String");
        }
    }
}