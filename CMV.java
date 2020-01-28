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

    //Returns a new array implementing the cmv
    //where each entry is true iff the relevant LIC is true
    public boolean[] DECIDE(){
      boolean [] cmv = new boolean[15];
      cmv[0] = LIC0();
      cmv[1] = LIC1();
      cmv[2] = LIC2();
      cmv[3] = LIC3();
      cmv[4] = LIC4();
      cmv[5] = LIC5();
      cmv[6] = LIC6();
      cmv[7] = LIC7();
      cmv[8] = LIC8();
      cmv[9] = LIC9();
      cmv[10] = LIC10();
      cmv[11] = LIC11();
      cmv[12] = LIC12();
      cmv[13] = LIC13();
      cmv[14] = LIC14();
      return cmv;
    }

    public boolean LIC0(){
      return false;
    }

    public boolean LIC1(){
      return false;
    }

    public boolean LIC2(){
      System.out.println("POINTS 1 = " + POINTS[0]);
      System.out.println("POINTS 2 = " + POINTS[1]);
      System.out.println("POINTS 3 = " + POINTS[2]);
      System.out.println("eplsilon " + param.EPSILON);
		if(param.EPSILON < 0 || param.EPSILON > Math.PI){
        return false;
      }


      Point a, b, c;
      for(int i = 0; i < NUMPOINTS - 2; i++){
        a = POINTS[i];
        b = POINTS[i + 1];
        c = POINTS[i + 2];
        double angle = calculateAngle(a, b, c);
        if(angle < 0){
          angle += 2 * Math.PI;
        }
        System.out.println(angle);
        if(angle > (Math.PI + param.EPSILON) || angle < (Math.PI - param.EPSILON)){
          return true;
        }
      }
      return false;
    }

    public boolean LIC3(){
      return false;
    }

    public boolean LIC4(){
      return false;
    }

    public boolean LIC5(){
      return false;
    }

    public boolean LIC6(){
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

    public boolean LIC7(){
      return false;
    }

    public boolean LIC8(){
      return false;
    }

    public boolean LIC9(){
      return false;
    }

    public boolean LIC10(){
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

    public boolean LIC11(){
      return false;
    }

    public boolean LIC12(){
      return false;
    }

    public boolean LIC13(){
      return false;
    }

    public boolean LIC14(){
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
      return Math.atan2(c.getY() - b.getY(), c.getX() - b.getX()) - Math.atan2(a.getY() - b.getY(), a.getX() - b.getX());
    }

    //Calculates the distance between two points
    private double distancePoint(Point a, Point b){
      return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    //Calcultaes the distance from point p to the line passing through startPoint and endPoint
    private double distanceToLine(Point startPoint, Point endPoint, Point p){
      return (Math.abs(p.getX() * (endPoint.getY() - startPoint.getY()) - p.getY() * (endPoint.getX() - startPoint.getX()) + endPoint.getX() * startPoint.getY() - endPoint.getY() * startPoint.getX()))/distancePoint(endPoint, startPoint);
    }

    //Utilitary function for LIC0 and LIC8
    //Compute the Euclidean distance between Points a and b
    private double dist(Point a, Point b){
      return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY()-b.getY(),2));
    }

    //Utilitary function for LIC4. Finds in which quadrant
    //the Point a is. Returns 1, 2, 3, 4 or 0 if none found.
    private int getQuad(Point a){
      double x = a.getX();
      double y = a.getY();
      if(x >= 0 && y >= 0) return 1; //both axis included plus (0,0)
      if(x >= 0 && y < 0) return 2; //x axis included without (0,0)
      if(x < 0 && y <= 0) return 3; //y axis included without (0,0)
      if(x < 0 && y > 0) return 4; //no axis included
      else return 0;
    }

    //Utilitary function for LIC8.
    //Computes the radius of the circumcircle of the triangle formed by a,b and c,
    //i.e. the radius of the circle that passes through a, b and c.
    //The formula comes from https://www.mathopenref.com/trianglecircumcircle.html
    private double getRadiusCircle(Point a, Point b, Point c){
      double l1 = dist(a,b);
      double l2 = dist(a,c);
      double l3 = dist(b,c);
      double r = l1 * l2 * l3 / Math.sqrt((l1+l2+l3)*(l2+l3-l1)*(l3+l1-l2)*(l1+l2-l3));
      return r;
    }
}
