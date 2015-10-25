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
    
    public void testInsert() {
        t1.insert(new Point2D(2, 1));
    }


}
