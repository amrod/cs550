package edu.sju;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class KDTreeTest {

    private KDTree2D t1;

    @Before
    public void setUp(){
        t1 = new KDTree2D(1, 1);
    }

    @Test
    public void testConstructor(){
        Assert.assertNotNull(t1);
    }

    @Test
    public void testEqualsOneLevel() {
        Assert.assertTrue("Not equals", t1.equals(new KDTree2D(1, 1)));
    }

    @Test
    public void testEquals2Levels() {
        KDTree2D a = new KDTree2D(1, 2, new KDTree2D(3, 4), new KDTree2D(5, 6));
        KDTree2D b = new KDTree2D(1, 2, new KDTree2D(3, 4), new KDTree2D(5, 6));
        Assert.assertTrue("Not equals", a.equals(b));
    }

    @Test
    public void testEqualsFalse() {
        KDTree2D a = new KDTree2D(1, 2, new KDTree2D(3, 4), new KDTree2D(5, 6));
        KDTree2D b = new KDTree2D(1, 2, new KDTree2D(3, 4), new KDTree2D(5, 5));
        Assert.assertFalse("Not equals", a.equals(b));
    }

    @Test
    public void testInsert1() {
        t1.insert(new Point2D(2, 2));
        Assert.assertTrue(t1.equals(new KDTree2D(1, 1, null, new KDTree2D(2, 2))));
    }

    @Test
    public void testInsert2() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5));
        KDTree2D t2 = new KDTree2D(4, 4, new KDTree2D(2, 2, new KDTree2D(1, 1), null),
                new KDTree2D(5, 5));

        t.insert(new Point2D(1, 1));

        Assert.assertTrue(t.equals(t2));
    }

    @Test
    public void testInsert3() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5));
        KDTree2D t2 = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5, null,
                new KDTree2D(6, 6)));

        t.insert(new Point2D(6, 6));

        Assert.assertTrue(t.equals(t2));
    }

    @Test
    public void testSearch1() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5, null,
                new KDTree2D(6, 6)));

        Point2D result = t.search(new Point2D(6, 6));

        Assert.assertEquals(6.0, result.getAtIndex(0));
        Assert.assertEquals(6.0, result.getAtIndex(1));
    }

    @Test
    public void testSearch2() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5, null,
                new KDTree2D(6, 6)));

        Point2D result = t.search(new Point2D(4, 4));

        Assert.assertEquals(4.0, result.getAtIndex(0));
        Assert.assertEquals(4.0, result.getAtIndex(1));
    }

    @Test
    public void testSearch3() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5, null,
                new KDTree2D(6, 6)));

        Point2D result = t.search(new Point2D(7, 7));

        Assert.assertNull(result);
    }

    @Test
    public void testToString1() {
        Assert.assertEquals("(1.0, 1.0)\n\tnull\n\tnull\n", t1.toString());
    }

    @Test
    public void testToString2() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2), new KDTree2D(5, 5, null,
                new KDTree2D(6, 6)));

        Assert.assertEquals("(4.0, 4.0)\n\t(2.0, 2.0)\n\t\tnull\n\t\tnull" +
                "\n\t(5.0, 5.0)\n\t\tnull\n\t\t(6.0, 6.0)\n\t\t\tnull" +
                "\n\t\t\tnull\n", t.toString());
    }

    @Test
    public void testFindMin1() {
        Point2D p = t1.findMin(0);

        Assert.assertEquals(1.0, p.getAtIndex(0));
        Assert.assertEquals(1.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMin2() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2),
                new KDTree2D(5, 5, null, new KDTree2D(6, 6)));

        Point2D p = t.findMin(0);

        Assert.assertEquals(2.0, p.getAtIndex(0));
        Assert.assertEquals(2.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMinDim0() {
        KDTree2D t = new KDTree2D(4, 4,
                new KDTree2D(2, 2),
                new KDTree2D(5, 5, new KDTree2D(6, 4), new KDTree2D(0, 6)));

        Point2D p = t.findMin(0);

        Assert.assertEquals(0.0, p.getAtIndex(0));
        Assert.assertEquals(6.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMinDim1() {
        KDTree2D t = new KDTree2D(4, 4,
                new KDTree2D(3.9, 3.9),
                new KDTree2D(5, 5, new KDTree2D(6, 2.5), new KDTree2D(0, 6)));

        Point2D p = t.findMin(1);

        Assert.assertEquals(6.0, p.getAtIndex(0));
        Assert.assertEquals(2.5, p.getAtIndex(1));
    }

    @Test
    public void testFindMinNull() {
        KDTree2D t = new KDTree2D();

        Point2D p = t.findMin(1);

        Assert.assertNull(p);
    }
    
    /*********************************************/

    @Test
    public void testFindMax1() {
        Point2D p = t1.findMax(0);

        Assert.assertEquals(1.0, p.getAtIndex(0));
        Assert.assertEquals(1.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMax2() {
        KDTree2D t = new KDTree2D(4, 4, new KDTree2D(2, 2),
                new KDTree2D(5, 5, null, new KDTree2D(6, 6)));

        Point2D p = t.findMax(0);

        Assert.assertEquals(6.0, p.getAtIndex(0));
        Assert.assertEquals(6.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMaxDim0() {
        KDTree2D t = new KDTree2D(4, 4,
                new KDTree2D(2, 2),
                new KDTree2D(5, 5, new KDTree2D(6, 4), new KDTree2D(0, 6)));

        Point2D p = t.findMax(0);

        Assert.assertEquals(6.0, p.getAtIndex(0));
        Assert.assertEquals(4.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMaxDim1() {
        KDTree2D t = new KDTree2D(4, 4,
                new KDTree2D(3.9, 10),
                new KDTree2D(5, 5, new KDTree2D(6, 2.5), new KDTree2D(0, 6)));

        Point2D p = t.findMax(1);

        Assert.assertEquals(3.9, p.getAtIndex(0));
        Assert.assertEquals(10.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMaxNull() {
        KDTree2D t = new KDTree2D();

        Point2D p = t.findMax(1);

        Assert.assertNull(p);
    }

    @Test
    public void testPrintRange1() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Points: (30,40) (5,25) (18,10) (80,90) (55,30) (35,45)
        KDTree2D t = new KDTree2D(30, 40, new KDTree2D(5, 25, new KDTree2D(18, 10), null),
                new KDTree2D(80, 90, new KDTree2D(55, 30, new KDTree2D(35, 45), null), null));

        Point2D llc = new Point2D(5, 25);
        Point2D urc = new Point2D(80, 90);

        t.printRange(llc, urc);

        Assert.assertEquals("(30.0, 40.0), (5.0, 25.0), (80.0, 90.0), (55.0, 30.0), (35.0, 45.0)", outContent.toString());

        System.setOut(oldOut);
    }

    @Test
    public void testPrintRangeAllPoints() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Points: (30,40) (5,25) (18,10) (80,90) (55,30) (35,45)
        KDTree2D t = new KDTree2D(30, 40, new KDTree2D(5, 25, new KDTree2D(18, 10), null),
                new KDTree2D(80, 90, new KDTree2D(55, 30, new KDTree2D(35, 45), null), null));

        Point2D llc = new Point2D(0, -1.5);
        Point2D urc = new Point2D(100, 200);

        t.printRange(llc, urc);

        Assert.assertEquals("(30.0, 40.0), (5.0, 25.0), (18.0, 10.0), (80.0, 90.0), (55.0, 30.0), (35.0, 45.0)", outContent.toString());

        System.setOut(oldOut);
    }

    @Test
    public void testPrintRangeNoPoints() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Points: (30,40) (5,25) (18,10) (80,90) (55,30) (35,45)
        KDTree2D t = new KDTree2D(30, 40, new KDTree2D(5, 25, new KDTree2D(18, 10), null),
                new KDTree2D(80, 90, new KDTree2D(55, 30, new KDTree2D(35, 45), null), null));

        Point2D llc = new Point2D(100, 100);
        Point2D urc = new Point2D(200, 200);

        t.printRange(llc, urc);

        Assert.assertEquals("", outContent.toString());

        System.setOut(oldOut);
    }
}
