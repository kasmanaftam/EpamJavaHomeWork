package RectGeometry;

import java.text.DecimalFormat;

public class RectTestbench {
    public static void main(String args[]){
        DecimalFormat df = new DecimalFormat("0.00");
        Rectangular r1 = new Rectangular(0,0, 2, 20, 0);
        Rectangular r2 = new Rectangular(0,0, 2, 20, 15);
        double area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 15.45");

        r1 = new Rectangular(0,0, 2, 20, 0);
        r2 = new Rectangular(5,0, 2, 20, 0);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 30");

        r1 = new Rectangular(0,0, 2, 20, 0);
        r2 = new Rectangular(0,1, 2, 20, 0);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 20");

        r1 = new Rectangular(0,0, 10, 10, 0);
        r2 = new Rectangular(0,0, 10, 10, 45);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 82.84");

        r1 = new Rectangular(0,0, 2, 10, 45);
        r2 = new Rectangular(1,1, 2, 10, 45);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 17.17");

        r1 = new Rectangular(1,1, 2, 2, 0);
        r2 = new Rectangular(1,0, Math.sqrt(2), Math.sqrt(2), 45);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 1");

        r1 = new Rectangular(1,1, 2, 3, 0);
        r2 = new Rectangular(1,1, 3, 2, 0);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 4");

        r1 = new Rectangular(1,1, 2, 3, 0);
        r2 = new Rectangular(1,0, 2, 2, 0);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 2");

        r1 = new Rectangular(1,1, 2, 3, 0);
        r2 = new Rectangular(1,0, 2, 2, 0);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 2");

        r1 = new Rectangular(1,1, 2, 3, 0);
        r2 = new Rectangular(1,0, 2, 3, 0);
        area = r1.intersectionArea(r2);
        System.out.println("Intersection area is " + df.format(area) + ". must be 3");
    }
}
