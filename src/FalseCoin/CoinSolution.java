package FalseCoin;

public class CoinSolution {

    public static void main(String[] args){
        int trueWeigth=10;
        int falseWeigth=11;
        for(int i=0; i<12;i++){
            int falseCoinIndex = findCoin(createCoinsHeap(trueWeigth,falseWeigth,i));
            System.out.println("False coin is heavier. " +
                    "Real index of false coin is " + i +
                    ", evaluated index is " + falseCoinIndex
            );
        }
        falseWeigth = 9;
        for(int i=0; i<12;i++){
            int falseCoinIndex = findCoin(createCoinsHeap(trueWeigth,falseWeigth,i));
            System.out.println("False coin is heavier. " +
                    "Real index of false coin is " + i +
                    ", evaluated index is " + falseCoinIndex
            );
        }

    }
    static int[] createCoinsHeap(int trueWeigth, int falseWeigth, int falseIndex){
        int[] array = new int[12];
        for(int i=0; i<12; i++){
            if(i==falseIndex){
                array[i]=falseWeigth;
            } else {
                array[i]=trueWeigth;
            }
        }
        return array;
    }
    //returns the index of false coin in array
    static int findCoin(int[] coinWeigths){
        if(coinWeigths.length!=12){
            System.out.println("Wrong heap size!");
            return -1;
        }
        //initialize first heaps;
        int[][] heaps = new int[3][4];
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                heaps[i][j] = coinWeigths[i*4+j];
            }
        }
        //initialize support variables
        int[] finalHeap;
        boolean falseCoinHeavier = false;
        int shift=0;
        //go!
        int firstCompare = compareHeaps(heaps[0], heaps[1]);
        if(firstCompare==0){
            finalHeap = heaps[2];
            shift = 8;
        } else {
            int[] bufHeap = new int[3];
            for(int i=0; i<3; i++) bufHeap[i]=heaps[0][i];
            for(int i=0; i<3; i++) heaps[0][i]=heaps[2][i];
            int buf=heaps[0][3];
            heaps[0][3] = heaps[1][3];
            heaps[1][3] = buf;
            int secondCompare = compareHeaps(heaps[0], heaps[1]);

            if(secondCompare == firstCompare){
                finalHeap = new int[3];
                for(int i=0; i<3; i++) finalHeap[i]=heaps[1][i];
                shift = 4;
                if(firstCompare>0) {
                    falseCoinHeavier = false;
                } else {
                    falseCoinHeavier = true;
                }
            }else if(secondCompare == 0){
                finalHeap = bufHeap;
                shift = 0;
                if(firstCompare<0) {
                    falseCoinHeavier = false;
                } else {
                    falseCoinHeavier = true;
                }
            } else {
                finalHeap = new int[2];
                finalHeap[0] = heaps[0][3];
                finalHeap[1] = heaps[1][3];
            }
        }
        if(finalHeap.length==2){
            if(finalHeap[0]!=heaps[2][3]){
                return 7;
            } else {
                return 3;
            }
        }
        if(finalHeap.length==3){
            if(finalHeap[0]==finalHeap[1]){
                return 2+shift;
            }
            if((finalHeap[0]<finalHeap[1])^falseCoinHeavier ) {
                return 0 + shift;
            } else {
                return 1+shift;
            }
        }
        if(finalHeap.length==4){
            if(finalHeap[0]==finalHeap[1]){
                if(finalHeap[2]==finalHeap[0]){
                    return 11;
                } else {
                    return 10;
                }
            } else {
                if(finalHeap[0]==finalHeap[2]){
                    return 9;
                } else {
                    return 8;
                }
            }
        }
        return -1;
    }
    static int compareHeaps(int[] a, int[] b){
        int aWeigth =0;
        int bWeigth =0;
        for(int w : a) aWeigth+=w;
        for(int w : b) bWeigth+=w;
        if(aWeigth==bWeigth) return 0;
        return (aWeigth>bWeigth)? 1:-1;
    }
}
