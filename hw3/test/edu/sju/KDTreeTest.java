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

//    @Test
//    public void testInsert() {
//        t1.insert(new Point2D(2, 1));
//    }

    @Test
    public void testEquals() {
        Assert.assertTrue(t1.equals(new KDTree(1, 1)));
    }


}
