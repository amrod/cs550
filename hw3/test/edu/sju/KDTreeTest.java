package edu.sju;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ListIterator;


public class KDTreeTest {

    private KDTree t1;

    @Before
    public void setUp(){
        t1 = new KDTree(1, 1);
    }

    @Test
    public void testConstructor(){
        Assert.assertNotNull(t1);
    }

    @Test
    public void testEqualsOneLevel() {
        Assert.assertTrue("Not equals", t1.equals(new KDTree(1, 1)));
    }

    @Test
    public void testEquals2Levels() {
        KDTree a = new KDTree(1, 2, new KDTree(3, 4), new KDTree(5, 6));
        KDTree b = new KDTree(1, 2, new KDTree(3, 4), new KDTree(5, 6));
        Assert.assertTrue("Not equals", a.equals(b));
    }

    @Test
    public void testEqualsFalse() {
        KDTree a = new KDTree(1, 2, new KDTree(3, 4), new KDTree(5, 6));
        KDTree b = new KDTree(1, 2, new KDTree(3, 4), new KDTree(5, 5));
        Assert.assertFalse("Not equals", a.equals(b));
    }

    @Test
    public void testInsert1() {
        t1.insert(new Point2D(2, 2));
        Assert.assertTrue(t1.equals(new KDTree(1, 1, null, new KDTree(2, 2))));
    }

    @Test
    public void testInsert2() {
        KDTree t = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5));
        KDTree t2 = new KDTree(4, 4, new KDTree(2, 2, new KDTree(1, 1), null),
                new KDTree(5, 5));

        t.insert(new Point2D(1, 1));

        Assert.assertTrue(t.equals(t2));
    }

    @Test
    public void testInsert3() {
        KDTree t = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5));
        KDTree t2 = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5, null,
                new KDTree(6, 6)));

        t.insert(new Point2D(6, 6));

        Assert.assertTrue(t.equals(t2));
    }

    @Test
    public void testSearch1() {
        KDTree t = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5, null,
                new KDTree(6, 6)));

        Point2D result = t.search(new Point2D(6, 6));

        Assert.assertEquals(6.0, result.getAtIndex(0));
        Assert.assertEquals(6.0, result.getAtIndex(1));
    }

    @Test
    public void testSearch2() {
        KDTree t = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5, null,
                new KDTree(6, 6)));

        Point2D result = t.search(new Point2D(4, 4));

        Assert.assertEquals(4.0, result.getAtIndex(0));
        Assert.assertEquals(4.0, result.getAtIndex(1));
    }

    @Test
    public void testSearch3() {
        KDTree t = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5, null,
                new KDTree(6, 6)));

        Point2D result = t.search(new Point2D(7, 7));

        Assert.assertNull(result);
    }

    @Test
    public void testToString1() {
        Assert.assertEquals("(1.0, 1.0)\n\tnull\n\tnull\n", t1.toString());
    }

    @Test
    public void testToString2() {
        KDTree t = new KDTree(4, 4, new KDTree(2, 2), new KDTree(5, 5, null,
                new KDTree(6, 6)));

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
        KDTree t = new KDTree(4, 4, new KDTree(2, 2),
                new KDTree(5, 5, null, new KDTree(6, 6)));

        Point2D p = t.findMin(0);

        Assert.assertEquals(2.0, p.getAtIndex(0));
        Assert.assertEquals(2.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMinDim0() {
        KDTree t = new KDTree(4, 4,
                new KDTree(2, 2),
                new KDTree(5, 5, new KDTree(6, 4), new KDTree(0, 6)));

        Point2D p = t.findMin(0);

        Assert.assertEquals(0.0, p.getAtIndex(0));
        Assert.assertEquals(6.0, p.getAtIndex(1));
    }

    @Test
    public void testFindMinDim1() {
        KDTree t = new KDTree(4, 4,
                new KDTree(3.9, 3.9),
                new KDTree(5, 5, new KDTree(6, 2.5), new KDTree(0, 6)));

        Point2D p = t.findMin(1);

        Assert.assertEquals(6.0, p.getAtIndex(0));
        Assert.assertEquals(2.5, p.getAtIndex(1));
    }

    @Test
    public void testFindMinNull() {
        KDTree t = new KDTree();

        Point2D p = t.findMin(1);

        Assert.assertNull(p);
    }
}
