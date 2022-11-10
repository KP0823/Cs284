package Treap;
// Kush Parmar
// I pledge my honor that i have abided by the stevens honor system-Kush Parmar
import static org.junit.Assert.*;

import org.junit.Test;

public class TreapTest {

	@Test
	public void testAdd() {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4, 19);
		testTree.add(2, 31);
		testTree.add(6, 70);
		testTree.add(1, 84);
		testTree.add(3, 12);
		testTree.add(5, 83);
		testTree.add(7, 26);
		testTree.add(8, 26);

		assertTrue(testTree.find(7));
		
		assertFalse(testTree.find(8));
		
	}
	@Test
	public void testFind() {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4, 19);
		testTree.add(2, 31);
		testTree.add(6, 70);
		testTree.add(1, 84);
		testTree.add(3, 12);
		testTree.add(5, 83);
		testTree.add(7, 26);
		assertTrue(testTree.find(7));
		assertFalse(testTree.find(8));
	}
	@Test
	public void testDelete() {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4, 19);
		testTree.add(2, 31);
		testTree.add(6, 70);
		testTree.add(1, 84);
		testTree.add(3, 12);
		testTree.add(5, 83);
		testTree.add(7, 26);

		assertTrue(testTree.find(7));
		testTree.delete(7);
		assertFalse(testTree.find(7));
		assertFalse(testTree.delete(8));	
	}
	
	@Test
	public void testString() {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(1, 84);
		String answerString= "(key=1, priority=84)\n"
				+ " null\n"
				+ " null\n"
				+ "";
		String checkToString= testTree.toString();
		assertEquals(checkToString, answerString);
	}

}
