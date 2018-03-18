package HackerRank;

class Person2 {
    protected String firstName;
    protected String lastName;
    protected int idNumber;

    // Constructor
    Person2(String firstName, String lastName, int identification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = identification;
    }

    // Print person data
    public void printPerson() {
        System.out.println(
                "Name: " + lastName + ", " + firstName
                        + "\nID: " + idNumber);
    }

}