package assignment5;

import java.util.Set;
import java.util.HashSet;

//Kush Parmar
//I pledge my honor that I have abided by the Stevens Honor System
public class Sort {
	static Set<Interval> setOfInterval = new HashSet<>(); // did i do this correctly

	public static class Interval {
		private int lower;
		private int upper;

		/**
		 * Constructor to make an Interval
		 * 
		 * @param lower- is the lower bound of the array
		 * @param upper- is the upper
		 */
		public Interval(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}

		/**
		 * @return the lower bound
		 */
		public int getLower() {
			return lower;
		}

		/**
		 * @return the upper bound
		 */
		public int getUpper() {
			return upper;
		}

		/**
		 * @return true if this interval and the given interval have the same lower and
		 * upper bounds
		 * 
		 * @param O- An Interval
		 */
		public boolean equals(Object o) {
			Interval checker = (Interval) o;
			if (checker.getLower() == lower && checker.getUpper() == upper) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * @return lower * lower + upper
		 */
		public int hashCode() {
			return (lower * lower) + upper;
		}

	}
	/**
	 * Swaps the two specific elements within an array
	 * @param <T> Generic data type
	 * @param table - the array
	 * @param first - the first element that will be swapped with last
	 * @param last - the second element that will be swapped with first
	 */
	private static <T extends Comparable<T>> void swap(T[] table, int first, int last) {
		T temp = table[first];
		table[first] = table[last];
		table[last] = temp;
	}

	/**
	 * Takes the the first, middle, last, element within a a certain bound, and places them lowest to highest.
	 * @param <T> Generic data type
	 * @param table- the array
	 * @param first- the first element that might be swapped with last or middle
	 * @param last- the third element that might be swapped with first or middle
	 * @param middle- the second element that might be swapped with last or first
	 */
	private static <T extends Comparable<T>> void fixMiddle(T[] table, int first, int last, int middle) {
		if (table[first].compareTo(table[middle]) > 0) {
			swap(table, middle, first);
		}
		if (table[first].compareTo(table[last]) > 0) {
			swap(table, first, last);
		}
		if (table[middle].compareTo(table[last]) > 0) {
			swap(table, middle, last);
		}

	}
	/**
	 * Find where the the new sub arrays should be made while also sorting the bounds
	 * @param <T>- Generic data type
	 * @param table- the array 
	 * @param first- The first bound which is the lower bound
	 * @param last- The second bound which is the higher bound
	 * @return the value of down to pivot index
	 */
	private static <T extends Comparable<T>> int partition(T[] table, int first, int last) {
		int middle = (first + last) / 2;
		int up = first;
		int down = last;
		fixMiddle(table, first, last, middle);
		swap(table, first, middle);
		T pivot = table[middle];
		do {
			while (up < last && pivot.compareTo(table[up]) >= 0) {
				up++;
			}
			while (down > first && pivot.compareTo(table[down]) < 0) {
				down--;
			}
			if (up < down) {
				swap(table, up, down);
			}
		} while (up < down);
		swap(table, first, down);
		return down;

	}

	/**
	 * The main sort function that utilizes bubbleSort, and partition. Sort Uses a set to store the intervals, and through there intervals the bounds of smaller sub arrays are formed.
	 * @param <T> Generic data type
	 * @param array-  the array that needs to be sorted.
	 */
	public static <T extends Comparable<T>> void sort(T[] array) {
		setOfInterval.add(new Interval(0, array.length - 1));
		while (!setOfInterval.isEmpty()) {
			Interval picked = setOfInterval.iterator().next();
			if (picked.getUpper() - picked.getLower() > 1) {
				int pivot = partition(array, picked.getLower(), picked.getUpper());
				setOfInterval.add(new Interval(picked.getLower(), pivot));
				setOfInterval.add(new Interval(pivot+1, picked.getUpper()));
			}
			else {
				bubbleSort(array);
			}
			setOfInterval.remove(picked);
		}
	}
	/**
	 * Compares adjacent array elements and exchanges their values if they are out of order. Smaller values bubble up to the top of the array and larger values sink to the bottom; hence the name
	 * @param <T>- Generic data type
	 * @param table- the array 
	 */
	public static <T extends Comparable<T>> void bubbleSort(T[] table) {
		int pass = 1;
		boolean exchanges = false;
		do {
			exchanges = false;
			for (int i = 0; i < table.length - pass; i++) {
				if (table[i].compareTo(table[i + 1]) > 0) {
					T temp = table[i];
					table[i] = table[i + 1];
					table[i + 1] = temp;
					exchanges = true;
				}
			}
			pass++;

		} while (exchanges);
	}

	public static void main(String[] args) {
		
	}
}
