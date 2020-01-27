import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import java.awt.Point;
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
    public void DECIDE(){
      CMV MockCMV = mock(CMV.class);
      boolean[] expected = {true,false,false,true,false,
                            true,true,true,false, true,
                            false,false,true,false,true};

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
      for (int i=0; i<15; i++) {
        assertThat(effective[i], equalTo(expected[i]));
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
      System.out.println("PUV[2] = " + main.PUV[2] +", PUM[2,14] = "+main.PUM[2][14]+", FUV[2] = "+main.FUV[2]);
      assertThat(main.FUV[2], equalTo(false));
      for (int i = 3; i < 15; i++ ) {
        assertThat(main.FUV[i], equalTo(true));
      }



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

      Point a = new Point(2,1);
      Point b = new Point(1,1);
      Point c = new Point(1,2);
	  Point d = new Point(0,1);
	  Point e = new Point(1,0)
	
	  //Test 1, check angle < PI-EPSILON.
      Point[] data1 = {a, b, c}; //abc is a PI/2 rad angle
      CMV cmv1 = new CMV(3, data1, param);
      //Should be true since PI/2 is less than PI-EPSILON=3PI/4
      assertThat(cmv1.DECIDE()[2], equalTo(true));

	  //Test 2, check angle > PI+EPSILON.
      Point[] data2 = {a, b, f}; //abf is a 3PI/2 rad angle
      CMV cmv2 = new CMV(5, data2, param);
      //Should be success since 3PI/2 is larger than 5PI/4
      assertThat(cmv2.DECIDE()[2], equalTo(true));

	  //Test 3, check if angle lays between PI-EPSILON and PI+EPSILON
      Point[] data3 = {a, b, d}; //abd is a PI rad angle
      CMV cmv3 = new CMV(3, data3, param);
      //Should be false since PI lies in PI +/- EPSILON
      assertThat(cmv3.DECIDE()[2], equalTo(false));

	  //Test 4, edge case if some point shares same coordinates as pivot point.
      Point[] data4 = {a, b, b}; //abb is undefined
      CMV cmv4 = new CMV(3, data4, param);
      //Should be false since angle is undefined.
      assertThat(cmv4.DECIDE()[2], equalTo(false));

	  //Test 5, edge case where EPSILON = 0.
	  param.EPSILON = 0;
	  Point[] data1 = {a, b, d}; //abc is a PI rad angle
      CMV cmv5 = new CMV(3, data1, param);
      //Should be false, since angle should be strictly smaller or larger than PI.
      assertThat(cmv1.DECIDE()[2], equalTo(false));
    }

}
