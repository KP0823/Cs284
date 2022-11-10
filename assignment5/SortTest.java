package assignment5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

//Kush Parmar
//I pledge my honor that I have abided by the Stevens Honor System
class SortTest {

	@Test
	public void test1() {
		Integer [] unsorted = {9,2,6,8,6};
		Integer [] sorted = {2,6,6,8,9};
		Sort.sort(unsorted);
		assertEquals(Arrays.toString(sorted), Arrays.toString(unsorted));
	}

	@Test
	public void test2() {
		Integer [] unsorted = {1};
		Integer [] sorted = {1};
		Sort.sort(unsorted);
		assertEquals(Arrays.toString(sorted), Arrays.toString(unsorted));
	}

	@Test
	public void test3() {
		Integer [] unsorted = {10,2,4,5,6,7,9,11};
		Integer [] sorted = {2,4,5,6,7,9,10,11};
		Sort.sort(unsorted);
		assertEquals(Arrays.toString(sorted), Arrays.toString(unsorted));
	}

	@Test
	public void test5() {
		Integer [] unsorted = {0,1,2,3,4,5,6,7,8,9,10,11};
		Integer [] sorted = {0,1,2,3,4,5,6,7,8,9,10,11};
		Sort.sort(unsorted);
		assertEquals(Arrays.toString(sorted), Arrays.toString(unsorted));
	}
	@Test
	public void test6() {
		Integer[] unsorted = {2,5,3,0,2,3,0,3};
		Integer[] sorted = {0,0,2,2,3,3,3,5};
		Sort.sort(unsorted);
		assertEquals(Arrays.toString(sorted), Arrays.toString(unsorted));
		

	}
}
