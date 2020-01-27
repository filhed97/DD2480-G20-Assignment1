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
		for(int i = 0; i<=NUMPOINTS-2; i++){	
			Point a,b,c,centre;
			a = POINTS[i];
			b = POINTS[i+1];
			c = POINTS[i+2];
			
			double x = (a.getX() + b.getX() + c.getX())/3;
			double y = (a.getY() + b.getY() + c.getY())/3;
			centre = new Point(0,0);
			centre.setLocation(x,y);
			
			//calculate all distances and compare to radius
			//if one point is outside circle centered around 3 points with spec. radius, LIC is true.
			if(centre.distance(a) > param.RADIUS1
			||	centre.distance(b) > param.RADIUS1
			||	centre.distance(c) > param.RADIUS1)
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
		for(int i = 0; i<=NUMPOINTS-(param.CPTS+param.DPTS+2); i++){
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
		boolean condition1 = false;
		boolean condition2 = false;
		for(int i = 0; i<=NUMPOINTS-(param.APTS+param.BPTS+2); i++){
			Point a,b,c,centre;
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
			||	centre.distance(b) <= param.RADIUS2
			||	centre.distance(c) <= param.RADIUS2)
				condition2 = true;
		}
		
		if(condition1 && condition2)
			return true;
		
		return false;
    }

    private boolean LIC14(){
      return false;
    }
	
	//Calculates the angle between the lines a-b and b-c
    private double calculateAngle(Point a, Point b, Point c){
      return Math.atan2(c.getY() - b.getY(), c.getX() - b.getX()) - Math.atan2(a.getY() - b.getY(), c.getX() - c.getY());
    }
}
