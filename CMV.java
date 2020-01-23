import java.awt.Point;
import java.util.Arrays;

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

    //True iff there exists 2 points at a smaller distance than LENGTH1
    private boolean LIC0(){
      //Argument check
      if(param.LENGTH1 < 0){
        System.err.println("LIC0: negative LENGTH1");
        return false;
      }
      for (int i = 0; i < NUMPOINTS - 1; i++) {
        if(dist(POINTS[i],POINTS[i+1]) <= param.LENGTH1){
          return true;
        }
      }
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

    //True iff exists points in QPTS consecutive points
    //that are located in QUADS different quadrants.
    private boolean LIC4(){
      if(param.QPTS < 2 || param.QPTS > NUMPOINTS ||
      param.QUADS < 1 || param.QUADS > 3){
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
            if(currQuad == 0){
              System.err.println("LIC4: not quadrant found");
              return false;
            }
            if(!quadUsed[currQuad - 1]){
              nbUsed++;
              quadUsed[currQuad - 1] = true;
            }
        }
        if(nbUsed > param.QUADS) return true;
      }
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

    //True iff 3 consecutive points sperated by APTS and BPTS respectively
    //are contained in a circle of radius RADIUS1
    private boolean LIC8(){
      //Argument check
      if(param.APTS < 1 || param.BPTS < 1 || param.RADIUS1 < 0 ||
      param.APTS + param.BPTS > NUMPOINTS - 3 ){
        System.err.println("LIC8: argument(s) out of bounds");
        return false;
      }
      if(NUMPOINTS < 5) return false;

      Point a, b, c;
      boolean inCircle;
      for (int i = 0; i < NUMPOINTS - param.APTS - param.BPTS; i++) {
        a = POINTS[i];
        b = POINTS[i + param.APTS];
        c = POINTS[i + param.APTS + param.BPTS];
        inCircle = true;

        //The circle that passes exactly through a, b and c is the smallest circle
        //that contains the 3 points. Thus RADIUS1 has to be greater or equal
        //in order to be able to contain the 3 points.
        if(getRadiusCircle(a,b,c) <= param.RADIUS1) return true;
      }
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

    //True iff exists 2 pairs of points, where the 2 points in each pair are separated by KPTS,
    //such that: the points of one pair are at a distance smaller than LENGTH1 and
    //the points of the other pair are at a distance greater than LENGTH2.
    private boolean LIC12(){
      //Argument check
      if(param.LENGTH1 < 0 || param.LENGTH2 < 0 || param.KPTS < 1){
        System.err.println("LIC12: Argument(s) out of bounds");
        return false;
      }
      if(NUMPOINTS < 3) return false;
      //true iff distance is smaller than LENGTH1
      boolean l1 = false;
      //true iff distance is greater than LENGTH2
      boolean l2 = false;
      //Current distance evaluated
      double d = 0;
      for (int i = 0; i < NUMPOINTS - param.KPTS; i++) {
        d = dist(POINTS[i], POINTS[i + param.KPTS]);
        if(d < param.LENGTH1) l1 = true;
        if(d > param.LENGTH2) l2 = true;
        if(l1 && l2) return true;
      }
      return false;
    }

    private boolean LIC13(){
      return false;
    }

    private boolean LIC14(){
      return false;
    }
}
