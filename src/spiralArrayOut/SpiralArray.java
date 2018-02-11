package spiralArrayOut;

import java.util.Scanner;

public class SpiralArray {

    public static void main(String[] args){
        System.out.println("Enter array size (rows, columns):");
        Scanner sc = new Scanner(System.in);
        int rows = sc.nextInt();
        int columns = sc.nextInt();
        int[][] array = new int[rows][columns];
        System.out.println("Enter the array values");
        for(int i=0; i<columns; i++){
            for(int j=0; j<rows; j++){
                array[i][j] = sc.nextInt();
            }
        }

        System.out.println("Initial array is:");
        print2dArray(array);
        System.out.println("Spiral sequence of initial array is:");
        spiralPrint2dArray(array);
    }

    static void spiralPrint2dArray(int[][] array){
        int rows = array.length;
        if(rows==0){
            System.out.println("Array is empty");
            return;
        }
        int columns = array[0].length;
        if(columns==0){
            System.out.println("Array is empty");
            return;
        }
        int x=0;
        int y=0;
        int xInc=1;
        int yInc=1;
        boolean moveX = true;
        while(rows>0 && columns>0){
            if(moveX){
                for(int i=0; i<columns; i++){
                    System.out.print(array[y][x] + " ");
                    x+=xInc;
                }
                rows--;
                x-=xInc;
                y+=yInc;
                xInc*=-1;
                moveX=!moveX;
            } else {
                for(int i=0; i<rows; i++){
                    System.out.print(array[y][x] + " ");
                    y+=yInc;
                }
                columns--;
                y-=yInc;
                x+=xInc;
                yInc*=-1;
                moveX=!moveX;
            }
        }
    }

    static void print2dArray(int array[][]){
        if(array.length==0){
            System.out.println("Array is empty");
            return;
        }
        if(array[0].length==0){
            System.out.println("Array is empty");
            return;
        }
        for(int[] row : array){
            for(int e: row){
                System.out.print(e + " ");
            }
            System.out.println("");
        }
    }
}
