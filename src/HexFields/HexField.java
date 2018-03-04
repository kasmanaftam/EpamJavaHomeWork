package HexFields;

public class HexField {
    boolean[][] field;
    HexField(){
        field = new boolean[10][10];
    }
    HexField(int rows, int columns){
        field = new boolean[rows][columns];
    }
    private void InitField(){
        int rows = field.length;
        if(rows==0){
            System.out.println("There is no field");
            return;
        }
        int columns = field[0].length;
        if(columns==0){
            System.out.println("There is no field");
            return;
        }
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                field[i][j] = false;
            }
        }
    }
    public int getRows(){
        return field.length;
    }
    public int getColumns(){
        if(field.length==0){
            return 0;
        }
        return field[0].length;
    }
    public boolean isEmpty(int x, int y){
        return !field[y][x];
    }
    public void confineCell(int x, int y){
        field[y][x] = true;
    }
    public void freeCell(int x, int y){
        field[y][x] = false;
    }
}

class Entity{
    String name;
    HexField field;
    int x;
    int y;
    Entity(){
        name = "SampleName";
    }
    Entity(String name){
        this.name=name;
    }
    Entity(String name, HexField field){
        this.name = name;
        this.field = field;
        placeToField(field, 0,0);
    }
    Entity(String name, HexField field,int x, int y){
        this.name = name;
        this.field = field;
        placeToField(field, x,y);
    }
    public void placeToField(HexField field){
        this.field = field;
        int x = 0;
        int y = 0;
        while(!field.isEmpty(x,y)){
            if(y<field.getRows()-1){
                y++;
            } else {
                y = 0;
                if (x < field.getColumns() - 1){
                    x++;
                } else {
                    System.out.println("The field is full. Can't place the entity there.");
                }
            }
        }
        field.confineCell(x,y);
    }
    public void placeToField(HexField field, int x, int y){
        this.field = field;
        if(field.isEmpty(x,y)) {
            this.x = x;
            this.y = y;
            field.confineCell(x, y);
        } else {
            System.out.println("Can't place here");
        }
    }
    public int distance(int targetX, int targetY){
        int distanceY=Math.abs(targetY-y);
        int distanceX=targetX-x;
        int xyMoves=Math.abs(distanceY/2);
        if(distanceY%2==1){
            if(distanceX>0 && y%2==1 || distanceX<0 && y%2==0) xyMoves++;
        }
        distanceX = Math.abs(distanceX);
        if(distanceX>xyMoves){
            return distanceY+distanceX-xyMoves;
        }else{
            return distanceY;
        }
    }
    public void moveToSimple(int targetX, int targetY){
        // debug message
        if(targetX>=field.getColumns() || targetX < 0 || targetY>=field.getRows() || targetY <0){
            System.out.println("This point is out of area of current field");
            return;
        }
        System.out.println("Moving starts from: " + x + " " + y + ", ends at: " + targetX + " " + targetY);
        while(targetX!=x || targetY!=y){
            int diffX = targetX-x;
            int diffY = targetY-y;
            int nextX = x;
            int nextY = y;
            if(diffY!=0){
                if(diffX>0 && y%2==1){
                    nextX++;
                }
                if(diffX<0 && y%2==0){
                    nextX--;
                }
                if(diffY>0){
                    nextY++;
                }
                if(diffY<0){
                    nextY--;
                }
            } else {
                if(diffX>0){
                    nextX++;
                }
                if (diffX<0) {
                    nextX--;
                }
            }
            if(field.isEmpty(nextX, nextY)) {
                field.confineCell(nextX, nextY);
                field.freeCell(x, y);
                x = nextX;
                y = nextY;
                //debug message
                System.out.println("Next step is " + x + " " + y);
            } else {
                System.out.println("Cant't move further");
                return;
            }
        }
    }
}
