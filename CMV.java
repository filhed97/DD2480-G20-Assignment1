import java.awt.Point;
import java.util.Arrays;
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

    //True iff there exists 2 points at a smaller distance than LENGTH1
    public boolean LIC0(){
      for (int i = 0; i < NUMPOINTS - 1; i++) {
        if(dist(POINTS[i],POINTS[i+1]) > param.LENGTH1){
          return true;
        }
      }
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

    //Returns true if there exists three consecutive points which form an angle
    //larger than PI + Epsilon or smaller than PI - epsilon
    public boolean LIC2(){
		  if(param.EPSILON < 0 || param.EPSILON > Math.PI){
        return false;
      }


      Point2D.Double a, b, c;
      for(int i = 0; i < NUMPOINTS - 2; i++){
        a = POINTS[i];
        b = POINTS[i + 1];
        c = POINTS[i + 2];
        if(a.equals(b) || b.equals(c)){
          continue;
        }
        double angle = calculateAngle(a, b, c);
        if(angle == Math.PI){
          return false;
        }
        if(angle < 0){
          angle += 2 * Math.PI;
        }
        if(angle > (Math.PI + param.EPSILON) || angle < (Math.PI - param.EPSILON)){
          return true;
        }
      }
      return false;
    }

    public boolean LIC3(){
		if (NUMPOINTS < 3) {
            return false;
        }

        Point2D.Double a, b, c;
        for (int i = 0; i < NUMPOINTS - 2; i++) {
            a = POINTS[i];
            b = POINTS[i + 1];
            c = POINTS[i + 2];

            double triangleArea = calculateTriangleArea(a, b, c);

            if (triangleArea > param.AREA1) {
                return true;
            }
        }
        return false;
    }

    //True iff exists points in QPTS consecutive points
    //that are located in QUADS different quadrants.
    public boolean LIC4() {
        //One entry per quadrant, true iff exists a point in the quadrant
        boolean[] quadUsed = new boolean[4];
        //Counter of the nb of quadrants set to true
        int nbUsed = 0;
        //The quadrant where the current point evaluated is
        int currQuad = 0;
        for (int i = 0; i < NUMPOINTS - param.QPTS + 1; i++) {
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

    //Returns true if there exists a set of NPTS consecutive points where one of the points lies a distance larger
    //then DIST away from the line passing through the first and last point of the set
    public boolean LIC6(){
		  if(NUMPOINTS < 3){
        return false;
      }
      if(param.DIST < 0 || param.NPTS < 3 || param.NPTS > NUMPOINTS){
        return false;
      }

      Point2D.Double start, end;

      for(int i = 0; i < NUMPOINTS - param.NPTS + 1; i++){
        start = POINTS[i];
        end = POINTS[i + param.NPTS - 1];

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
		if (NUMPOINTS < 3) {
            return false;
        }

        Point2D.Double a, b;
        for (int i = 0; i < NUMPOINTS - 1 - param.KPTS; i++) {

            a = POINTS[i];
            b = POINTS[i + param.KPTS + 1];
            double distance = distancePoint(a, b);

            if (distance > param.LENGTH1) {
				return true;
            }
        }
        return false;
    }


    //True iff 3 consecutive points sperated by APTS and BPTS respectively
    //are contained in a circle of radius RADIUS1
    public boolean LIC8(){
      if(NUMPOINTS < 5) return false;

      Point2D.Double a, b, c;
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
		if(NUMPOINTS < 5){
        return false;
      }
      if(param.EPTS < 1 || param.FPTS < 1 || (param.EPTS + param.FPTS > NUMPOINTS-3)){
        return false;
      }

      Point2D.Double a, b, c;
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
		if (NUMPOINTS < 3) {
            return false;
        }

        Point2D.Double a, b;
        for (int i = 0; i < NUMPOINTS - 1 - param.GPTS; i++) {
				a = POINTS[i];
				b = POINTS[i + param.GPTS + 1];

				if (b.getX() - a.getX() < 0) {
					return true;
				}
        }
        return false;
    }

    //True iff exists 2 pairs of points, where the 2 points in each pair are separated by KPTS,
    //such that: the points of one pair are at a distance smaller than LENGTH1 and
    //the points of the other pair are at a distance greater than LENGTH2.
    public boolean LIC12() {
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
		if(NUMPOINTS < 5){
        return false;
      }
      if(param.EPTS < 1 || param.FPTS < 1 || (param.EPTS + param.FPTS > NUMPOINTS-3) || param.AREA2 < 0){
        return false;
      }

      boolean largerExists = false, smallerExists = false;
      Point2D.Double a, b, c;
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
    public double calculateTriangleArea(Point2D.Double a, Point2D.Double b, Point2D.Double c){
      return Math.abs((a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX()*(a.getY() - b.getY()))/2);
    }

    //This method doesn't work for if all points are on the X-axis
    public double calculateAngle(Point2D.Double a, Point2D.Double b, Point2D.Double c){
      return Math.atan2(c.getY() - b.getY(), c.getX() - b.getX()) - Math.atan2(a.getY() - b.getY(), a.getX() - c.getY());
    }

    //Calculates the distance between two points
    public double distancePoint(Point2D.Double a, Point2D.Double b){
      return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    //Calcultaes the distance from point p to the line passing through startPoint and endPoint
    public double distanceToLine(Point2D.Double startPoint, Point2D.Double endPoint, Point2D.Double p){
      return (Math.abs(p.getX() * (endPoint.getY() - startPoint.getY()) - p.getY() * (endPoint.getX() - startPoint.getX()) + endPoint.getX() * startPoint.getY() - endPoint.getY() * startPoint.getX()))/distancePoint(endPoint, startPoint);
    }

    //Utilitary function for LIC0 and LIC8
    //Compute the Euclidean distance between Points a and b
    public double dist(Point2D.Double a, Point2D.Double b){
      return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY()-b.getY(),2));
    }

    //Utilitary function for LIC4. Finds in which quadrant
    //the Point a is. Returns 1, 2, 3, 4 or 0 if none found.
    public int getQuad(Point2D.Double a){
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
    public double getRadiusCircle(Point2D.Double a, Point2D.Double b, Point2D.Double c){
      double l1 = dist(a,b);
      double l2 = dist(a,c);
      double l3 = dist(b,c);
      double r = l1 * l2 * l3 / Math.sqrt((l1+l2+l3)*(l2+l3-l1)*(l3+l1-l2)*(l1+l2-l3));
      return r;
    }
}
