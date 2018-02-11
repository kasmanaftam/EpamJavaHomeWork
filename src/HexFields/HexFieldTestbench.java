package HexFields;

public class HexFieldTestbench {
    public static void main(String[] args) {
        HexField field = new HexField(5, 5);
        Entity hooman = new Entity("Veniamin", field, 1, 4);
        System.out.println(hooman.distance(4,0));
        hooman.moveToSimple(4, 0);
        System.out.println(hooman.distance(0,0));
        hooman.moveToSimple(0, 0);
        System.out.println(hooman.distance(1,4));
        hooman.moveToSimple(1, 4);
        System.out.println(hooman.distance(4,4));
        hooman.moveToSimple(4, 4);
        System.out.println(hooman.distance(0,0));
        hooman.moveToSimple(0, 0);
    }
}
