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

    //Tests true if there exists two consecutive points 
    //a distance larger than LENGTH away from each other
    @Test 
    public void LIC0(){
        param.Length = 3;
        Point a, b;
        
        a = new Point(0, 0);
        b = new Point(2, 2);

        Point[] data1 = {a,b}; // a and b are too close
        CMV cmv1 = new CMV(2, data1, param);
        //data1 doesn't satisfy LIC0 thus should not be true
        assertThat(cmv1.DECIDE()[0], is(not(equalTo(true)))); //lots of syntatic sugar

        Point c = new Point(4, 0);
        Point[] data2 = {a,c,b}; // a and c are further than LENGTH apart
        CMV cmv2 = new CMV(3, data2, param);
        //data2 satisfies LIC0 thus should be true
        assertThat(cmv2.DECIDE()[0], is(equalTo(true))); //lots of syntatic sugar

        c.setLocation(3, 0);
        Point[] data3 = {a,c,b}; // a and c are exactly Length apart
        CMV cmv3 = new CMV(3, data3, param);
        //data3 doesn't satisfy LIC0 thus should not be true
        assertThat(cmv3.DECIDE()[0], is(not(equalTo(true)))); //lots of syntatic sugar
    }
}
