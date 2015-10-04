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
        assertEquals(0, empty.size());
        assertEquals(1, one.size());
        assertEquals(3, multiple.size());
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
        assertEquals("Check get multiple elements", "foo", multiple.get(0));
        assertEquals("Check get multiple elements", "bar", multiple.get(1));
        assertEquals("Check get multiple elements", "baz", multiple.get(2));
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
        assertEquals("foo", r);

        r = one.set(0, "foo");
        assertEquals("bar", r);

    }

    public void testAddAtEnd1() throws Exception {
        LinkedList<String> list = new LinkedList<>();
        list.add("foo");
        assertEquals(1, list.size());
    }

    public void testAddAtEnd2() throws Exception {
        empty.add("foo");
        assertEquals(1, empty.size());
        one.add("foo");
        assertEquals(2, one.size());
        multiple.add("foo");
        assertEquals(4, multiple.size());
    }

    public void testAddAtIndex() throws Exception {
        empty.add(0, "foo");
        assertEquals(empty.size(), 1);
        one.add(1, "bar");
        assertEquals("Check size increases after adding item", 2, one.size());
        assertEquals("Check item was added at correct index", "bar", one.get(1));
    }

    public void testRemove() throws Exception {
        assertEquals("Check correct value is returned when removing last element", "foo", one.remove());
        assertEquals("Check list is empty after removing last element", 0, one.size());
        assertEquals("Check correct value is returned when removing last element", "foo", multiple.remove());
    }

    public void testRemoveAtIndex() throws Exception {
        assertEquals("foo", one.remove(0));
        assertEquals("Check list is empty after removing last element", 0, one.size());
        assertEquals("bar", multiple.remove(1));
        assertEquals("baz", multiple.remove(1));
        assertEquals(1, multiple.size());
        assertEquals("Check list size is 1 after removing element", 1, multiple.size());
    }

    public void testRemoveLastMultipleChangesSize() throws Exception {
        multiple.removeLast();
        assertEquals("Check list size after removing last element", 2, multiple.size());
    }

    public void testRemoveLastMultiReturnsElement() throws Exception {
        assertEquals("baz", multiple.removeLast());
    }

    public void testRemoveLastOnReturnsElement() throws Exception {
        assertEquals("Check removeLast on one-element list", "foo", one.removeLast());
    }

    public void testRemoveLastOneElmentEmptyList() throws Exception {
        one.removeLast();
        assertEquals("Check list is empty after removing last element", 0, one.size());
    }
}