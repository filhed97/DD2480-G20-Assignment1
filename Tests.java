import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import java.awt.geom.Point2D;
import static org.mockito.Mockito.*;
import java.util.Arrays;


public class Tests {

    //Documentation : https://github.com/junit-team/junit4/wiki/Getting-started
    // Compile Tests class
    //   Linux and MAC :
    //   javac -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:mockito-all-1.9.5.jar Tests.java
    //
    //   Windows
    //   javac -cp .;junit-4.13.jar;hamcrest-core-1.3.jar;mockito-all-1.9.5.jar Tests.java
    //
    // Run tests
    //   Linux or MacOS
    //   java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:mockito-all-1.9.5.jar org.junit.runner.JUnitCore Tests
    //   Windows
    //   java -cp .;junit-4.13.jar;hamcrest-core-1.3.jar;mockito-all-1.9.5.jar org.junit.runner.JUnitCore Tests

    //Output format:
    //Junit version 4.13
    //.E   (a dot means the test pasts, an E means an failure)
    //Time: x.xxx
    //Ok or failure

    @Test
    public void WhatDoesTheTestAssert() {
        // assert statements
        // You can find other assertions on the following website.
        //http://junit.sourceforge.net/javadoc/org/junit/Assert.html
        assertEquals(0, 10*0);
        assertFalse(0 == 1);
        //assertNotEquals(1,1);//This assertion obviously fails

        //Edit: Here is a description of the testing library Hamcrest but
        //after coding some tests it seems like it's not really useful
        //for this assignment
        //
        //
        //Hamcrest is a library that makes the code and the output message
        //more readable and efficient
        //
        //Use assertThat(effectiveValue, matcher(expectedValue));
        //
        //The structure is such that it is readable in english, keep that in mind
        //when searching how to use a certain matcher.
        //You can find more matchers like both(), anyOf(),
        //not() or isNullValue() here:
        //http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/CoreMatchers.html
        assertThat(1+1, equalTo(2));
        assertThat(1+1, both(equalTo(2)).and(not(equalTo(0))));
    }

    //Initialize parameters
    //Always intialize the relevant param values needed in your tests
    private Parameters param = new Parameters(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

    //Object containing the input parameters, such as the PUV or the PUM,
    //and the methods that compute their values.
    private Main main = new Main();

    @Test
    public void DECIDE() {
        CMV MockCMV = mock(CMV.class);
        boolean[] expected = {true, false, false, true, false, true, true, true, false, true, false, false, true, false, true};

        when(MockCMV.LIC0()).thenReturn(expected[0]);
        when(MockCMV.LIC1()).thenReturn(expected[1]);
        when(MockCMV.LIC2()).thenReturn(expected[2]);
        when(MockCMV.LIC3()).thenReturn(expected[3]);
        when(MockCMV.LIC4()).thenReturn(expected[4]);
        when(MockCMV.LIC5()).thenReturn(expected[5]);
        when(MockCMV.LIC6()).thenReturn(expected[6]);
        when(MockCMV.LIC7()).thenReturn(expected[7]);
        when(MockCMV.LIC8()).thenReturn(expected[8]);
        when(MockCMV.LIC9()).thenReturn(expected[9]);
        when(MockCMV.LIC10()).thenReturn(expected[10]);
        when(MockCMV.LIC11()).thenReturn(expected[11]);
        when(MockCMV.LIC12()).thenReturn(expected[12]);
        when(MockCMV.LIC13()).thenReturn(expected[13]);
        when(MockCMV.LIC14()).thenReturn(expected[14]);

        when(MockCMV.DECIDE()).thenCallRealMethod();
        boolean[] effective = MockCMV.DECIDE();
        for (int i = 0; i < 15; i++) {
            assertThat(effective[i], equalTo(expected[i]));
        }
    }

    //Tests that the FUV is filled accordingly to the PUM and the PUV:
    //FUV[i] true iff PUV[i] is false or PUM[i,j] is true for all j.
    //Cases: PUV true/false, PUM row all true/ contain false.
    @Test
    public void fillFUV(){
      //Reset attributes
      //main.FUV = new boolean[15];

      //The PUV is filled with true except first value
      //main.PUV = new boolean[15];
      Arrays.fill(main.PUV, true);
      main.PUV[0] = false;

      //The PUM is filled with true except the 1st and 2nd rows that are
      // entirely false and the 3rd row that contains a single false.
      //main.PUM = new boolean[15][15];
      Arrays.fill(main.PUM[0], false);
      Arrays.fill(main.PUM[1], false);
      for (int i = 2; i<15; i++) {
        Arrays.fill(main.PUM[i], true);
      }
      main.PUM[2][14] = false;

      //Expected outcomes:
      //  FUV[0] = true since PUV[0] is false
      //  FUV[1] = false since PUV[1] = true and PUM[1,j] = false for all j
      //  FUV[2] = false since PUV[2] = true and PUM[2,14] = false
      //  all remaining FUV should be true since PUV is true and PUM is true
      main.fillFUV();
      assertThat(main.FUV[0], equalTo(true));
      assertThat(main.FUV[1], equalTo(false));
      assertThat(main.FUV[2], equalTo(false));
      for (int i = 3; i < 15; i++ ) {
        assertThat(main.FUV[i], equalTo(true));
      }
    }

    // Test launch function.
    // LAUNCH set to true if all values in FUV are true, otherwise false.
    @Test
    public void testLaunch() {
        Arrays.fill(main.FUV, true);
        main.makeLaunchDecision();
        assertThat(main.LAUNCH, equalTo(true));

        main.FUV[0] = false;
        main.makeLaunchDecision();
        assertThat(main.LAUNCH, equalTo(false));
    }

    //How to test a function in general:
    // * Generate artificial inputs (points, param, etc.) such that you know
    //   for certain the expected output of the tested function
    // * Compare the expected value with the output of the function
    // * Test the function's limit cases (negative value, null value,
    //   last index of array, etc.)

    //Since the LICs are private methods you can only access
    //the output of the tested LIC through the DECIDE method:
    //the i-th entry of the CMV is equal to the i-th LIC's output
    //i.e. CMV[i] = LICi();

    //Tests true iff no 3 consecutive points can be contained in a
    //circle of radius RADIUS1
    //Limit case: a,b,c form an equilateral triangle of length 2*RADIUS1
    @Test
    public void LIC1(){
      param.RADIUS1 = 1;
      Point2D.Double a,b,c,d,e;
      d = new Point2D.Double(-2,0);
      e = new Point2D.Double(3,0);
      //a, b and c are the Point that can be contained in the circle
      a = new Point2D.Double(0,0);
      b = new Point2D.Double(1,0);
      c = new Point2D.Double(0,1);


      Point2D.Double[] data1 = {a,b,c}; // a,b,c can be contained
      CMV cmv1 = new CMV(3, data1, param);
      //data1 doesn't satisfy LIC1 thus should not be true
      assertThat(cmv1.LIC1(), is(not(equalTo(true)))); //lots of syntatic sugar

      Point2D.Double[] data2 = {a,d,b,e,c}; // cannot be contained
      CMV cmv2 = new CMV(5, data2, param);
      //data2 satisfy LIC1 thus should be true
      assertThat(cmv2.LIC1(), is((equalTo(true))));

      //Let's create an equilateral triangle of side length 2
      b.setLocation(2,0);
      c.setLocation(1, Math.sqrt(3.0)); //Pythagoras => sqrt(3)^2 + 1^2 = 2^2
      Point2D.Double[] data3 = {a,b,c};
      //data3 doesn't satisfy LIC1
      CMV cmv3 = new CMV(3, data3, param);
      assertThat(cmv3.LIC1(), equalTo(true));
    }
	
	//Tests true iff there exists 3 consecutive points that together form
    // a triangle with area larger the AREA1
    @Test
    public void LIC3(){
        param.AREA1 = 4.5;
        Point2D.Double a, b, c, e;
        a = new Point2D.Double(0, 0);
        b = new Point2D.Double(0, 3);
        c = new Point2D.Double(3, 0);

        Point2D.Double[] data1 = {a, b, c}; // a,b,c creates triangle with same area
        CMV cmv1 = new CMV(3, data1, param);
        //data1 doesn't satisfy LIC3 thus should not be true
        assertThat(cmv1.DECIDE()[3], is(not(equalTo(true))));

        Point2D.Double d = new Point2D.Double(6, 3);
        Point2D.Double[] data2 = {a, b, c, d}; // b, c, d creates triangle with area larger than AREA1
        CMV cmv2 = new CMV(4, data2, param);
        //data2 satisfies LIC3 thus should be true
        assertThat(cmv2.DECIDE()[3], is(equalTo(true))); //lots of syntatic sugar

        param.AREA1 = 5;
        e = new Point2D.Double(3, 3);
        Point2D.Double[] data3 = {a, b, c, e, d}; // no triangle with area larger than AREA1 exists
        CMV cmv3 = new CMV(5, data3, param);
        //data1 doesn't satisfy LIC3 thus should not be true
        assertThat(cmv1.DECIDE()[3], is(not(equalTo(true))));
    }

    //Tests true iff there exists 2 consecutive points such that
    //the second has a strictly lower X coordinate than the first.
    //Limit case: X[i] - X[j] = 0
    @Test
    public void LIC5(){
      Point2D.Double a = new Point2D.Double(1,0);
      Point2D.Double b = new Point2D.Double(0,0);

      Point2D.Double[] data1 = {a,b};
      CMV cmv1 = new CMV(2, data1, param);
      assertThat(cmv1.LIC5(), equalTo(true));//b.X - a.X = -1 < 0

      Point2D.Double[] data2 = {a,a,a,a,a,a};
      CMV cmv2 =  new CMV(6, data2,param);
      assertThat(cmv2.LIC5(), equalTo(false));// a.X - a.X not strictly less than 0

      Point2D.Double[] data3 = {b,a};
      CMV cmv3 =  new CMV(2, data3,param);
      assertThat(cmv3.LIC5(), equalTo(false));//a.X - b.X = 1 > 0
    }
	
	//Tests true if there exists two points separated by exactly KPTS consecutive intervening
    //a distance larger than LENGTH away from each other
    @Test
    public void LIC7(){
        param.KPTS = 1;
        param.LENGTH1 = 3;
        Point2D.Double a, b, c;

        a = new Point2D.Double(0,0);
        b = new Point2D.Double(2,2);
        c = new Point2D.Double(4,0);

        Point2D.Double[] data1 = {a, b, c}; // a and c are further than LENGTH apart
        CMV cmv1 = new CMV(3, data1, param);
        //data1 satisfies LIC7 thus should be true
        assertThat(cmv1.DECIDE()[7], is(equalTo(true))); //lots of syntatic sugar

        c.setLocation(3,0);
        Point2D.Double[] data2 = {a, b, c}; // a and c are exactly Length apart
        CMV cmv2 = new CMV(3, data2, param);
        //data2 doesn't satisfy LIC7 thus should not be true
        assertThat(cmv2.DECIDE()[7], is(not(equalTo(true)))); //lots of syntatic sugar

        param.KPTS = 2;
        Point2D.Double d = new Point2D.Double(3, 3);
        Point2D.Double[] data3 = {b, a, c, b, d}; // a and d are further than LENGTH apart
        CMV cmv3 = new CMV(5, data3, param);
        //data3 satisfies LIC7 thus should be true
        assertThat(cmv3.DECIDE()[7], is(equalTo(true))); //lots of syntatic sugar

        Point2D.Double[] data4 = {b, c, a, b, d}; // no points separated by KPTS are further than LENGTH apart
        CMV cmv4 = new CMV(5, data4, param);
        //data4 doesn't satisfy LIC7 thus not should be true
        assertThat(cmv2.DECIDE()[7], is(not(equalTo(true)))); //lots of syntatic sugar
    }

    //Assuming that the angle of 3 points is in rad in [0, PI].
    //
    //Tests true iff exists 3 consecutive points separated by CPTS and DPTS
    //that creates an angle < PI - EPSILON and similarly exists 3 points that
    //creates an angle > PI - EPSILON
    //Limit cases: PI - EPSILON = 0
    //EPSILON = 0
    //2 points on vertex of angle
    //NUMPOINTS < 5
    @Test
    public void LIC9(){
      param.CPTS = 1;
      param.DPTS = 1;

      Point2D.Double a = new Point2D.Double(0,0);
      Point2D.Double b = new Point2D.Double(1,0);
      Point2D.Double c = new Point2D.Double(0,1);
      Point2D.Double skip = new Point2D.Double(0,0);

      param.EPSILON = 0;
      Point2D.Double[] data1 = {a, skip, b, skip, c}; //abc is a PI/2 rad angle
      CMV cmv1 = new CMV(5, data1, param);
      //Should be true for any angle except PI
      assertThat(cmv1.LIC9(), equalTo(true));


      Point2D.Double d = new Point2D.Double(1,1);
      Point2D.Double e = new Point2D.Double(-1,-1);
      Point2D.Double[] data2 = {e, skip, a, skip, d}; //abd is a PI rad angle
      CMV cmv2 = new CMV(5, data2, param);
      //Should be false since angle should be different that PI
      //assertThat(cmv2.LIC9(), equalTo(false));

      param.EPSILON = Math.PI;
      CMV cmv3 = new CMV(5, data1, param);//abc is a PI/2 rad angle
      //Should be true for any angle except 0
      assertThat(cmv3.LIC9(), equalTo(true));

      Point2D.Double[] data3 = {a, skip, b, skip, a}; //aba is a 0 rad angle
      CMV cmv4 = new CMV(5, data3, param);
      //Should be false for angle 0
      assertThat(cmv4.LIC9(), equalTo(false));

      CMV cmv5 = new CMV(4, data1, param);
      //false since NUMPOINTS has to be greater than 4
      assertThat(cmv5.LIC9(), equalTo(false));

      Point2D.Double[] data4 = {a, skip, a, skip, b}; //aab is undefined
      CMV cmv6 = new CMV(5, data4, param);
      //false since undefined angle
      //assertThat(cmv6.LIC9(), equalTo(false));
    }
	
	//Tests true iff there exists 2 points separated by exactly GPTS such that
    //X[i] - X[j] < 0
    //Limit case: X[i] - X[j] = 0
    @Test
    public void LIC11(){
        param.GPTS = 1;
        Point2D.Double a = new Point2D.Double(2, 0);
        Point2D.Double b = new Point2D.Double(0, 0);
        Point2D.Double c = new Point2D.Double(1, 0);

        Point2D.Double[] data1 = {b, a, a}; // a.X - b.X = 2-0 = 2
        CMV cmv1 = new CMV(2, data1, param);
        //should be false
        assertThat(cmv1.DECIDE()[11], equalTo(false));

        Point2D.Double[] data2 = {a,a,a,a,a,a}; //needs to be strictly smaller
        CMV cmv2 =  new CMV(6, data2,param);
        //data2 doesn't satisfy LIC7 thus should not be true
        assertThat(cmv2.DECIDE()[11], equalTo(false));// a.X - a.X not strictly less than 0

        param.GPTS = 2;
        Point2D.Double d = new Point2D.Double(2, 2);
        Point2D.Double[] data3 = {b, a, a, c, d, c}; //a followed by c satisfies the condition c.X - a.X = 1-2 = -1
        CMV cmv3 =  new CMV(6, data3,param);
        //data1 satisfies LIC11 thus should be true
        assertThat(cmv3.DECIDE()[11], equalTo(true));
    }

    @Test
    public void calculateAngle(){
      Point2D.Double a = new Point2D.Double(0,0);
      Point2D.Double b = new Point2D.Double(1,0);
      Point2D.Double c = new Point2D.Double(1,1);
      Point2D.Double d = new Point2D.Double(2,0);

      Point2D.Double[] data1 = {a,b,c};
      CMV cmv1 = new CMV(3, data1, param);
      assertThat(cmv1.calculateAngle(a,b,c), equalTo(Math.PI/2));

      Point2D.Double[] data2 = {a,b,d};
      CMV cmv2 = new CMV(3, data2, param);
      //assertThat(cmv2.calculateAngle(a,b,d), equalTo(Math.PI));
    }

    //Tests true iff exists 3 consecutive points separated by APTS and BPTS
    // that cannot be contained by circle of radius RADIUS1 and similarly
    // exiss 3 consec[...] that can be contained by circle of radius RADIUS2
    //Limit cases: points on circle
    //one case only true
    //NUMPOINTS < 5
    @Test
    public void LIC13(){
      param.APTS = 1;
      param.BPTS = 1;
      param.RADIUS1 = .5;
      param.RADIUS2 = 2;
      Point2D.Double a = new Point2D.Double(0,0);
      Point2D.Double b = new Point2D.Double(2,0);
      Point2D.Double c = new Point2D.Double(1,0);
      c.setLocation(1,Math.sqrt(3));
      Point2D.Double skip = new Point2D.Double (-5,0);
      //Equilateral triangle of length 2
      Point2D.Double[] data1 = {a, skip, b, skip, c};

      CMV cmv1 = new CMV(5, data1, param);
      //abc is not contained RADIUS1 but is contained in RADIUS2
      assertThat(cmv1.LIC13(), equalTo(true));

      Point2D.Double d = new Point2D.Double(4,0);
      //All a,b and c on x axis
      Point2D.Double[] data2 = {a, skip, b, skip, d};
      CMV cmv2 = new CMV(5, data2, param);
      //True since a,b,d are exactly on circle of RADIUS2
      assertThat(cmv2.LIC13(), equalTo(true));

      //Check didn't switch radius1 and radius2
      param.RADIUS1 = 2;
      param.RADIUS2 = 0.5;
      CMV cmv3 = new CMV(5, data1, param);
      //a,b,c can be contained in radius1 but cannot in radius2
      assertThat(cmv3.LIC13(), equalTo(false)); //case both conditions are false

      param.RADIUS1 = 2;
      param.RADIUS2 = 2;
      CMV cmv4 = new CMV(5, data1, param);
      //a,b,c can be contained in radius1 and in radius2
      assertThat(cmv4.LIC13(), equalTo(false)); //case radius1 is false

      param.RADIUS1 = 1;
      param.RADIUS2 = 1;
      CMV cmv5 = new CMV(5, data1, param);
      //a,b,c can be contained in either radius1 nor radius2
      assertThat(cmv5.LIC13(), equalTo(false)); //case radius2 false
    }

    @Test
    public void fillPUM(){

      //The CMV is filled with true except first value
      Arrays.fill(main.cmv, true);
      main.cmv[0] = false;
      main.cmv[14] = false;

      //The LCM is filled with NOTUSED except in certain position in which
      //the logical operators ANDD and ORR are tested
      for (int i = 0; i<15; i++) {
        Arrays.fill(main.LCM[i], Main.LogicalOperators.NOTUSED);
      }
      main.LCM[0][1] = Main.LogicalOperators.ANDD;
      main.LCM[1][2] = Main.LogicalOperators.ANDD;
      main.LCM[1][0] = Main.LogicalOperators.ORR;
      main.LCM[0][14] = Main.LogicalOperators.ORR;

      //Expected outcomes:
      //  PUM[0][1] = false since cmv[0] is false
      //  PUM[1][2] = true since cmv[1] AND cmv[2] are true
      //  PUM[1][0] = true since cmv[1] OR cmv[0] is true
      //  PUM[0][14] = false since cmv[0] OR cmv[14] is false
      //  PUM[0][0] = true since LCM[0][0] is NOTUSED
      //  Same for rest of PUM
      main.fillPUM();

      //TEST ANDD
      assertThat(main.PUM[0][1], equalTo(false));
      assertThat(main.PUM[1][2], equalTo(true));

      //TEST ORR
      assertThat(main.PUM[1][0], equalTo(true));
      assertThat(main.PUM[0][14], equalTo(false));

      //Test NOTUSED
      assertThat(main.PUM[0][0], equalTo(true));
      for(int i = 2; i < 15; i++){
        for(int j = 0; j < 15; j++){
            assertThat(main.PUM[i][j], equalTo(true));
        }
      }
    }

}

