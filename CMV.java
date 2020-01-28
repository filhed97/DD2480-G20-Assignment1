import java.awt.Point;
import java.util.Arrays;
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

    //True iff there exists 2 points at a smaller distance than LENGTH1
    public boolean LIC0(){
      //Argument check
      if(param.LENGTH1 < 0){
        System.err.println("LIC0: negative LENGTH1");
        return false;
      }
      for (int i = 0; i < NUMPOINTS - 1; i++) {
        if(dist(POINTS[i],POINTS[i+1]) > param.LENGTH1){
          return true;
        }
      }
      return false;
    }

    public boolean LIC1(){
  		for(int i = 0; i<NUMPOINTS-2; i++){
  			Point a,b,c,centre;
  			a = POINTS[i];
  			b = POINTS[i+1];
  			c = POINTS[i+2];

  			double x = (a.getX() + b.getX() + c.getX())/3.0;
  			double y = (a.getY() + b.getY() + c.getY())/3.0;
  			centre = new Point(0,0);
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

    //True iff exists points in QPTS consecutive points
    //that are located in QUADS different quadrants.
    public boolean LIC4() {
        if (param.QPTS < 2 || param.QPTS > NUMPOINTS || param.QUADS < 1 || param.QUADS > 3) {
            System.err.println("LIC4: argument(s) out of bounds");
            return false;
        }
        //One entry per quadrant, true iff exists a point in the quadrant
        boolean[] quadUsed = new boolean[4];
        //Counter of the nb of quadrants set to true
        int nbUsed = 0;
        //The quadrant where the current point evaluated is
        int currQuad = 0;
        for (int i = 0; i < NUMPOINTS - param.QPTS; i++) {
            //Reset to false every new QPTS points
            Arrays.fill(quadUsed, Boolean.FALSE);
            nbUsed = 0;
            currQuad = 0;
            for (int j = i; j < i + param.QPTS; j++) {
                //Find the quad of the curr point
                //If it's the first in this quad increase nbUsed
                //and update the quadUsed entry
                currQuad = getQuad(POINTS[j]);
                if (currQuad == 0) {
                    System.err.println("LIC4: not quadrant found");
                    return false;
                }
                if (!quadUsed[currQuad - 1]) {
                    nbUsed++;
                    quadUsed[currQuad - 1] = true;
                }
            }
            if (nbUsed > param.QUADS) return true;
        }
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

    //-----> IMPORTANT <-----
    //If this implementation fails try LIC1 implementation
    //before trying to hard to solve this one.
    //
    //True iff 3 consecutive points sperated by APTS and BPTS respectively
    //are contained in a circle of radius RADIUS1
    public boolean LIC8(){
      //Argument check
      if(param.APTS < 1 || param.BPTS < 1 || param.RADIUS1 < 0 ||
      param.APTS + param.BPTS > NUMPOINTS - 3 ){
        System.err.println("LIC8: argument(s) out of bounds");
        return false;
      }
      if(NUMPOINTS < 5) return false;

      Point a, b, c;
      boolean inCircle;
      for (int i = 0; i < NUMPOINTS - param.APTS - param.BPTS - 2; i++) {
        a = POINTS[i];
        b = POINTS[i + param.APTS + 1];
        c = POINTS[i + param.APTS + param.BPTS + 2];
        inCircle = true;

        //The circle that passes exactly through a, b and c is the smallest circle
        //that contains the 3 points. Thus RADIUS1 has to be greater or equal
        //in order to be able to contain the 3 points.
        if(getRadiusCircle(a,b,c) <= param.RADIUS1) return true;
      }
      return false;
    }

    //Doesn't work for PI angle
    public boolean LIC9(){
  		for(int i = 0; i<NUMPOINTS-(param.CPTS+param.DPTS+2); i++){
  			Point a,b,c;
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

    //True iff exists 2 pairs of points, where the 2 points in each pair are separated by KPTS,
    //such that: the points of one pair are at a distance smaller than LENGTH1 and
    //the points of the other pair are at a distance greater than LENGTH2.
    public boolean LIC12() {
        //Argument check
        if (param.LENGTH1 < 0 || param.LENGTH2 < 0 || param.KPTS < 1) {
            System.err.println("LIC12: Argument(s) out of bounds");
            return false;
        }
        if (NUMPOINTS < 3) return false;
        //true iff distance is smaller than LENGTH1 // should be greater than LENGTH1
        boolean l1 = false;
        //true iff distance is greater than LENGTH2 // should be less than LENGTH2
        boolean l2 = false;
        //Current distance evaluated
        double d = 0;
        for (int i = 0; i < NUMPOINTS - param.KPTS - 1; i++) {
            d = dist(POINTS[i], POINTS[i + param.KPTS + 1]);
            if (d > param.LENGTH1) l1 = true;
            if (d < param.LENGTH2) l2 = true;
            if (l1 && l2) return true;
        }
        return false;
    }

    public boolean LIC13(){
		boolean condition1 = false;
		boolean condition2 = false;
        Point centre = null;
		for(int i = 0; i<NUMPOINTS-(param.APTS+param.BPTS +2); i++){
			Point a,b,c;
			a = POINTS[i];
			b = POINTS[i+param.APTS+1];
			c = POINTS[i+param.APTS+param.BPTS+2];

			double x = (a.getX() + b.getX() + c.getX())/3;
			double y = (a.getY() + b.getY() + c.getY())/3;
			centre = new Point(0,0);
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
    private double calculateTriangleArea(Point a, Point b, Point c){
      return Math.abs((a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX()*(a.getY() - b.getY()))/2);
    }

    //Calculates the angle between the lines a-b and b-c
    //This method doesn't work for if all points are on the X-axis
    public double calculateAngle(Point a, Point b, Point c){
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
