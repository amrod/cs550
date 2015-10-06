package edu.sju;

import junit.framework.TestCase;


public class MyPolynomialTest extends TestCase {
    MyPolynomial poly1;
    MyPolynomial poly2;

    @Override
    protected void setUp() throws Exception {
        poly1 = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        poly2 = new MyPolynomial(new Integer[][] {{4, 5}, {-2, 3}, {-100, 2}, {40, 1}});
    }

    public void testDegree() throws Exception {
        assertEquals(poly1.degree(), 100);
    }

    public void testToString1() throws Exception {
        assertEquals("10x^100 + x^14 + 1", poly1.toString());

    }

    public void testToString2() throws Exception {
        assertEquals("4x^5 - 2x^3 - 100x^2 + 40x", poly2.toString());
    }

    public void testGetCoefFirst() throws Exception {
        assertEquals(10, poly1.getCoef(100));
        assertEquals(4, poly2.getCoef(5));
    }

    public void testGetCoefLast() throws Exception {
        assertEquals(1, poly1.getCoef(0));
        assertEquals(40, poly2.getCoef(1));
    }

    public void testGetCoefMiddle() throws Exception {
        assertEquals(1, poly1.getCoef(14));
        assertEquals(-2, poly2.getCoef(3));
    }

    public void testSetCoefZeroExp() throws Exception {
        poly1.setCoef(2, 0);
        assertEquals(2, poly1.getCoef(0));
    }

    public void testSetCoefMiddle() throws Exception {
        poly1.setCoef(2, 14);
        assertEquals(2, poly1.getCoef(14));
        poly2.setCoef(2, 3);
        assertEquals(2, poly2.getCoef(3));
    }

    public void testSetCoefNewMiddle() throws Exception {
        poly1.setCoef(2, 3);
        assertEquals(2, poly1.getCoef(3));
    }

    public void testSetCoefZero() throws Exception {
        poly2.setCoef(2, 0);
        assertEquals(2, poly2.getCoef(0));
    }

    public void testSetCoefLarger() throws Exception {
        poly2.setCoef(2, 100);
        assertEquals(2, poly2.getCoef(100));
    }

    public void testEqualsPolysAreEqual() throws Exception {
        MyPolynomial poly3 = new MyPolynomial(new Integer[][] {{4, 5}, {-2, 3}, {-100, 2}, {40, 1}});
        assertTrue(poly2.equals(poly3));
    }

    public void testEqualsPolysNotEqual() throws Exception {
        assertFalse(poly2.equals(poly1));
    }

    public void testEqualsPolysAreSimilar() throws Exception {
        MyPolynomial poly3 = new MyPolynomial(new Integer[][] {{4, 5}, {-2, 3}, {-100, 2}, {40, 1}, {1, 0}});
        assertFalse(poly2.equals(poly3));
    }

    public void testAddConmutation() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 2}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{20, 100}, {1, 14}, {1, 2}, {2, 0}});

        MyPolynomial result = polyA.add(polyB);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
        result = polyB.add(polyA);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testAdd2() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}, {1, 2}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{20, 100}, {1, 14}, {1, 2}, {1, 0}});

        MyPolynomial result = polyA.add(polyB);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testAdd3() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}, {1, 2}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{20, 100}, {1, 14}, {1, 2}, {1, 0}});

        MyPolynomial result = polyB.add(polyA);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testAdd4() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 2}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{20, 100}, {1, 14}, {1, 2}, {1, 0}});

        MyPolynomial result = polyA.add(polyB);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
        result = polyB.add(polyA);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testAddWithEmptyPolynomial() throws Exception {
        MyPolynomial polyA = new MyPolynomial();
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});

        MyPolynomial result = polyA.add(polyB);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
        result = polyB.add(polyA);
        assertTrue("Resulting Polynomial should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }


    /*  =============  */

    public void testSubtractConmutation() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 2}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{0, 100}, {1, 14}, {-1, 2}, {0, 0}});

        MyPolynomial result = polyA.subtract(polyB);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));

        expected = new MyPolynomial(new Integer[][] {{0, 100}, {-1, 14}, {1, 2}, {0, 0}});
        result = polyB.subtract(polyA);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testSubtract2() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}, {1, 2}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{0, 100}, {-1, 14}, {1, 2}, {-1, 0}});

        MyPolynomial result = polyA.subtract(polyB);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testSubtract3() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}, {1, 2}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{0, 100}, {1, 14}, {-1, 2}, {1, 0}});

        MyPolynomial result = polyB.subtract(polyA);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testSubtract4() throws Exception {
        MyPolynomial polyA = new MyPolynomial(new Integer[][] {{10, 100}});
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 2}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{0, 100}, {-1, 14}, {-1, 2}, {-1, 0}});

        MyPolynomial result = polyA.subtract(polyB);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));

        expected = new MyPolynomial(new Integer[][] {{0, 100}, {1, 14}, {1, 2}, {1, 0}});
        result = polyB.subtract(polyA);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

    public void testSubtractWithEmptyPolynomial() throws Exception {
        MyPolynomial polyA = new MyPolynomial();
        MyPolynomial polyB = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        MyPolynomial expected = new MyPolynomial(new Integer[][] {{-10, 100}, {-1, 14}, {-1, 0}});

        MyPolynomial result = polyA.subtract(polyB);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));

        expected = new MyPolynomial(new Integer[][] {{10, 100}, {1, 14}, {1, 0}});
        result = polyB.add(polyA);
        assertTrue("Subtraction result should be " + expected.toString() + ", not " + result.toString(), result.equals(expected));
    }

}
