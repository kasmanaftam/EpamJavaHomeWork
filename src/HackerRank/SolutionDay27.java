package HackerRank;

import java.util.Scanner;

public class SolutionDay27 {
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        int testNum = 5; //number of tests
        boolean[] results = {true, false, true, false, true};
        System.out.println(testNum);

        for (int i = 0; i < testNum; i++) {
            int numberOfStudents = (int) (Math.random() * 199.0 + 1.0);
            int arrivingStudents = (int) (Math.random() * (numberOfStudents - 1) + 1);
            System.out.println(numberOfStudents + " " + arrivingStudents);
            int arrivingCount = 0;
            for (int j = 0; j < numberOfStudents; j++) {
                double chance = Math.random();
                int time = (int) (15.0 * Math.random());
                if (!results[i]) {
                    if (chance < 0.5 && arrivingStudents - arrivingCount <= numberOfStudents - j - 1) {
                        System.out.print(time + 1 + " ");
                    } else {
                        System.out.print(-1 * time + " ");
                        arrivingCount++;
                    }
                } else {
                    if (chance > 0.5 && arrivingStudents - 1 > arrivingCount) {
                        System.out.print(-1 * time + " ");
                        arrivingCount++;
                    } else {
                        System.out.print(time + 1 + " ");
                    }
                }
            }
            System.out.println("");
        }
    }
}
