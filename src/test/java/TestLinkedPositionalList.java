import es.urjc.grafo.EDA.LinkedPositionalList;
import es.urjc.grafo.EDA.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class TestLinkedPositionalList {

    @Test
    void testEmptyList() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            Assertions.assertTrue(list.isEmpty());
            Assertions.assertEquals(0, list.size());
            Assertions.assertNull(list.first());
            Assertions.assertNull(list.last());
        } catch (Exception e) {
            Assertions.fail("Exception thrown in empty list test: " + e.getMessage());
        }
    }

    @Test
    void testAddFirst() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addFirst(10);
            Assertions.assertEquals(10, list.first().getElement());
            Assertions.assertEquals(10, list.last().getElement());
            Assertions.assertFalse(list.isEmpty());
            Assertions.assertEquals(1, list.size());

            list.addFirst(20);
            Assertions.assertEquals(2, list.size());
            Assertions.assertEquals(20, list.first().getElement());
            Assertions.assertEquals(10, list.last().getElement());
            Position<Integer> firstPosition = list.first();
            Position<Integer> lastPosition = list.last();
            Assertions.assertEquals(lastPosition, list.after(firstPosition));
            Assertions.assertEquals(firstPosition, list.before(lastPosition));
        } catch (Exception e) {
            Assertions.fail("Exception thrown in addFirst test: " + e.getMessage());
        }
    }

    @Test
    void testAddLast() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addLast(10);
            Assertions.assertEquals(10, list.first().getElement());
            Assertions.assertEquals(10, list.last().getElement());
            Assertions.assertFalse(list.isEmpty());
            Assertions.assertEquals(1, list.size());

            list.addLast(20);
            Assertions.assertEquals(2, list.size());
            Assertions.assertEquals(10, list.first().getElement());
            Assertions.assertEquals(20, list.last().getElement());
            Position<Integer> firstPosition = list.first();
            Position<Integer> lastPosition = list.last();
            Assertions.assertEquals(lastPosition, list.after(firstPosition));
            Assertions.assertEquals(firstPosition, list.before(lastPosition));
        } catch (Exception e) {
            Assertions.fail("Exception thrown in addLast test: " + e.getMessage());
        }
    }

    @Test
    void testAddBeforeAfter() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addLast(10);
            list.addLast(30);
            Position<Integer> firstPosition = list.first();
            Position<Integer> lastPosition = list.last();

            list.addAfter(firstPosition, 20);
            Assertions.assertEquals(3, list.size());
            Assertions.assertEquals(20, list.after(firstPosition).getElement());
            Assertions.assertEquals(lastPosition, list.after(list.after(firstPosition)));

            list.addBefore(lastPosition, 25);
            Assertions.assertEquals(4, list.size());
            Assertions.assertEquals(25, list.before(lastPosition).getElement());
            Assertions.assertEquals(firstPosition, list.before(list.before(list.before(lastPosition))));
        } catch (Exception e) {
            Assertions.fail("Exception thrown in addBeforeAfter test: " + e.getMessage());
        }
    }

    @Test
    void testSet() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);
            Position<Integer> firstPosition = list.first();
            Position<Integer> lastPosition = list.last();
            Position<Integer> middlePosition = list.after(firstPosition);

            list.set(middlePosition, 25);
            Assertions.assertEquals(25, middlePosition.getElement());
            Assertions.assertEquals(10, firstPosition.getElement());
            Assertions.assertEquals(30, lastPosition.getElement());
        } catch (Exception e) {
            Assertions.fail("Exception thrown in set test: " + e.getMessage());
        }
    }

    @Test
    void testRemove() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);
            Position<Integer> firstPosition = list.first();
            Position<Integer> lastPosition = list.last();
            Position<Integer> middlePosition = list.after(firstPosition);

            Integer removedElement = list.remove(middlePosition);
            Assertions.assertEquals(20, removedElement);
            Assertions.assertEquals(2, list.size());
            Assertions.assertEquals(lastPosition, list.after(firstPosition));
            Assertions.assertEquals(firstPosition, list.before(lastPosition));

            removedElement = list.remove(firstPosition);
            Assertions.assertEquals(10, removedElement);
            Assertions.assertEquals(1, list.size());
            Assertions.assertEquals(lastPosition, list.first());
            Assertions.assertEquals(lastPosition, list.last());

            removedElement = list.remove(lastPosition);
            Assertions.assertEquals(30, removedElement);
            Assertions.assertTrue(list.isEmpty());
            Assertions.assertNull(list.first());
            Assertions.assertNull(list.last());
        } catch (Exception e) {
            Assertions.fail("Exception thrown in remove test: " + e.getMessage());
        }
    }

    @Test
    void testIterator() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);

            StringBuilder sb = new StringBuilder();
            for (Integer value : list) {
                sb.append(value).append(" ");
            }
            Assertions.assertEquals("10 20 30 ", sb.toString());

            sb = new StringBuilder();
            Iterator<Position<Integer>> iterator = list.positionIterator();
            while (iterator.hasNext()) {
                Integer value = iterator.next().getElement();
                sb.append(value).append(" ");
            }
            Assertions.assertEquals("10 20 30 ", sb.toString());
        } catch (Exception e) {
            Assertions.fail("Exception thrown in iterator test: " + e.getMessage());
        }
    }

    @Test
    void testIterable() {
        try {
            LinkedPositionalList<Integer> list = new LinkedPositionalList<>();
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);

            StringBuilder sb = new StringBuilder();
            for (Integer value : list.elements()) {
                sb.append(value).append(" ");
            }
            Assertions.assertEquals("10 20 30 ", sb.toString());

            sb = new StringBuilder();
            for (Position<Integer> position : list.positions()) {
                sb.append(position.getElement()).append(" ");
            }
            Assertions.assertEquals("10 20 30 ", sb.toString());
        } catch (Exception e) {
            Assertions.fail("Exception thrown in iterable test: " + e.getMessage());
        }
    }
}
