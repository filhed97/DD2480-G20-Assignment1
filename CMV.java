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

    //Return true if there exists three points seperated by EPTS and FPTS respictively
    //which together create a traingle with an area larger than AREA1 and there also three points seperated
    //by EPTS and FPTS respictively which together create a traingle with an area smaller than AREA2
    private boolean LIC14(){
      return false;
    }

}
