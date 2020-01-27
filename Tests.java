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

    //Tests true iff there exists 3 consecutive points that together form
    // a triangle with area larger the AREA1
    @Test
    public void LIC3(){
        param.AREA1 = 4.5;
        Point a, b, c;
        a = new Point(0, 0);
        b = new Point(0, 3);
        c = new Point(3, 0);

        Point[] data1 = {a, b, c}; // a,b,c creates triangle with same area
        CMV cmv1 = new CMV(3, data1, param);
        //data1 doesn't satisfy LIC3 thus should not be true
        assertThat(cmv1.DECIDE()[3], is(not(equalTo(true))));

        Point d = new Point(6, 3);
        Point[] data2 = {a, b, c, d}; // b, c, d creates triangle with area larger than AREA1
        CMV cmv2 = new CMV(4, data2, param);
        //data2 satisfies LIC3 thus should be true
        assertThat(cmv2.DECIDE()[3], is(equalTo(true))); //lots of syntatic sugar

        param.AREA1 = 5;
        e = new Point(3, 3);
        Point[] data3 = {a, b, c, e, d}; // no triangle with area larger than AREA1 exists
        CMV cmv3 = new CMV(5, data3, param);
        //data1 doesn't satisfy LIC3 thus should not be true
        assertThat(cmv1.DECIDE()[3], is(not(equalTo(true))));
    }

    //Tests true if there exists two points separated by exactly KPTS consecutive intervening
    //a distance larger than LENGTH away from each other
    @Test
    public void LIC7(){
        param.KPTS = 1;
        param.Length = 3;
        Point a, b, c;

        a = new Point(0,0);
        b = new Point(2,2);
        c = new Point(4,0);

        Point[] data1 = {a, b, c}; // a and c are further than LENGTH apart
        CMV cmv1 = new CMV(3, data1, param);
        //data1 satisfies LIC7 thus should be true
        assertThat(cmv1.DECIDE()[7], is(equalTo(true))); //lots of syntatic sugar

        c.setLocation(3,0);
        Point[] data2 = {a, b, c}; // a and c are exactly Length apart
        CMV cmv2 = new CMV(3, data2, param);
        //data2 doesn't satisfy LIC7 thus should not be true
        assertThat(cmv2.DECIDE()[7], is(not(equalTo(true)))); //lots of syntatic sugar

        param.KPTS = 2;
        Point d = new Point(3, 3);
        Point[] data3 = {b, a, c, b, d}; // a and d are further than LENGTH apart
        CMV cmv3 = new CMV(5, data2, param);
        //data3 satisfies LIC7 thus should be true
        assertThat(cmv3.DECIDE()[7], is(equalTo(true))); //lots of syntatic sugar

        Point[] data4 = {b, c, a, b, d}; // no points separated by KPTS are further than LENGTH apart
        CMV cmv4 = new CMV(6, data4, param);
        //data4 doesn't satisfy LIC7 thus not should be true
        assertThat(cmv2.DECIDE()[7], is(not(equalTo(true)))); //lots of syntatic sugar
    }

    //Tests true iff there exists 2 points separated by exacytly GPTS such that
    //the second has a strictly lower X coordinate than the first.
    //Limit case: X[i] - X[j] = 0
    @Test
    public void LIC11(){
        param.GPTS = 1;
        Point a = new Point(2, 0);
        Point b = new Point(0, 0);
        Point c = new Point(1, 0);

        Point[] data1 = {a, a, b}; // b has lower x value than a
        CMV cmv1 = new CMV(3, data1, param);
        //data1 satisfies LIC11 thus should be true
        assertThat(cmv1.DECIDE()[11], equalTo(true));//b.X - a.X = -1 < 0

        Point[] data2 = {a,a,a,a,a,a}; //needs to be strictly smaller
        CMV cmv2 =  new CMV(6, data3,param);
        //data2 doesn't satisfy LIC7 thus should not be true
        assertThat(cmv2.DECIDE()[11], equalTo(false));// a.X - a.X not strictly less than 0

        Param.GPTS = 2;
        Point d = new Point(2, 2);
        Point[] data3 = {b, a, c, c, d, a}; //c and a satisfy the condition has c has a smallar x-value
        CMV cmv3 =  new CMV(6, data3,param);
        //data1 satisfies LIC11 thus should be true
        assertThat(cmv3.DECIDE()[5], equalTo(false));//a.X - b.X = 1 > 0

        Point[] data4 = {b, c, d, a, d, a}; // no two points separated by GPTS have the second one with lower x-value
        CMV cmv4 = new CMV(6, data4, param);
        //data4 doesn't satisfy LIC11 thus not should be true
        assertThat(cmv4.DECIDE()[7], is(not(equalTo(true)))); //lots of syntatic sugar
    }
}