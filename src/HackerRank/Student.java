package HackerRank;

class Student extends Person2 {
    private int[] testScores;

    /*
    *   Class Constructor
    *
    *   @param firstName - A string denoting the Person's first name.
    *   @param lastName - A string denoting the Person's last name.
    *   @param id - An integer denoting the Person's ID number.
    *   @param scores - An array of integers denoting the Person's test scores.
    */
    // Write your constructor here
    Student(String firstName, String lastName, int idNumber, int[] testScores) {
        super(firstName, lastName, idNumber);
        this.testScores = testScores;
    }

    /*
    *   Method Name: calculate
    *   @return A character denoting the grade.
    */
    // Write your method here
    public char calculate() {
        int score = 0;
        for (int i : testScores) {
            score += i;
        }
        score /= testScores.length;
        if (score >= 40 && score < 55) return 'D';
        if (score >= 55 && score < 70) return 'P';
        if (score >= 70 && score < 80) return 'A';
        if (score >= 80 && score < 90) return 'E';
        if (score >= 90 && score <= 100) return 'O';
        return 'T';
    }
}
