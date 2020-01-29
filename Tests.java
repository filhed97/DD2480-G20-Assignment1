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
    public void TemplateTest() {
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

    //Test the whole project with Mockito
    //such that each LIC returns true.
    //the launch output then has to be coherent with the inputs
    @Test
    public void Main1(){
        Main main = new Main();
        main.NUMPOINTS = 5;
        main.POINTS = new Point2D.Double[]{new Point2D.Double(0, 0), new Point2D.Double(100, 5), new Point2D.Double(-1, 5), new Point2D.Double(14.2, 0), new Point2D.Double(0, -5)};
        main.PARAMETERS = new Parameters(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        //Mock CMV class with DECIDE() that returns true vector
        boolean[] expected = new boolean[15];
        Arrays.fill(expected, true);
        main.cmv = mock(CMV.class);
        when(main.cmv.DECIDE()).thenReturn(expected);

        main.LCM = new Main.LogicalOperators[15][15];
        for (int i = 0; i < 15; i++) {
          for (int j = 0; j < 15; j++) {
            if(i == j) main.LCM[i][i] = Main.LogicalOperators.NOTUSED;
            main.LCM[i][j] = Main.LogicalOperators.ORR;
          }
        }
        main.LCM[0][1] = Main.LogicalOperators.ANDD;
        main.LCM[1][0] = Main.LogicalOperators.ANDD;

        main.PUV = new boolean[15];
        Arrays.fill(main.PUV, true);
        main.PUV[0] = false;

        main.getLaunch(true);
        assertThat(main.LAUNCH, equalTo(true));
    }

    //Test using LIC 0, 3, 5 and 11
    //The expected outputs are commented below
    @Test
    public void Main2(){
      Main main = new Main();
      main.NUMPOINTS = 4;
      main.POINTS = new Point2D.Double[]{new Point2D.Double(0, 0), new Point2D.Double(1, 0), new Point2D.Double(1, 1), new Point2D.Double(0, 1)};

      //double LENGTH1,double RADIUS1,double EPSILON,double AREA1,int QPTS,
      //int QUADS,double DIST,int NPTS,int KPTS,int APTS,int BPTS,int CPTS, int DPTS,
      //int EPTS, int FPTS, int GPTS,double LENGTH2,double RADIUS2,double AREA2
      main.PARAMETERS = new Parameters(2,0,0.1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0);
      main.LCM = new Main.LogicalOperators[15][15];
      for (int i = 0; i<15; i++) {
        for (int j = 0; j<15; j++) {
          //Expected output
          //LIC0 : False
          //LIC3 : True
          //LIC5 : True
          //LIC11 : True
          if(i == 0 && j== 3 || i==3 && j==0) main.LCM[i][j] = Main.LogicalOperators.ANDD; // means PUM entry will be false
          else if(i == 0 && j == 5 && i == 5 && j == 0) main.LCM[i][j] = Main.LogicalOperators.ORR;
          else if(i == 0 && j == 11 && i == 11 && j == 0) main.LCM[i][j] = Main.LogicalOperators.ORR;
          else if(i == 3 && j == 5 && i == 5 && j == 3) main.LCM[i][j] = Main.LogicalOperators.ANDD;
          else if(i == 3 && j == 11 && i == 11 && j == 3) main.LCM[i][j] = Main.LogicalOperators.ANDD;
          else if(i == 5 && j == 11 && i == 11 && j == 5) main.LCM[i][j] = Main.LogicalOperators.ANDD;
          else main.LCM[i][j] = Main.LogicalOperators.NOTUSED;
        }
      }
      main.PUV = new boolean[15];
      Arrays.fill(main.PUV, false);
      main.getLaunch(false);
      assertThat(main.LAUNCH, equalTo(true));

      Arrays.fill(main.PUV, true);
      main.getLaunch(false);
      assertThat(main.LAUNCH, equalTo(false));
    }

	//Tests complete program with values which should result in a launch
	@Test
    public void Main3(){
        Main main = new Main();
		main.NUMPOINTS = 27;
		//Datapoints are mostly sourced from unit tests of individual LIC's such that each relevant LIC should be true in the CMV.
		main.POINTS = new Point2D.Double[]{new Point2D.Double(0, 0), new Point2D.Double(1, 0), new Point2D.Double(0, 1), new Point2D.Double(2, 1),
		new Point2D.Double(1, 1),new Point2D.Double(1,2),new Point2D.Double(0,0),new Point2D.Double(4,0),new Point2D.Double(0,4),new Point2D.Double(0,0),
		new Point2D.Double(1,3),new Point2D.Double(3,0),new Point2D.Double(0,0),new Point2D.Double(-2,0),new Point2D.Double(0,0),new Point2D.Double(0,0),
		new Point2D.Double(1,0),new Point2D.Double(0,0),new Point2D.Double(0,1),new Point2D.Double(0,4),new Point2D.Double(0,0),new Point2D.Double(4,0),
		new Point2D.Double(0,0),new Point2D.Double(-5,0),new Point2D.Double(4,0),new Point2D.Double(-5,0),new Point2D.Double(1,0)};

		//Reminder of input order in PARAMETERS:
		//double LENGTH1,double RADIUS1,double EPSILON,double AREA1,int QPTS,
		//int QUADS,double DIST,int NPTS,int KPTS,int APTS,int BPTS,int CPTS, int DPTS,
		//int EPTS, int FPTS, int GPTS,double LENGTH2,double RADIUS2,double AREA2
			main.PARAMETERS = new Parameters(3,1,0.8,1,0,0,2,3,1,1,1,1,1,1,1,1,0,4,100);
			main.LCM = new Main.LogicalOperators[15][15];
			for (int i = 0; i<15; i++) {
				for (int j = 0; j<15; j++) {
					//Expected output
					//LIC #1,2,3,5,6,7,9,10,11,13,14 should be true, rest are irrelevant due to PUV.
					//Set all entries in LCM to ORR, the launch should happen since all the considered LIC's should be true.
					//Diagonal entries not relevant so set to NOTUSED.
					if(i == j)
						main.LCM[i][i] = Main.LogicalOperators.NOTUSED;

					main.LCM[i][j] = Main.LogicalOperators.ORR;
				}
		  }
		  
		  //All LIC's except for 0,4,8 and 12 should be considered when determining launch 
		  //LIC's 0,4,8 and 12 have not been considered when creating the data points, so they might be true or false.
		  //PUV says they should not hold back launch despite their values, so they are set to false in the PUV.
		  main.PUV = new boolean[15];
		  Arrays.fill(main.PUV, true);
		  main.PUV[0]=false;
		  main.PUV[4]=false;
		  main.PUV[8]=false;
		  main.PUV[12]=false;
		  
		  //Call main function to determine launch with the data provided above.
		  //Should be true.
		  main.getLaunch(false);
		  assertThat(main.LAUNCH, equalTo(true));
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


    @Test
    public void fillPUM(){

      //The CMV is filled with true except first value
      Arrays.fill(main.cmvResult, true);
      main.cmvResult[0] = false;
      main.cmvResult[14] = false;

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


    //Tests true if there exists two consecutive points
    //a distance larger than LENGTH away from each other
    @Test
    public void LIC0(){
        param.LENGTH1 = 3;
        Point2D.Double a, b;

        a = new Point2D.Double(0, 0);
        b = new Point2D.Double(2, 2);

        Point2D.Double[] data1 = {a,b}; // a and b are too close
        CMV cmv1 = new CMV(2, data1, param);
        //data1 doesn't satisfy LIC0 thus should not be true
        assertThat(cmv1.LIC0(), is(not(equalTo(true))));

        Point2D.Double c = new Point2D.Double(4, 0);
        Point2D.Double[] data2 = {a,c,b}; // a and c are further than LENGTH apart
        CMV cmv2 = new CMV(3, data2, param);
        //data2 satisfies LIC0 thus should be true
        assertThat(cmv2.LIC0(), is(equalTo(true)));

        c.setLocation(3, 0);
        Point2D.Double[] data3 = {a,c,b}; // a and c are exactly Length apart
        CMV cmv3 = new CMV(3, data3, param);
        //data3 doesn't satisfy LIC0 thus should not be true
        assertThat(cmv3.LIC0(), is(not(equalTo(true))));
    }

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

	//Tests true iff exists 3 consecutive points
	//which create an angle < PI - EPSILON
    //or creates an angle > PI - EPSILON
    //Edge cases:
	//EPSILON = 0
    //2 points on vertex of angle
    @Test
    public void LIC2(){

      param.EPSILON = Math.PI / 4;
	  //This gives
	  //PI-EPSILON = 3PI/4 rad angle
	  //PI+EPSILON = 5PI/4 rad angle

      Point2D.Double a = new Point2D.Double(2,1);
      Point2D.Double b = new Point2D.Double(1,1);
      Point2D.Double c = new Point2D.Double(1,2);
	  Point2D.Double d = new Point2D.Double(0,1);
	  Point2D.Double e = new Point2D.Double(1,0);

	  //Test 1, check angle < PI-EPSILON.
      Point2D.Double[] data1 = {a, b, c}; //abc is a PI/2 rad angle
      CMV cmv1 = new CMV(3, data1, param);
      //Should be true since PI/2 is less than PI-EPSILON=3PI/4
      assertThat(cmv1.LIC2(), equalTo(true));

	  //Test 2, check angle > PI+EPSILON.
      Point2D.Double[] data2 = {a, b, e}; //abe is a 3PI/2 rad angle
      CMV cmv2 = new CMV(3, data2, param);
      //Should be success since 3PI/2 is larger than 5PI/4
      assertThat(cmv2.LIC2(), equalTo(true));

	  //Test 3, check if angle lays between PI-EPSILON and PI+EPSILON
	  d.setLocation(0.0, 1.100);
      Point2D.Double[] data3 = {a, b, d}; //abd is a PI rad angle
      CMV cmv3 = new CMV(3, data3, param);
      //Should be false since PI lies in PI +/- EPSILON
      assertThat(cmv3.LIC2(), equalTo(false));

	  //Test 4, edge case if some point shares same coordinates as pivot point.
      Point2D.Double[] data4 = {a, b, b}; //abb is undefined
      CMV cmv4 = new CMV(3, data4, param);
      //Should be false since angle is undefined.
      assertThat(cmv4.LIC2(), equalTo(false));

	  //Test 5, edge case where EPSILON = 0.
	  param.EPSILON = 0;
      d.setLocation(0, 1);
	  Point2D.Double[] data5 = {a, b, d}; //abc is a PI rad angle
      CMV cmv5 = new CMV(3, data5, param);
      //Should be false, since angle should be strictly smaller or larger than PI.
      assertThat(cmv5.LIC2(), equalTo(false));
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
        assertThat(cmv1.LIC3(), is(not(equalTo(true))));

        Point2D.Double d = new Point2D.Double(6, 3);
        Point2D.Double[] data2 = {a, b, c, d}; // b, c, d creates triangle with area larger than AREA1
        CMV cmv2 = new CMV(4, data2, param);
        //data2 satisfies LIC3 thus should be true
        assertThat(cmv2.LIC3(), is(equalTo(true))); //lots of syntatic sugar

        param.AREA1 = 5;
        e = new Point2D.Double(3, 3);
        Point2D.Double[] data3 = {a, b, c, e, d}; // no triangle with area larger than AREA1 exists
        CMV cmv3 = new CMV(5, data3, param);
        //data1 doesn't satisfy LIC3 thus should not be true
        assertThat(cmv1.LIC3(), is(not(equalTo(true))));
    }

    @Test
    public void testLIC4() {
        param.QUADS = 1;
        param.QPTS = 2;
        Point2D.Double a = new Point2D.Double(1,0);
        Point2D.Double b = new Point2D.Double(-1, 0);

        // This test isn't passing
        Point2D.Double[] data1 = {a, b};
        CMV cmv1 = new CMV(2, data1, param);
        assertThat(cmv1.LIC4(), is(equalTo(true)));

        param.QUADS = 3;
        CMV cmv2 = new CMV(2, data1, param);
        assertThat(cmv2.LIC4(), is(equalTo(false)));
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

    //Tests true iff some point between i and i+NPTS has distance greater
  	//than DIST between itself and the line created by the points i and i+NPTS
  	//If points i and i+NPTS coincide, distance is calculated from their joint position.
  	//If NUMPOINTS < 3, value should be false.
  	//Edge cases:
  	//DIST = 0,
  	//Points i and i+NPTS are identical.
  	@Test
      public void LIC6(){

  		param.NPTS = 3;
  		param.DIST = 2;

  		Point2D.Double a = new Point2D.Double(0,0);
  		Point2D.Double b = new Point2D.Double(1,3);
  		Point2D.Double c = new Point2D.Double(2,1);
  		Point2D.Double d = new Point2D.Double(3,0);

  		//Test 1, check case where distance from middle point to line is 1, but dist is 2.
  		Point2D.Double[] data1 = {a, c, d}; //In a coordinate system x/y, the line a-d follows the x-axis from 0 to 3.
  		CMV cmv1 = new CMV(3, data1, param);
  		//Should be false, since dist is 2, and the distance from c to the x-axis is 1.
  		assertThat(cmv1.LIC6(), equalTo(false));

  		//Test 2, check case where distance from middle point to line is 3, and dist is 2.
  		Point2D.Double[] data2 = {a, b, d}; //In a coordinate system x/y, the line a-d follows the x-axis from 0 to 3.
  		CMV cmv2 = new CMV(3, data2, param);
  		//Should be true, since dist is 2, and the distance from b to the x-axis is 3.
  		assertThat(cmv2.LIC6(), equalTo(true));

  		//Test 3, check case where edge points coincide.
  		Point2D.Double[] data3 = {a, d, a};
  		CMV cmv3 = new CMV(3, data3, param);
  		//Should be true, since dist is 2, and the distance from a to d is 3.
  		assertThat(cmv3.LIC6(), equalTo(true));

          b.setLocation(1, 0.0001);
  		//Test 4, check case where DIST = 0, i.e. every input should be true.
  		param.DIST = 0;
  		Point2D.Double[] data4 = {a, b, d};
  		CMV cmv4 = new CMV(3, data4, param);
  		//Should be true, since any point not on the line should fulfill the distance condition
  		assertThat(cmv4.LIC6(), equalTo(true));

  		//Test 5, not enough points.
  		Point2D.Double[] data5 = {a, d};
  		CMV cmv5 = new CMV(2, data4, param);
  		//Should be false, not enough data.
  		assertThat(cmv5.LIC6(), equalTo(false));

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
        assertThat(cmv1.LIC7(), is(equalTo(true))); //lots of syntatic sugar

        c.setLocation(3,0);
        Point2D.Double[] data2 = {a, b, c}; // a and c are exactly Length apart
        CMV cmv2 = new CMV(3, data2, param);
        //data2 doesn't satisfy LIC7 thus should not be true
        assertThat(cmv2.LIC7(), is(not(equalTo(true)))); //lots of syntatic sugar

        param.KPTS = 2;
        Point2D.Double d = new Point2D.Double(3, 3);
        Point2D.Double[] data3 = {b, a, c, b, d}; // a and d are further than LENGTH apart
        CMV cmv3 = new CMV(5, data3, param);
        //data3 satisfies LIC7 thus should be true
        assertThat(cmv3.LIC7(), is(equalTo(true))); //lots of syntatic sugar

        Point2D.Double[] data4 = {b, c, a, b, d}; // no points separated by KPTS are further than LENGTH apart
        CMV cmv4 = new CMV(5, data4, param);
        //data4 doesn't satisfy LIC7 thus not should be true
        assertThat(cmv2.LIC7(), is(not(equalTo(true)))); //lots of syntatic sugar
    }


    @Test
    public void testLIC8() {
        param.RADIUS1 = 2;
        param.APTS = 1;
        param.BPTS = 2;

        Point2D.Double a = new Point2D.Double(1, 0);
        Point2D.Double b = new Point2D.Double(0, 1);
        Point2D.Double c = new Point2D.Double(0, -1);
        Point2D.Double d = new Point2D.Double(5, 0);

        // This test isn't passing.
        Point2D.Double[] data1 = {a, d, b, d, d, c};
        CMV cmv1 = new CMV(6, data1, param);
        assertThat(cmv1.LIC8(), is(equalTo(true)));

        Point2D.Double[] data2 = {a, d, d, b, d, c};
        CMV cmv2 = new CMV(6, data2, param);
        assertThat(cmv2.LIC8(), is(equalTo(false)));
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


  	//Tests true iff area created by points {i, i+EPTS+1, i+EPTS+FPTS+2} is larger than AREA1.
  	//NUMPOINTS >= 5.
  	@Test
      public void LIC10(){
  		param.AREA1 = 1;
  		param.EPTS = 1;
  		param.FPTS = 1;

  		Point2D.Double a = new Point2D.Double(0,0);
  		Point2D.Double b = new Point2D.Double(1,0);
  		Point2D.Double c = new Point2D.Double(0,1);
  		Point2D.Double filler = new Point2D.Double(0,0);

  		//Test 1, check case where area is smaller than AREA1.
  		Point2D.Double[] data1 = {a, filler, b, filler, c}; //creates a triangle with area 0.5
  		CMV cmv1 = new CMV(5, data1, param);
  		//Should be false, 0.5<1.
  		assertThat(cmv1.LIC10(), equalTo(false));

  		//Test 2, check case where area is smaller than AREA1.
  		param.AREA1 = 0.1;
  		CMV cmv2 = new CMV(5, data1, param); //triangle abc with area 0.5
  		//Should be true, 0.5>0.1.
  		assertThat(cmv2.LIC10(), equalTo(true));

  		//Test 3, not enough data.
  		Point2D.Double[] data2 = {a, b, filler, c};
  		CMV cmv3 = new CMV(4, data2, param);
  		//Should be false, must have at least 5 data points.
  		assertThat(cmv3.LIC10(), equalTo(false));
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
        assertThat(cmv1.LIC11(), equalTo(false));

        Point2D.Double[] data2 = {a,a,a,a,a,a}; //needs to be strictly smaller
        CMV cmv2 =  new CMV(6, data2,param);
        //data2 doesn't satisfy LIC7 thus should not be true
        assertThat(cmv2.LIC11(), equalTo(false));// a.X - a.X not strictly less than 0

        param.GPTS = 2;
        Point2D.Double d = new Point2D.Double(2, 2);
        Point2D.Double[] data3 = {b, a, a, c, d, c}; //a followed by c satisfies the condition c.X - a.X = 1-2 = -1
        CMV cmv3 =  new CMV(6, data3,param);
        //data1 satisfies LIC11 thus should be true
        assertThat(cmv3.LIC11(), equalTo(true));
    }

    @Test
    public void testLIC12() {
        param.LENGTH1 = 3;
        param.LENGTH2 = 6;
        param.KPTS = 1;

        Point2D.Double a = new Point2D.Double(0, 0);
        Point2D.Double b = new Point2D.Double (5, 0);
        Point2D.Double c = new Point2D.Double(8, 0);

        Point2D.Double[] data1 = {a, c, b};
        CMV cmv1 = new CMV(3, data1, param);
        assertThat(cmv1.LIC12(), is(equalTo(true)));

        Point2D.Double[] data2 = {a, b, c};
        CMV cmv2 = new CMV(3, data2, param);
        assertThat(cmv2.LIC12(), is(equalTo(false)));
    }

    @Test
    public void calculateAngle(){
      Point2D.Double a = new Point2D.Double(0,0);
      Point2D.Double b = new Point2D.Double(1,0);
      Point2D.Double c = new Point2D.Double(1,1);
      Point2D.Double d = new Point2D.Double(2,0);

      Point2D.Double[] data1 = {a,b,c};
      CMV cmv1 = new CMV(3, data1, param);
      assertThat(cmv1.calculateAngle(a,b,c), equalTo(-Math.PI/2));

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
  	public void LIC14(){
  		param.AREA1 = 0.5;
  		param.AREA2 = 1;
  		param.EPTS = 1;
  		param.FPTS = 1;

  		Point2D.Double a = new Point2D.Double(0,0);
  		Point2D.Double b = new Point2D.Double(1,0);
  		Point2D.Double c = new Point2D.Double(0,1);
  		Point2D.Double d = new Point2D.Double(2,0);
  		Point2D.Double e = new Point2D.Double(0,2);
  		Point2D.Double filler = new Point2D.Double(0,0);

  		//Test 1, check case where area is smaller than AREA1.
  		Point2D.Double[] data1 = {c, filler, b ,filler, a, filler, d, filler, e}; //cba = 0.5, ade = 1.
  		CMV cmv1 = new CMV(9, data1, param);
  		//Should be true, since cba < AREA2 and ade > AREA1.
  		assertThat(cmv1.LIC14(), equalTo(true));

  		//Test 2, check case where only one condition is met.
  		param.AREA1 = 2;
  		param.AREA2 = 2;
  		CMV cmv2 = new CMV(9, data1, param);
  		//Should be false, since cba and ade < AREA2 but biggest triangle ade (=1) not bigger AREA1 or AREA2.
  		assertThat(cmv2.LIC14(), equalTo(false));

  		//Test 3, too little data;
  		Point2D.Double[] data2 = {a, b, c, d};
  		CMV cmv3 = new CMV(4, data2, param);
  		//Should be false, only 4 data points, need 5.
  		assertThat(cmv3.LIC14(), equalTo(false));
  	}

}
