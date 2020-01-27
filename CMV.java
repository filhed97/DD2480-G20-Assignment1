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

    private boolean LIC2(){
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

    private boolean LIC6(){
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

    private boolean LIC10(){
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

    private boolean LIC14(){
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
