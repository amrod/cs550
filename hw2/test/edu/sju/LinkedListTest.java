package edu.sju;

import junit.framework.TestCase;


public class LinkedListTest extends TestCase {


    private LinkedList<String> empty;
    private LinkedList<String> one;
    private LinkedList<String> multiple;


    public LinkedListTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        super.setUp();
        empty = new LinkedList<>();
        one = new LinkedList<>();
        one.add("foo");
        multiple = new LinkedList<>();
        multiple.add("foo");
        multiple.add("bar");
        multiple.add("baz");

    }

    public void testIterator() throws Exception {
        assertTrue(one.iterator() != null);
    }

    public void testSize() throws Exception {
        assertEquals(empty.size(), 0);
        assertEquals(one.size(), 1);
        assertEquals(multiple.size(), 3);
    }

    public void testGetThrowsIndexOutOfBoundsException() throws Exception {
        Throwable e = null;

        try {
            empty.get(0);
        } catch (Throwable ex) {
            e = ex;
        }

        assertTrue("Check get index 0 on empty list throws IndexOutOfBoundsException",
                e instanceof IndexOutOfBoundsException);

        e = null;
        try {
            multiple.get(3);
        } catch (Throwable ex) {
            e = ex;
        }

        assertTrue("Check get index 3 on length-3 list throws IndexOutOfBoundsException",
                e instanceof IndexOutOfBoundsException);

    }
    public void testGet() throws Exception {
        assertEquals("Check get one element", one.get(0), "foo");
        assertEquals("Check get multiple elements", multiple.get(0), "foo");
        assertEquals("Check get multiple elements", multiple.get(1), "bar");
        assertEquals("Check get multiple elements", multiple.get(2), "baz");
    }

    public void testSetThrowsIndexOutOfBoundsException() throws Exception {

        Throwable e = null;

        try {
            empty.set(0, "foo");
        } catch (Throwable ex) {
            e = ex;
        }

        assertTrue("Check set index 0 on empty list throws IndexOutOfBoundsException",
                e instanceof IndexOutOfBoundsException);

        e = null;
        try {
            one.set(1, "foo");
        } catch (Throwable ex) {
            e = ex;
        }

        assertTrue("Check set index 1 on length-1 list throws IndexOutOfBoundsException",
                e instanceof IndexOutOfBoundsException);
    }

    public void testSet() throws Exception {
        String r = one.set(0, "bar");
        assertEquals(r, "foo");

        r = one.set(0, "foo");
        assertEquals(r, "bar");

    }

    public void testAddAtEnd1() throws Exception {
        LinkedList<String> list = new LinkedList<>();
        list.add("foo");
        assertEquals(list.size(), 1);
    }

    public void testAddAtEnd2() throws Exception {
        empty.add("foo");
        assertEquals(empty.size(), 1);
        one.add("foo");
        assertEquals(one.size(), 2);
        multiple.add("foo");
        assertEquals(multiple.size(), 4);
    }

    public void testAddAtIndex() throws Exception {
        empty.add(0, "foo");
        assertEquals(empty.size(), 1);
        one.add(1, "bar");
        assertEquals("Check size increases after adding item", one.size(), 2);
        assertEquals("Check item was added at correct index", one.get(1), "bar");
    }

    public void testRemove() throws Exception {
        assertEquals("Check correct value is returned when removing last element", one.remove(), "foo");
        assertEquals("Check list is empty after removing last element", one.size(), 0);
        assertEquals("Check correct value is returned when removing last element", multiple.remove(), "baz");
    }

    public void testRemoveAtIndex() throws Exception {
        assertEquals(one.remove(0), "foo");
        assertEquals("Check list is empty after removing last element", one.size(), 0);
        assertEquals(multiple.remove(1), "bar");
        assertEquals(multiple.remove(1), "baz");
        assertEquals(multiple.size(), 1);
        assertEquals("Check list size is 1 after removing element", one.size(), 1);
    }

    public void testRemoveLast() throws Exception {
        assertEquals("Check removeLast on one-element list", one.removeLast(), "foo");
        assertEquals("Check list is empty after removing last element", one.size(), 0);
        assertEquals(multiple.removeLast(), "baz");
        assertEquals("Check list size after removing last element", multiple.size(), 2);
    }
}