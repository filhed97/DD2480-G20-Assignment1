import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import java.awt.Point;


public class Tests {

    //Documentation : https://github.com/junit-team/junit4/wiki/Getting-started
    // Compile Tests class
    //   Linux and MAC :
    //   javac -cp .:junit-4.13.jar:hamcrest-core-1.3.jar Tests.java
    //
    //   Windows
    //   javac -cp .;junit-4.13.jar;hamcrest-core-1.3.jar Tests.java
    //
    // Run tests
    //   Linux or MacOS
    //   java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore Tests
    //   Windows
    //   java -cp .;junit-4.13.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Tests

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
      Point a,b,c,d,e;
      d = new Point(-2,0);
      e = new Point(3,0);
      //a, b and c are the points that can be contained in the circle
      a = new Point(0,0);
      b = new Point(1,0);
      c = new Point(0,1);


      Point[] data1 = {a,b,c,d,e}; // a,b,c can be contained
      CMV cmv1 = new CMV(5, data1, param);
      //data1 doesn't satisfy LIC1 thus should not be true
      assertThat(cmv1.DECIDE()[1], is(not(equalTo(true)))); //lots of syntatic sugar

      Point[] data2 = {a,d,b,e,c}; // cannot be contained
      CMV cmv2 = new CMV(5, data2, param);
      //data2 satisfy LIC1 thus should be true
      assertThat(cmv2.DECIDE()[1], is((equalTo(true))));

      //Let's create an equilateral triangle of side length 2
      b.setLocation(2,0);
      c.setLocation(1, Math.sqrt(3.0)); //Pythagoras => sqrt(3)^2 + 1^2 = 2^2
      Point[] data3 = {a,b,c};
      //data3 doesn't satisfy LIC1
      CMV cmv3 = new CMV(3, data3, param);
      assertThat(cmv3.DECIDE()[1], equalTo(false));
    }

    //Tests true iff there exists 2 consecutive points such that
    //the second has a strictly lower X coordinate than the first.
    //Limit case: X[i] - X[j] = 0
    @Test
    public void LIC5(){
      Point a = new Point(1,0);
      Point b = new Point(0,0);

      Point[] data1 = {a,b};
      CMV cmv1 = new CMV(2, data1, param);
      assertThat(cmv1.DECIDE()[5], equalTo(true));//b.X - a.X = -1 < 0

      Point[] data2 = {a,a,a,a,a,a};
      CMV cmv2 =  new CMV(6, data1,param);
      assertThat(cmv2.DECIDE()[5], equalTo(false));// a.X - a.X not strictly less than 0

      Point[] data3 = {b,a};
      CMV cmv3 =  new CMV(2, data1,param);
      assertThat(cmv3.DECIDE()[5], equalTo(false));//a.X - b.X = 1 > 0
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

      Point a = new Point(0,0);
      Point b = new Point(1,0);
      Point c = new Point(0,1);
      Point skip = new Point(0,0);

      param.EPSILON = 0;
      Point[] data1 = {a, skip, b, skip, c}; //abc is a PI/2 rad angle
      CMV cmv1 = new CMV(5, data1, param);
      //Should be true for any angle except PI
      assertThat(cmv1.DECIDE()[9], equalTo(true));

      Point d = new Point(2,0);
      Point[] data2 = {a, skip, b, skip, d}; //abd is a PI rad angle
      CMV cmv2 = new CMV(5, data2, param);
      //Should be false since angle should be different that PI
      assertThat(cmv2.DECIDE()[9], equalTo(false));

      param.EPSILON = Math.PI;
      CMV cmv3 = new CMV(5, data1, param);//abc is a PI/2 rad angle
      //Should be true for any angle except 0
      assertThat(cmv3.DECIDE()[9], equalTo(true));

      Point[] data3 = {a, skip, b, skip, a}; //aba is a 0 rad angle
      CMV cmv4 = new CMV(5, data3, param);
      //Should be false for angle 0
      assertThat(cmv4.DECIDE()[9], equalTo(false));

      CMV cmv5 = new CMV(4, data1, param);
      //false since NUMPOINTS has to be greater than 4
      assertThat(cmv5.DECIDE()[9], equalTo(false));

      Point[] data4 = {a, skip, a, skip, b}; //aab is undefined
      CMV cmv6 = new CMV(5, data4, param);
      //false since undefined angle
      assertThat(cmv6.DECIDE()[9], equalTo(false));
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
      param.RADIUS1 = 1;
      param.RADIUS2 = 2;
      Point a = new Point(0,0);
      Point b = new Point(2,0);
      Point c = new Point(1, 0);
      c.setLocation(1,Math.sqrt(3));
      Point skip = new Point (-5,0);
      //Equilateral triangle of length 2
      Point[] data1 = {a, skip, b, skip, c};

      CMV cmv1 = new CMV(5, data1, param);
      //abc is not contained RADIUS1 but is contained in RADIUS2
      assertThat(cmv1.DECIDE()[13], equalTo(true));

      Point d = new Point(4,0);
      //All a,b and c on x axis
      Point[] data2 = {a, skip, b, skip, d};
      CMV cmv2 = new CMV(5, data2, param);
      //True since a,b,d are exactly on circle of RADIUS2
      assertThat(cmv2.DECIDE()[13], equalTo(true));

      //Check didn't switch radius1 and radius2
      param.RADIUS1 = 2;
      param.RADIUS2 = 1;
      CMV cmv3 = new CMV(5, data1, param);
      //a,b,c can be contained in radius1 but cannot in radius2
      assertThat(cmv3.DECIDE()[13], equalTo(false)); //case both conditions are false

      param.RADIUS1 = 2;
      param.RADIUS2 = 2;
      CMV cmv4 = new CMV(4, data1, param);
      //a,b,c can be contained in radius1 and in radius2
      assertThat(cmv4.DECIDE()[13], equalTo(false)); //case radius1 is false

      param.RADIUS1 = 1;
      param.RADIUS2 = 1;
      CMV cmv5 = new CMV(5, data1, param);
      //a,b,c can be contained in either radius1 nor radius2
      assertThat(cmv5.DECIDE()[13], equalTo(false)); //case radius2 false
    }
}
