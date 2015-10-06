package edu.sju;

import junit.framework.TestCase;

import java.util.ListIterator;


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
        assertEquals(0, empty.getSize());
        assertEquals(1, one.getSize());
        assertEquals(3, multiple.getSize());
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
        assertEquals(1, list.getSize());
    }

    public void testAddAtEnd2() throws Exception {
        LinkedList<String> list = new LinkedList<>();
        list.add("foo");
        assertEquals("foo", list.get(0));
        list.add("bar");
        assertEquals("bar", list.get(1));
    }

    public void testAddAtEnd3() throws Exception {
        empty.add("foo");
        assertEquals(1, empty.getSize());
        one.add("banana");
        assertEquals(2, one.getSize());
        multiple.add("foo");
        assertEquals(4, multiple.getSize());
    }

    public void testAddAtIndex() throws Exception {
        empty.add(0, "foo");
        assertEquals(empty.getSize(), 1);
        one.add(1, "bar");
        assertEquals("Check size increases after adding item", 2, one.getSize());
        assertEquals("Check item was added at correct index", "bar", one.get(1));
    }

    public void testRemoveFromOneReturnsElement() throws Exception {
        assertEquals("Check correct value is returned when removing last element", "foo", one.remove());

    }

    public void testRemoveFromOneListIsEmpty() throws Exception {
        one.remove();
        assertEquals("Check list is empty after removing last element", 0, one.getSize());
    }

    public void testRemoveFromMultipleReturnsElement() throws Exception {
        assertEquals("Check correct value is returned when removing last element", "foo", multiple.remove());
    }

    public void testRemoveAtIndexOne() throws Exception {
        assertEquals("foo", one.remove(0));
        assertEquals("Check list is empty after removing last element", 0, one.getSize());
    }

    public void testRemoveAtIndexMultipleReturnsElements() throws Exception {
        assertEquals("bar", multiple.remove(1));
        assertEquals("baz", multiple.remove(1));
        assertEquals("foo", multiple.remove(0));
    }

    public void testRemoveAtIndexMultipleReportsCorrectSize() throws Exception {
        multiple.remove(1);
        assertEquals("Check list size is 1 after removing element", 2, multiple.getSize());
        multiple.remove(1);
        assertEquals("Check list size is 1 after removing element", 1, multiple.getSize());
        multiple.remove(0);
        assertEquals("Check list size is 1 after removing element", 0, multiple.getSize());
    }

    public void testRemoveLastMultipleChangesSize() throws Exception {
        multiple.removeLast();
        assertEquals("Check list size after removing last element", 2, multiple.getSize());
    }

    public void testRemoveLastMultiReturnsElement() throws Exception {
        assertEquals("baz", multiple.removeLast());
    }

    public void testRemoveLastOnoReturnsElement() throws Exception {
        assertEquals("Check removeLast on one-element list", "foo", one.removeLast());
    }

    public void testRemoveLastOneElmentEmptyList() throws Exception {
        one.removeLast();
        assertEquals("Check list is empty after removing last element", 0, one.getSize());
    }

    public void testIterarorAddIncrementsIndex() throws Exception {
        ListIterator<String> iter = empty.iterator();
        iter.add("foo");
        assertEquals(1, iter.nextIndex());
        assertEquals(0, iter.previousIndex());
    }
}