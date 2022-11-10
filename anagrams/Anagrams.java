package anagrams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Anagrams {
	final Integer[] primes =  
			{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67, 
			71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;

	/**
	 * buildLetterTable generates the letter table using a list of characters and the list of primes. 
	 */
	public void buildLetterTable() {
			String letters="abcdefghijklmnopqrstuvwxyz";
			char[] list= letters.toCharArray();
			int i =0;
			letterTable= new HashMap<>();
			for(char a: list) {
				letterTable.put(a, primes[i]);
				i++;
			}	
		}

	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}
	/**
	 * addsWords: adds words to the anagram table based on its HashCode. Appends words
	 * to lists if the word is an anagram to another word. Else it adds its hashcode to and the word
	 * as new map 
	 * @param s- the word
	 */
	public void addWord(String s) {
		long temp = myHashCode(s);
		if(anagramTable.containsKey(temp) && anagramTable.get(temp).contains(s)) {
			throw new IllegalArgumentException ("addWord: duplicate value");
		}
	
		if(anagramTable.containsKey(temp)) {
			anagramTable.get(temp).add(s);
		}
		else {
			ArrayList<String> tempArrayList= new ArrayList<String>();
			tempArrayList.add(s);
			anagramTable.put(temp, tempArrayList);
		}
	}
	
	/**
	 * Calculates the the hashCode based using letterTable based on its key and values found in letter table
	 * It iterates through s, to find the hashCode
	 * @param s- the word
	 * @return- the hashCode
	 */
	public long myHashCode(String s) {
		if(s.length()==0) {
			throw new IllegalArgumentException("myHashCode: String can not be Empty");
		}
		long temp=1;
		char[] list= s.toCharArray();
		for(int i =0; i< list.length;i++ ) {
			temp = temp * letterTable.get(list[i]); // gets the value for this letter
		}
		return temp;
	}
	
	/**
	 * Process the txt file while appending the words to anagramTable using addWords.
	 * @param s - the word
	 * @throws IOException 
	 */
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	/**
	 * Compares each key's value while trying to find the largest sized ArrayList of String
	 * The ArrayList of strings is the list of words that are anagrams.
	 * @return an ArrayList with the hashcode and ArrayList of the words with the most amount of 
	 * anagrams
	 */
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
	   ArrayList<Map.Entry<Long, ArrayList<String>>> temp = new ArrayList<Map.Entry<Long,ArrayList<String>>>();
	   int maxSize=0;
	   for (Map.Entry<Long, ArrayList<String>>checker: anagramTable.entrySet() ) {

		   if(maxSize <checker.getValue().size()) {
			   maxSize=checker.getValue().size();
			   temp.clear();
			   temp.add(checker);
		   }
		   else if(maxSize==checker.getValue().size()) {
			   temp.add(checker);
		   }
	   }
	   return temp;
	}

	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: "+ maxEntries);
	}
}
