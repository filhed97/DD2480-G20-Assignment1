import java.awt.Point;

public class Main{

    enum LogicalOperators {
  	   NOTUSED,
  	   ORR,
 	     ANDD
    }

    public int NUMPOINTS;
    public Point[] POINTS;
    public Parameters PARAMETERS;
    public LogicalOperators[][] LCM = new LogicalOperators[15][15];
    public boolean[] PUV = new boolean[15];
    public boolean LAUNCH;
    public boolean[] cmv = new boolean[15];
    public boolean[][] PUM = new boolean[15][15];
    public boolean[] FUV = new boolean[15];

    public static void main(String[] args){


    }

    //Fills the FUV by reading the PUV and PUM
    public void fillFUV(){
        for(int i = 0; i < 15; i++){
            if(!PUV[i]){ // If PUV[i] is false PUM[i] doesn't matter, FUV[i] is always true
                FUV[i] = true;
                continue;
            } else { //otherwise makes sure everything in PUM[i] is true
            FUV[i] = true;
                for(int j = 0; j < 15; j++){
                    if(!PUM[i][j]){
                        FUV[i] = false;
                        break;
                    }
                }
            }
        }
    }

    public void makeLaunchDecision() {
        for (int i = 0; i < 15; i++) {
            if (!FUV[i]) {
                LAUNCH = false;
                return;
            }
        }
        LAUNCH = true;
    }

}
