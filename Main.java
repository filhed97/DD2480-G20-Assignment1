import java.awt.geom.Point2D;

public class Main{

    public enum LogicalOperators {
        NOTUSED,
        ORR,
        ANDD
    }

    public int NUMPOINTS;
    public Point2D.Double[] POINTS;
    public Parameters PARAMETERS;
    public LogicalOperators[][] LCM = new LogicalOperators[15][15];
    public boolean[] PUV = new boolean[15];
    public CMV cmv;
    public boolean[] cmvResult = new boolean[15];
    public boolean LAUNCH;
    public boolean[][] PUM = new boolean[15][15];
    public boolean[] FUV = new boolean[15];


    public void getLaunch(boolean mockTesting){
      if(!mockTesting){
        cmv = new CMV(NUMPOINTS, POINTS, PARAMETERS);
      }
      cmvResult = cmv.DECIDE();
      fillPUM();
      fillFUV();
      makeLaunchDecision();
    }

    /**
     * Fill in the Preliminary Unlocking Matrix.
     */
    public void fillPUM() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {

                if (LCM[i][j] == LogicalOperators.NOTUSED) {
                    PUM[i][j] = true;

                } else if (LCM[i][j] == LogicalOperators.ORR) {
                    if (cmvResult[i] || cmvResult[j]) {
                        PUM[i][j] = true;
                    } else {
                        PUM[i][j] = false;
                    }

                } else if (LCM[i][j] == LogicalOperators.ANDD) {
                    if (cmvResult[i] && cmvResult[j]) {
                        PUM[i][j] = true;
                    } else {
                        PUM[i][j] = false;
                    }
                }
            }
        }
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
