//package Ex1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
    static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
    static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};
    static double[] po3 = {2,1,-0.7, -0.02,0.02};
    static double[] po4 = {-3, 0.61, 0.2};

    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testF() {
        double fx0 = Ex1.f(po1, 0);
        double fx1 = Ex1.f(po1, 1);
        double fx2 = Ex1.f(po1, 2);

        assertEquals(fx0, 2, Ex1.EPS);
        assertEquals(fx1, 4, Ex1.EPS);
        assertEquals(fx2, 6, Ex1.EPS);

    }
    @Test
    /**
     * Tests that p1(x) + p2(x) == (p1+p2)(x)
     */
    void testF2() {
        double x = Math.PI;
        double[] po12 = Ex1.add(po1, po2);
        double f1x = Ex1.f(po1, x);
        double f2x = Ex1.f(po2, x);
        double f12x = Ex1.f(po12, x);
        assertEquals(f1x + f2x, f12x, Ex1.EPS);
    }
    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testAdd() {
        double[] p12 = Ex1.add(po1, po2);
        double[] minus1 = {-1};
        double[] pp2 = Ex1.mul(po2, minus1);
        double[] p1 = Ex1.add(p12, pp2);
        assertTrue(Ex1.equals(p1, po1));
    }
    @Test
    /**
     * Tests that p1+p2 == p2+p1
     */
    void testAdd2() {
        double[] p12 = Ex1.add(po1, po2);
        double[] p21 = Ex1.add(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }
    @Test
    /**
     * Tests that p1+0 == p1
     */
    void testAdd3() {
        double[] p1 = Ex1.add(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, po1));
    }
    @Test
    /**
     * Tests that p1*0 == 0
     */
    void testMul1() {
        double[] p1 = Ex1.mul(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, Ex1.ZERO));
    }
    @Test
    /**
     * Tests that p1*p2 == p2*p1
     */
    void testMul2() {
        double[] p12 = Ex1.mul(po1, po2);
        double[] p21 = Ex1.mul(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }
    @Test
    /**
     * Tests that p1(x) * p2(x) = (p1*p2)(x),
     */
    void testMulDoubleArrayDoubleArray() {
        double[] xx = {0,1,2,3,4.1,-15.2222};
        double[] p12 = Ex1.mul(po1, po2);
        for(int i = 0;i<xx.length;i=i+1) {
            double x = xx[i];
            double f1x = Ex1.f(po1, x);
            double f2x = Ex1.f(po2, x);
            double f12x = Ex1.f(p12, x);
            assertEquals(f12x, f1x*f2x, Ex1.EPS);
        }
    }
    @Test
    /**
     * Tests a simple derivative examples - till ZERO.
     */
    void testDerivativeArrayDoubleArray() {
        double[] p = {1,2,3}; // 3X^2+2x+1
        double[] pt = {2,6}; // 6x+2
        double[] dp1 = Ex1.derivative(p); // 2x + 6
        double[] dp2 = Ex1.derivative(dp1); // 2
        double[] dp3 = Ex1.derivative(dp2); // 0
        double[] dp4 = Ex1.derivative(dp3); // 0
        assertTrue(Ex1.equals(dp1, pt));
        assertTrue(Ex1.equals(Ex1.ZERO, dp3));
        assertTrue(Ex1.equals(dp4, dp3));
    }
    @Test
    /**
     * Tests the parsing of a polynom in a String like form.
     */
    public void testFromString() {
        double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^2 +2.3x -1.1";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if(!isSame1) {fail();}
        if(!isSame2) {fail();}
        assertEquals(sp, Ex1.poly(p1));
    }
    @Test
    /**
     * Tests the equality of pairs of arrays.
     */
    public void testEquals() {
        double[][] d1 = {{0}, {1}, {1,2,0,0}};
        double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
        double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
        for(int i=0;i<d1.length;i=i+1) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for(int i=0;i<d1.length;i=i+1) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */
    public void testSameValue2() {
        double x1=-4, x2=0;
        double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
        assertEquals(rs1,rs2, Ex1.EPS);
    }
    @Test
    /**
     * Test the area function - it should be symmetric.
     */
    public void testArea() {
        double x1=-4, x2=0;
        double a1 = Ex1.area(po1, po2, x1, x2, 100);
        double a2 = Ex1.area(po2, po1, x1, x2, 100);
        assertEquals(a1,a2, Ex1.EPS);
    }
    @Test
    /**
     * Test the area f1(x)=0, f2(x)=x;
     */
    public void testArea2() {
        double[] po_a = Ex1.ZERO;
        double[] po_b = {0,1};
        double x1 = -1;
        double x2 = 2;
        double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
        double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
        double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
        double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
        double area =2.5;
        assertEquals(a1,area, Ex1.EPS);
        assertEquals(a2,area, Ex1.EPS);
        assertEquals(a3,area, Ex1.EPS);
        assertEquals(a100,area, Ex1.EPS);
    }
    @Test
    /**
     * Test the area function.
     */
    public void testArea3() {
        double[] po_a = {2,1,-0.7, -0.02,0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
        double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
        double area = 58.5658;
        assertEquals(a1,area, Ex1.EPS);
    }

    //tests for the function PolynomFromPoint

    @Test
    /**
     * test if there is no points given
     */
    public void testNullPolynomFromPoints(){
        double [] x1x2 = null;
        double [] y1y2 = null;
        assertNull(Ex1.PolynomFromPoints(x1x2,y1y2));
    }


    @Test
    /**
     * test if there is more than 3 points
     */
    public void testUpTo3PolynomFromPoints(){
        double [] x1x2 = {5,8,1.2,-7};
        double [] y1y2 = {87,-85,3.69,74.2};
        assertNull(Ex1.PolynomFromPoints(x1x2,y1y2));
    }

    // tests for 2 dots
    @Test
    /**
     * test positive values in dots
     */
    public void testPositive2PolynomFromPoints(){
        double [] x1x2 = {1,4};
        double [] y1y2 = {5,11};
        assertArrayEquals(new double[]{3,2}, Ex1.PolynomFromPoints(x1x2,y1y2));
    }

    @Test
    /**
     * test negative values in dots
     */
    public void testNegative2PolynomFromPoints(){
        double [] x1x2 = {-1,-4};
        double [] y1y2 = {-5,-11};
        assertArrayEquals(new double[]{-3,2}, Ex1.PolynomFromPoints(x1x2,y1y2));
    }

    @Test
    /**
     * test axis X function (example y=6 function)
     * note: don't need to test axis Y function because it's not a polynom
     */
    public void testAxisX2PolynomFromPoints(){
        double [] x1x2 = {8,-4};
        double [] y1y2 = {6,6};
        assertArrayEquals(new double[]{6,0}, Ex1.PolynomFromPoints(x1x2,y1y2));
    }


    @Test
    /**
     * tests division by 0 = null
     */
    public void testDiv02PolynomFromPoints(){
        double [] x1x2 = {3,3};
        double [] y1y2 = {18,-9};
        assertNull(Ex1.PolynomFromPoints(x1x2,y1y2));
    }


    @Test
    /**
     * tests function that has cut with axis x
     */
    public void testCutAxisX2PolynomFromPoints(){
        double [] x1x2 = {2,1};
        double [] y1y2 = {0,-1.7};
        assertArrayEquals(new double[]{-3.4,1.7}, Ex1.PolynomFromPoints(x1x2,y1y2));
    }


    @Test
    /**
     * tests function has cut with axis y
     */
    public void testCutAxisY2PolynomFromPoints(){
        double [] x1x2 = {0,4};
        double [] y1y2 = {2,4};
        assertArrayEquals(new double[]{2,0.5}, Ex1.PolynomFromPoints(x1x2,y1y2));
    }


    @Test
    /**
     * test function that has Origin dot (0,0)
     */
    public void testOrigin2PolynomFromPoints(){
        double [] x1x2 = {0,8};
        double [] y1y2 = {0,1.9};
        assertArrayEquals(new double[]{0,0.2375}, Ex1.PolynomFromPoints(x1x2,y1y2));
    }

    // tests for 3 dots
    @Test
    /**
     * test positive values in dots
     */
    public void testPositive3PolynomFromPoints(){
        double [] x1x2x3 = {3,1,2};
        double [] y1y2x3 = {53,13,30};
        assertArrayEquals(new double[]{2,8,3}, Ex1.PolynomFromPoints(x1x2x3,y1y2x3));
    }


    @Test
    /**
     * test negative values in dots
     */
    public void testNegative3PolynomFromPoints(){
        double [] x1x2x3 = {3,0,8.5};
        double [] y1y2x3 = {-68.7,-1.2,-426.2};
        assertArrayEquals(new double[]{-1.2, -7.5,-5}, Ex1.PolynomFromPoints(x1x2x3,y1y2x3));
    }

    @Test
    /**
     * test divide by 0 (when denom ==0)
     */
    public void testDenom0PolynomFromPoints(){
        double [] x1x2x3 = {50,50,8.5};
        double [] y1y2x3 = {-68.7,-1.2,-426.2};
        assertNull(Ex1.PolynomFromPoints(x1x2x3,y1y2x3));
    }

    @Test
    /**
     * test A=0 situation
     */
    public void testA0PolynomFromPoints(){
        double [] x1x2x3 = {10,4,60};
        double [] y1y2x3 = {-34,-6.4,-264};
        assertArrayEquals(new double[]{12, -4.6,0}, Ex1.PolynomFromPoints(x1x2x3,y1y2x3));
    }


    @Test
    /**
     * test value of 0 dot
     */
    public void testZeroPolynomFromPoints(){
        double [] x1x2x3 = {-1, 0, 2};
        double [] y1y2x3 = {-6, -1, -3};
        assertArrayEquals(new double[]{-1, 3, -2}, Ex1.PolynomFromPoints(x1x2x3,y1y2x3));
    }

    //tests for the function Poly
    @Test
    /**
     * tests positive values
     */
    public void testPositivePoly() {
        double[] p = {1.1, 2.3, 3.1};
        assertEquals("3.1x^2 +2.3x +1.1", Ex1.poly(p));
    }

    @Test
    /**
     * tests negative values
     */
    public void testNegativePoly() {
        double[] p = {-1.1, -2.3, -3.1};
        assertEquals("-3.1x^2 -2.3x -1.1", Ex1.poly(p));
    }

    @Test
    /**
     * tests zero value
     */
    public void testHasZeroPoly() {
        double[] p = {1.1, 0, 3.1};
        assertEquals("3.1x^2 +1.1", Ex1.poly(p));
    }


    @Test
    /**
     * tests long functions
     */
    public void testLongPoly() {
        double[] p = {1.1, 0, 3.1,-5.6, 855, 1, -13};
        assertEquals("-13.0x^6 +x^5 +855.0x^4 -5.6x^3 +3.1x^2 +1.1", Ex1.poly(p));
    }

    @Test
    /**
     * tests value 1 (need to be x and not 1.0x)
     */
    public void testOnePoly() {
        double[] p = {5,1,8.7,-14, -1};
        assertEquals("-x^4 -14.0x^3 +8.7x^2 +x +5.0",Ex1.poly(p));
    }


    @Test
    /**
     * tests function with only 1 value
     */
    public void testShortPoly() {
        double[] p = {7};
        assertEquals("7.0", Ex1.poly(p));
    }


    @Test
    /**
     * tests function of f(x) = 0
     */
    public void testZeroPoly() {
        double[] p = {0,0,0,0};
        assertEquals("0", Ex1.poly(p));
    }

    @Test
    /**
     * tests empty array
     */
    public void testEmptyPoly() {
        double[] p = {};
        assertEquals("0", Ex1.poly(p));
    }

    @Test
    /**
     * tests null array
     */
    public void testNullPoly() {
        double[] p = null;
        assertNull(Ex1.poly(p));
    }


// tests for the function equals

    @Test
    /**
     * tests if 2 functions are equal when promoters are positive
     */
    public void testEqualsPositive() {
        double[] p1 = {1.1, 0, 3.1}; // 3.1X^2 +1.1
        double[] p2 = {1.1, 0, 3.1}; // 3.1X^2 +1.1

        assertTrue(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when promoters are negative
     */
    public void testEqualsNegative() {
        double[] p1 = {-8.9, -70, -536.9}; // -536.9X^2-70X-8.9
        double[] p2 = {-8.9, -70, -536.9}; // -536.9X^2-70X-8.9

        assertTrue(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when length isn't equal
     */
    public void testEqualsP1Longer() {
        double[] p1 = {-8.9, -70, -536.9, 5.8}; // -5.8X^3-536.9X^2-70X-8.9
        double[] p2 = {-8.9, -70, -536.9}; // -536.9X^2-70X-8.9

        assertFalse(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when length isn't equal
     */
    public void testEqualsP2Longer() {
        double[] p1 = {-8.9, -70, -536.9, 5.8};
        double[] p2 = {-8.9, -70, -536.9, 5.8,-7.2,-100};

        assertFalse(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when length is equal but other value
     */
    public void testEqualsSameLengthOtherValue() {
        double[] p1 = {-8.9, 508, -536.9, 5.8};
        double[] p2 = {-8.9, -70, -536.9, 5.8};

        assertFalse(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when all promoters are 0
     */
    public void testEqualsZero() {
        double[] p1 = {0,0,0,0,0,0}; //0X^5+0X^4+0X^3+0X^2+0X+0
        double[] p2 = {0,0,0,0,0,0};

        assertTrue(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when promoter is epsilon
     */
    public void testEqualsEpsilon() {
        double[] p1 = {1.0000001, 2};
        double[] p2 = {1.0000002, 2};

        assertFalse(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when one is null
     */
    public void testEqualsNull() {
        double[] p1 = null;
        double[] p2 = {-7.8};

        assertFalse(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when promotor is 0 and other is null
     */
    public void testEqualsZeros() {
        double[] p1 = {4.8,5,-95}; // -95X^2+5X+4.8
        double[] p2 = {4.8,5,-95,0,0,0}; // 0X^5+0X+4+0X^3-95X^2+5X+4.8

        assertTrue(Ex1.equals(p1, p2));
    }


    @Test
    /**
     * tests if 2 functions are equal when promotor is 0 and other is null
     */
    public void testEqualsOtherZeros() {
        double[] p1 = {4.8,5,-95,0,0,0}; // 0X^5+0X+4+0X^3-95X^2+5X+4.8
        double[] p2 = {4.8,5,-95}; // -95X^2+5X+4.8

        assertTrue(Ex1.equals(p1, p2));
    }

    //tests for the function derivative
    @Test
    /**
     * tests positive values
     */
    public void testPositiveDerivative() {
        double[] p = {3,5,20,9}; //9x^3 +20x^2 +5x +3, der = 18x^2 +40x +5
        double[] der = {5,40,27};
        assertArrayEquals(der, Ex1.derivative(p));
    }

    @Test
    /**
     * tests negative values
     */
    public void testNegativeDerivative() {
        double[] p = {-5,-1.2,-7,-2};
        double[] der = {-1.2, -14,-6};
        assertArrayEquals(der, Ex1.derivative(p));
    }


    @Test
    /**
     * tests zero values
     */
    public void testZeroDerivative() {
        double[] p = {0,-1.2,0,-2};
        double[] der = {-1.2, 0,-6};
        assertArrayEquals(der, Ex1.derivative(p));
    }


    @Test
    /**
     * tests all zero values
     */
    public void testAllZeroDerivative() {
        double[] p = {0,0,0,0};
        double[] der = {0, 0,0};
        assertArrayEquals(der, Ex1.derivative(p));
    }


}
