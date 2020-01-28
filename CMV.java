import java.awt.geom.Point2D;
import java.lang.Math;

public class CMV{

    private int NUMPOINTS;
    private Point2D.Double [] POINTS;
    private Parameters param;


    public CMV(int NUMPOINTS,Point2D.Double[] POINTS, Parameters param){

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
  		for(int i = 0; i<NUMPOINTS-2; i++){
  			Point2D.Double a,b,c,centre;
  			a = POINTS[i];
  			b = POINTS[i+1];
  			c = POINTS[i+2];

  			double x = (a.getX() + b.getX() + c.getX())/3.0;
  			double y = (a.getY() + b.getY() + c.getY())/3.0;
  			centre = new Point2D.Double(0,0);
  			centre.setLocation(x,y);

  			//calculate all distances and compare to radius
  			//if one point is outside circle centered around 3 points with spec. radius, LIC is true.
  			if(centre.distance(a) > param.RADIUS1
  			||	centre.distance(b) > param.RADIUS1
  			||	centre.distance(c) > param.RADIUS1) return true;
		  }
        return false;
    }

    public boolean LIC2(){
      return false;
    }

    public boolean LIC3(){
      return false;
    }

    public boolean LIC4(){
      return false;
    }


    public boolean LIC5(){
		for(int i = 0; i<NUMPOINTS-1; i++){
			if(POINTS[i+1].getX()-POINTS[i].getX() < 0)
				return true;
		}
        return false;
    }

    public boolean LIC6(){
      return false;
    }

    public boolean LIC7(){
      return false;
    }

    public boolean LIC8(){
      return false;
    }

    //Doesn't work for PI angle
    public boolean LIC9(){
  		for(int i = 0; i<NUMPOINTS-(param.CPTS+param.DPTS+2); i++){
  			Point2D.Double a,b,c;
  			a = POINTS[i];
  			b = POINTS[i+param.CPTS+1];
  			c = POINTS[i+param.CPTS+param.DPTS+2];
  			double angle = calculateAngle(a, b, c);
  			if(angle > (Math.PI + param.EPSILON) || angle < (Math.PI - param.EPSILON))
  				return true;
  		}
        return false;
    }

    public boolean LIC10(){
      return false;
    }

    public boolean LIC11(){
      return false;
    }

    public boolean LIC12(){
      return false;
    }

    public boolean LIC13(){
		boolean condition1 = false;
		boolean condition2 = false;
        Point2D.Double centre = null;
		for(int i = 0; i<NUMPOINTS-(param.APTS+param.BPTS +2); i++){
			Point2D.Double a,b,c;
			a = POINTS[i];
			b = POINTS[i+param.APTS+1];
			c = POINTS[i+param.APTS+param.BPTS+2];

			double x = (a.getX() + b.getX() + c.getX())/3;
			double y = (a.getY() + b.getY() + c.getY())/3;
			centre = new Point2D.Double(0,0);
			centre.setLocation(x,y);

			if(centre.distance(a) > param.RADIUS1
			||	centre.distance(b) > param.RADIUS1
			||	centre.distance(c) > param.RADIUS1)
				condition1 = true;

			if(centre.distance(a) <= param.RADIUS2
			&&	centre.distance(b) <= param.RADIUS2
			&&	centre.distance(c) <= param.RADIUS2)
				condition2 = true;
		}
		if(condition1 && condition2)
			return true;

		return false;
  }

    public boolean LIC14(){
      return false;
    }

    //Calcultaes the area of the traignle defined by the three points a, b, c
    private double calculateTriangleArea(Point2D.Double a, Point2D.Double b, Point2D.Double c){
      return Math.abs((a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX()*(a.getY() - b.getY()))/2);
    }

    //Calculates the angle between the lines a-b and b-c
    //This method doesn't work for if all points are on the X-axis
    public double calculateAngle(Point2D.Double a, Point2D.Double b, Point2D.Double c){
      return Math.atan2(c.getY() - b.getY(), c.getX() - b.getX()) - Math.atan2(a.getY() - b.getY(), c.getX() - c.getY());
    }

    //Calculates the distance between two points
    private double distancePoint(Point2D.Double a, Point2D.Double b){
      return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    //Calcultaes the distance from point p to the line passing through startPoint and endPoint
    private double distanceToLine(Point2D.Double startPoint, Point2D.Double endPoint, Point2D.Double p){
      return (Math.abs(p.getX() * (endPoint.getY() - startPoint.getY()) - p.getY() * (endPoint.getX() - startPoint.getX()) + endPoint.getX() * startPoint.getY() - endPoint.getY() * startPoint.getX()))/distancePoint(endPoint, startPoint);
    }

    //Utilitary function for LIC0 and LIC8
    //Compute the Euclidean distance between Points a and b
    private double dist(Point2D.Double a, Point2D.Double b){
      return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY()-b.getY(),2));
    }

    //Utilitary function for LIC4. Finds in which quadrant
    //the Point a is. Returns 1, 2, 3, 4 or 0 if none found.
    private int getQuad(Point2D.Double a){
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
    private double getRadiusCircle(Point2D.Double a, Point2D.Double b, Point2D.Double c){
      double l1 = dist(a,b);
      double l2 = dist(a,c);
      double l3 = dist(b,c);
      double r = l1 * l2 * l3 / Math.sqrt((l1+l2+l3)*(l2+l3-l1)*(l3+l1-l2)*(l1+l2-l3));
      return r;
    }
}
