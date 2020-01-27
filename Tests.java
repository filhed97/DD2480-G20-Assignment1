import static org.junit.Assert.*;
import org.junit.Test;

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
    public void WhatIsAssertedInTheTest() {
        // assert statements
        // You can find other assertions on the following website.
        //http://junit.sourceforge.net/javadoc/org/junit/Assert.html
        assertEquals(0, 10*0);
        assertFalse(0 == 1);
        assertNotEquals(1,1);

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
