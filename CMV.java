import java.awt.Point;

public class CMV{

    private int NUMPOINTS;
    private Point [] POINTS;
    private Parameters param;


    public CMV(int NUMPOINTS,Point[] POINTS, Parameters param){

      this.NUMPOINTS = NUMPOINTS;
      this.POINTS = POINTS;
      this.param = param;

    }

    public boolean[] DECIDE(){
      return null;
    }

    private boolean LIC0(){
      return false;
    }

    private boolean LIC1(){
		double x,y;
		for(int i = 0; i<=NUMPOINTS-2; i++){	
			//calculate centre of circle
			x = (POINTS[i].getX() + POINTS[i+1].getX() + POINTS[i+2].getX())/3;
			y = (POINTS[i].getY() + POINTS[i+1].getY() + POINTS[i+2].getY())/3;
			
			//calculate all distances and compare to radius
			//if one point is outside circle centered around 3 points with spec. radius, LIC is true.
			if(Math.sqrt(Math.pow(POINTS[i].getX() - x, 2)+Math.pow(POINTS[i].getY() - y, 2)) > param.RADIUS1
			||	Math.sqrt(Math.pow(POINTS[i+1].getX() - x, 2)+Math.pow(POINTS[i+1].getY() - y, 2)) > param.RADIUS1
			||	Math.sqrt(Math.pow(POINTS[i+2].getX() - x, 2)+Math.pow(POINTS[i+2].getY() - y, 2)) > param.RADIUS1)
				return true;	
		}
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
		for(int i = 0; i<=NUMPOINTS-1; i++){
			if(POINTS[i+1].getX()-POINTS[i].getX() < 0)
				return true;
		}
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
}
