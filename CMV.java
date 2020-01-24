import java.awt.Point;
import java.lang.Math;

public class CMV{

    private int NUMPOINTS;
    private Point [] POINTS;
    private Parameters param;


    public CMV(int NUMPOINTS,Point[] POINTS, Parameters param){

      this.NUMPOINTS = NUMPOINTS;
      this.POINTS = POINTS;
      this.param = param;

    }

    public void fillCMV(Boolean[] CMV){

    }

    private boolean LIC0(){
      return false;
    }

    private boolean LIC1(){
      return false;
    }

    //Returns true if there exists three consecutive points which form an angle 
    //larger than PI + Epsilon or smaller than PI - epsilon
    private boolean LIC2(){
      if(param.EPSILON < 0 || param.EPSILON > Math.PI){
        return false;
      }

      Point a, b, c;
      for(int i = 0; i < NUMPOINTS - 2; i++){
        a = POINTS[i];
        b = POINTS[i + 1];
        c = POINTS[i + 2];
        double angle = calculateAngle(a, b, c);
        if(angle > (Math.PI + param.EPSILON) || angle < (Math.PI - param.EPSILON)){
          return true;
        }
      }
      return false;
    }

    private boolean LIC3(){
      return false;
    }

    private boolean LIC4(){
      return false;
    }

    private boolean LIC5(){
      return false;
    }

    //Returns true if there exists a set of NPTS consecutive points where one of the points lies a distance larger
    //then DIST away from the line passing through the first and last point of the set
    private boolean LIC6(){
      if(NUMPOINTS < 3){
        return false;
      }
      if(param.DIST < 0 || param.NPTS < 3 || param.NPTS > NUMPOINTS){
        return false;
      }

      Point start, end;
      for(int i = 0; i < NUMPOINTS - param.NPTS; i++){
        start = POINTS[i];
        end = POINTS[i + param.NPTS];
        if(start.equals(end)){
          for(int j = i + 1; j < i + param.NPTS - 1; j++){
            double dist = distancePoint(POINTS[j], start);
            if(dist > param.DIST){
              return true;
            }
          }
        } else {
          for(int j = i + 1; j < i + param.NPTS - 1; j++){
            double dist = distanceToLine(start, end, POINTS[j]);
            if(dist > param.DIST){
              return true;
            }
          }
        }
      }
      return false;
    }

    private boolean LIC7(){
      return false;
    }

    private boolean LIC8(){
      return false;
    }

    private boolean LIC9(){
      return false;
    }

    //Return true if there exists three points seperated by param.EPTS and param.FPTS respictively
    //which together create a traingle with an area larger than AREA1
    private boolean LIC10(){
      if(NUMPOINTS < 5){
        return false;
      }
      if(param.EPTS < 1 || param.FPTS < 1 || (param.EPTS + param.FPTS > NUMPOINTS-3)){
        return false;
      }

      Point a, b, c;
      for(int i = 0; i < NUMPOINTS - param.EPTS - param.FPTS; i++){
        a = POINTS[i];
        b = POINTS[i + param.EPTS];
        c = POINTS[i + param.EPTS + param.FPTS];
        double area = calculateTriangleArea(a, b, c);
        if(area > param.AREA1){
          return true;
        }
      }
      return false;
    }

    private boolean LIC11(){
      return false;
    }

    private boolean LIC12(){
      return false;
    }

    private boolean LIC13(){
      return false;
    }

    //Return true if there exists three points seperated by EPTS and FPTS respictively
    //which together create a traingle with an area larger than AREA1 and there also three points seperated
    //by EPTS and FPTS respictively which together create a traingle with an area smaller than AREA2
    private boolean LIC14(){
      if(NUMPOINTS < 5){
        return false;
      }
      if(param.EPTS < 1 || param.FPTS < 1 || (param.EPTS + param.FPTS > NUMPOINTS-3) || param.AREA2 < 0){
        return false;
      }

      boolean largerExists = false, smallerExists = false;
      Point a, b, c;
      for(int i = 0; i < NUMPOINTS - param.EPTS - param.FPTS; i++){
        a = POINTS[i];
        b = POINTS[i + param.EPTS];
        c = POINTS[i + param.EPTS + param.FPTS];
        double area = calculateTriangleArea(a, b, c);
        if(area > param.AREA1){
          largerExists = true;
        } else if (area < param.AREA2){
          smallerExists = true;
        }
      }

      if(largerExists && smallerExists){
        return true;
      }
      return false;
    }

    //Calcultaes the area of the traignle defined by the three points a, b, c
    private double calculateTriangleArea(Point a, Point b, Point c){
      return Math.abs((a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX()*(a.getY() - b.getY()))/2);
    }

    //Calculates the angle between the lines a-b and b-c
    private double calculateAngle(Point a, Point b, Point c){
      return Math.atan2(c.getY() - b.getY(), c.getX() - b.getX()) - Math.atan2(a.getY() - b.getY(), c.getX() - c.getY());
    }

    //Calculates the distance between two points
    private double distancePoint(Point a, Point b){
      return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    //Calcultaes the distance from point p to the line passing through startPoint and endPoint
    private double distanceToLine(Point startPoint, Point endPoint, Point p){
      return (Math.abs(p.getX() * (endPoint.getY() - startPoint.getY()) - p.getY() * (endPoint.getX() - startPoint.getX()) + endPoint.getX() * startPoint.getY() - endPoint.getY() * startPoint.getX()))/distancePoint(endPoint, startPoint);
    }
}
