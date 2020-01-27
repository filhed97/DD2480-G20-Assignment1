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
      return false;
    }
}
