package HackerRank;

import java.util.Scanner;

public class SolutionDay04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int age = sc.nextInt();
            Person p = new Person(age);
            p.amIOld();
            for (int j = 0; j < 3; j++) {
                p.yearPasses();
            }
            p.amIOld();
            System.out.println();
        }
        sc.close();
    }
}
class Person {
    private int age;

    public Person(int initialAge) {
        // Add some more code to run some checks on initialAge
        if (initialAge < 0) {
            this.age = 0;
            System.out.println("Age is not valid, setting age to 0.");
        } else {
            this.age = initialAge;
        }
    }

    public void amIOld() {
        // Write code determining if this person's age is old and print the correct statement:
        String arg = "";
        int age = this.age;
        if (age < 13) arg = "You are young.";
        if (age >= 13 && age < 18) arg = "You are a teenager.";
        if (age >= 18) arg = "You are old.";
        System.out.println(arg);
    }

    public void yearPasses() {
        // Increment this person's age.
        this.age++;
    }
}