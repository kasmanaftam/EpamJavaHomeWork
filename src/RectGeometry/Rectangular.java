package RectGeometry;

import java.util.ArrayList;
import java.util.Arrays;

public class Rectangular {
    Point p1 = new Point();
    Point p2 = new Point();
    Point p3 = new Point();
    Point p4 = new Point();
    Point center = new Point();

    /* constructors*/
    Rectangular(int x1, int y1, int x2, int y2){
        p1.setCoordinates(x1, y2);
        p2.setCoordinates(x2, y2);
        p3.setCoordinates(x2, y1);
        p4.setCoordinates(x1, y1);
        center = crossing(p1, p3, p2, p4);
    }

    Rectangular(double x1, double y1, double x2, double y2){
        p1.setCoordinates(x1, y2);
        p2.setCoordinates(x2, y2);
        p3.setCoordinates(x2, y1);
        p4.setCoordinates(x1, y1);
        center = crossing(p1, p3, p2, p4);
    }

    Rectangular(double centerX, double centerY, double height, double length, double rotationAngle){
        this.center.setCoordinates(centerX,centerY);
        this.p1.setCoordinates(centerX-length*0.5, centerY+height*0.5);
        this.p2.setCoordinates(centerX+length*0.5, centerY+height*0.5);
        this.p3.setCoordinates(centerX+length*0.5, centerY-height*0.5);
        this.p4.setCoordinates(centerX-length*0.5, centerY-height*0.5);
        this.rotate(rotationAngle);
    }

    /*methods*/
    public void rotate(double angle){
        this.p1.rotation(center, angle);
        this.p2.rotation(center, angle);
        this.p3.rotation(center, angle);
        this.p4.rotation(center, angle);
    }
    public Point crossing (Point p11, Point p12, Point p21, Point p22) {

        double k1 = (p11.y - p12.y) / (p11.x - p12.x);
        double k2 = (p21.y - p22.y) / (p21.x - p22.x);
        double b1 = p11.y - k1 * p11.x;
        double b2 = p21.y - k2 * p21.x;
        double x = (b2 - b1) / (k1 - k2);
        double y = k1 * x + b1;
        if(k1==k2){
            if (b1!=b2){
                return new Point(Double.NaN, Double.NaN);
            } else {
                return new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
        }
        if(p11.x==p12.x){
            x = p11.x;
            y = k2*x+b2;
        }
        if(p21.x==p22.x){
            x = p21.x;
            y = k1*x+b1;
        }
        return new Point(x, y);
    }
    public boolean containsPoint(Point p){
        Point px = new Point(p.x+1, p.y);
        Point[] points = {p1,p2,p3,p4,p1};
        int res = 0;

        for(int i=0; i<4; i++){
            double yMin = Math.min(points[i].y, points[i+1].y);
            double yMax = Math.max(points[i].y, points[i+1].y);
            if(p.y<yMin || p.y>yMax) continue;
            Point inter = crossing(p,px,points[i],points[i+1]);
            if(inter.x == Double.POSITIVE_INFINITY) return false;
            double xMin = Math.min(points[i].x, points[i+1].x);
            double xMax = Math.max(points[i].x, points[i+1].x);
            if(inter.x == p.x) return false;
            if(inter.x>=xMin && inter.x<=xMax){
                int position = (p.x>inter.x)? 1:-1;
                if(res==0){
                    res = position;
                } else {
                    if(res!=position) return true;
                }
            }
        }
        return false;
    }
    public double triangleArea(Point a, Point b, Point c){
        double ab = a.distance(b);
        double bc = b.distance(c);
        double ca = c.distance(a);
        double p = (ab + bc + ca)*0.5;
        return Math.sqrt(p*(p-ab)*(p-bc)*(p-ca));
    }
    public double area(){
        return p1.distance(p2) * p1.distance(p4);
    }

    public double intersectionArea(Rectangular r){
        // init points arrays
        Point[] pointsA = {this.p1, this.p2, this.p3, this.p4, this.p1};
        Point[] pointsB = {r.p1, r.p2, r.p3, r.p4, r.p1};
        ArrayList<Point> areaPoints = new ArrayList<>();
        // find all contour points
        for(int i=0; i<4; i++){
            if(this.containsPoint(pointsB[i])){
                areaPoints.add(pointsB[i]);
            }
        }
        for(int i=0; i<4; i++){
            if(r.containsPoint(pointsA[i])){
                areaPoints.add(pointsA[i]);
            }
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                double[] xArr = {pointsA[i].x, pointsA[i+1].x, pointsB[j].x, pointsB[j+1].x};
                double[] yArr = {pointsA[i].y, pointsA[i+1].y, pointsB[j].y, pointsB[j+1].y};
                Arrays.sort(xArr);
                Arrays.sort(yArr);
                if(yArr[3]==yArr[2]) yArr[2] = yArr[1];
                if(xArr[3]==xArr[2]) xArr[2] = xArr[1];
                if(yArr[0]==yArr[1]) yArr[1] = yArr[2];
                if(xArr[0]==xArr[1]) xArr[1] = xArr[2];
                Point cross = crossing(pointsA[i], pointsA[i+1], pointsB[j], pointsB[j+1]);
                if( cross.x>=xArr[1] && cross.x<=xArr[2] &&
                    cross.y>=yArr[1] && cross.y<=yArr[2]){
                    areaPoints.add(cross);
                }
            }
        }
        // check the amount of points,
        if(areaPoints.size()==0) return -1.0;
        if(areaPoints.size()<3) return 0.0;
        //sort the points to get the continuous contour
        Point[] contour = areaPoints.toArray(new Point[areaPoints.size()]);
        int n = contour.length;
        for(int i=0; i<n-1; i++){
            for(int j=i+1; j<n; j++){
                Point cross = crossing(contour[i], contour[j], contour[(j+1)%n], contour[(j+2)%n]);
                if(this.containsPoint(cross) && (j+1)%n!=i && (j+2)%n!=i && (j+1)%n!=j && (j+2)%n!=j) {
                    continue;
                }else if(j!=i+1){
                    Point buf = contour[j];
                    contour[j]=contour[i+1];
                    contour[i+1] = buf;
                    break;
                }else{
                    break;
                }
            }
        }
        //calc contour area
        double area = 0.0;
        for(int i=0; i<contour.length-2;i++){
            area+=triangleArea(contour[0], contour[i+1], contour[i+2]);
        }
        return area;
    }
    @Override
    public String toString() {
        return  "p1: "+p1.x+" "+p1.y+"\n" +
                "p2: "+p2.x+" "+p2.y+"\n" +
                "p3: "+p3.x+" "+p3.y+"\n" +
                "p4: "+p4.x+" "+p4.y+"\n";
    }
}
class Point {
    public double x;
    public double y;
    Point(){
        this.x=Double.NaN;
        this.y=Double.NaN;
    }
    Point(double x, double y){
        this.x=x;
        this.y=y;
    }
    public void setCoordinates(double x, double y){
        this.x=x;
        this.y=y;
    }
    public void setCoordinates(int x, int y){
        this.x=x;
        this.y=y;
    }
    public double[] getCoordinates(){
        return new double[]{this.x, this.y};
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double distance(Point point){
        double xSquare = Math.pow(this.x-point.x, 2);
        double ySquare = Math.pow(this.y-point.y, 2);
        return Math.sqrt(xSquare + ySquare);
    }
    public void rotation(Point Center, double angle){
        //calc point coordinates with center point basis
        double xZero = this.x-Center.x;
        double yZero = this.y-Center.y;
        //calc sin and cos of rotation angle
        double sina = Math.sin(angle/180.0*Math.PI);
        double cosa = Math.cos(angle/180.0*Math.PI);
        //calc new coordinates of point
        this.x = Center.x + xZero*cosa - yZero*sina;
        this.y = Center.y + xZero*sina + yZero*cosa;
    }

    @Override
    public String toString(){
        return "Point coordinates x,y is: "+this.x +", "+this.y;
    }

    public boolean equals(Point point){
        return  point.x==this.x &&
                point.y==this.y;
    }
}