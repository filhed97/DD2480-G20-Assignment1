import java.awt.Point;

public class Main{

    enum LogicalOperators {
  	   NOTUSED,
  	   ORR,
 	     ANDD
    }

    int NUMPOINTS;
    Point[] POINTS;
    Parameters PARAMETERS;
    LogicalOperators[][] LCM = new LogicalOperators[15][15];
    Boolean[] PUV = new Boolean[15];
    String LAUNCH;
    Boolean[] cmv = new Boolean[15];
    Boolean[][] PUM = new Boolean[15][15];
    Boolean[] FUV = new Boolean[15];

    public static void main(String[] args){


    }

    //Fills the FUV by reading the PUV and PUM
    private void fillFUV(){
        for(int i = 0; i < 15; i++){
            if(!PUV[i]){ // If PUV[i] is false PUM[i] doesn't matter, FUV[i] is always true
                FUV[i] = true;
                continue;
            } else { //otherwise makes sure everything in PUM[i] is true
                for(int j = 0; j < 14; j++){
                    if(!PUM[i][j]){
                        FUV[i] = false;
                        break;
                    }
                }
            }
        }
    }

}
