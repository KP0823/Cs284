package CoinPurse;

//Kush Parmar
//I pledge my honor that I have abided by the stevens honor system-Kush Parmar
import java.util.Random;

public class CoinPurse {
	private int numGalleons, numSickles, numKnuts;
	private static final int CAPACITY = 256;

	public CoinPurse(int g, int s, int k) {
		if (g + s + k > CAPACITY) {
			throw new IllegalArgumentException("Too many coins");
		} else if (g < 0 || s < 0 || k < 0) {
			throw new IllegalArgumentException("Can not have negative coins");
		}
		numGalleons = g;
		numSickles = s;
		numKnuts = k;
	}

	/**
	 * depositGalleons,depositSickles,depositKnuts functionally perform the same
	 * operation where we add n amount of specified coins to the coinpurse
	 * 
	 * @param n = amount of galleons,sickles,knuts in their respective functions
	 */
	public void depositGalleons(int n) {
		if (numGalleons + numSickles + numKnuts + n > CAPACITY)
			throw new IllegalArgumentException("too many Galleon coins");
		else if (n < 0)
			throw new IllegalArgumentException("Can not have negative coins");
		numGalleons += n;
	}

	public void depositSickles(int n) {
		if (numGalleons + numSickles + numKnuts + n > CAPACITY)
			throw new IllegalArgumentException("too many Sickle coins");
		else if (n < 0)
			throw new IllegalArgumentException("Can not have negative coins");
		numSickles += n;
	}

	public void depositKnuts(int n) {
		if (numGalleons + numSickles + numKnuts + n > CAPACITY)
			throw new IllegalArgumentException("too many Knut coins");
		else if (n < 0)
			throw new IllegalArgumentException("Can not have negative coins");
		numKnuts += n;
	}

	/**
	 * withdrawGalleons,withdrawSickles,withdrawKnuts functionally perform the same
	 * operation where we subtract n amount of specified coins to the coinpurse
	 * 
	 * @param n = amount of galleons,sickles,knuts in their respective functions
	 */
	public void withdrawGalleons(int n) {
		if (numGalleons - n < 0)
			throw new IllegalArgumentException("Not enough Galleons in purse");
		else if (n < 0)
			throw new IllegalArgumentException("Can not have negative coins");
		numGalleons -= n;
	}

	public void withdrawSickles(int n) {
		if (numSickles - n < 0)
			throw new IllegalArgumentException("Not enough Sickles in purse");
		else if (n < 0)
			throw new IllegalArgumentException("Can not have negative coins");
		numSickles -= n;
	}

	public void withdrawKnuts(int n) {
		if (numKnuts - n < 0)
			throw new IllegalArgumentException("Not enough Knuts in purse");
		else if (n < 0)
			throw new IllegalArgumentException("Can not have negative coins");
		numKnuts -= n;
	}

	// numCoins returns the number of coins in the coin purse
	public int numCoins() {
		return numGalleons + numSickles + numKnuts;
	}

	// totalValue returns the number of coins in Knuts
	public int totalValue() {
		return (numGalleons * 493) + (numSickles * 29) + numKnuts;
	}

	// toString returns the Printing format
	public String toString() {
		return "Galleons: " + numGalleons + " Sickles: " + numSickles + " Knuts: " + numKnuts;
	}

	/**
	 * exactChange runs a series of tests in order to find the most optimal way to
	 * provide exact change if it is possible with coins we have
	 * 
	 * @param n is the amount of coins we need exact change for
	 * @return returns true if the available coins can provide the exact value, else
	 *         it is false
	 */
	public boolean exactChange(int n) {
		int gToK = 493;
		int sToK = 29;
		int kToK = 1;
		int total = n;
		if (((total / gToK) >= 1) && numGalleons >= (total / gToK))
			total -= (total / gToK) * gToK;
		if (((total / sToK) >= 1) && numSickles >= (total / sToK))
			total -= (total / sToK) * sToK;
		if (((total / kToK) >= 1) && numKnuts >= (total / kToK))
			total -= (total / kToK) * kToK;
		if (total == 0)
			return true;
		return false;
	}

	/**
	 * withdraw runs a series of tests in order to find the most optimal way to
	 * provide exact change if it is possible with coins we have
	 * 
	 * @param n is the amount of coins we need exact change for
	 * @return returns true if the available coins can provide the exact value, else
	 *         it is false
	 */
	public int[] withdraw(int n) {
		int[] coins = { 0, 0, 0 };
		int gToK = 493;
		int sToK = 29;
		int kToK = 1;
		int total = n;
		int k = totalValue();
		if (k < n)
			throw new IllegalArgumentException("Not enough coins in purse");
		if (exactChange(n) == true) {
			coins[0] = (total / gToK);
			total -= (total / gToK) * gToK;
			coins[1] = (total / sToK);
			total -= (total / sToK) * sToK;
			coins[2] = (total / kToK);
			total -= (total / kToK) * kToK;
		} else if (exactChange(n) == false) {
			if (((total / gToK) >= 1) && numGalleons >= (total / gToK)) {
				coins[0] = (total / gToK);
				total -= (total / gToK) * gToK;
			}
			if (((total / sToK) >= 1) && numSickles >= (total / sToK)) {
				coins[1] = (total / sToK);
				total -= (total / sToK) * sToK;
			}
			if (((total / kToK) >= 1) && numKnuts >= (total / kToK)) {
				coins[2] = (total / kToK);
				total -= (total / kToK) * kToK;
			}
			if (total != 0 && numSickles>0)
				coins[1] += 1;

		}
		return coins;
	}

	/**
	 * drawRandCoin() takes a decimal of the amount of knuts,sickles,and galleons
	 * then by using Math.random() a random decimal between 0-1 is chosen
	 * 
	 * @return 0 if knuts, 1 if sickles, 2 if galleons
	 */
	public int drawRandCoin() {
		int total = numCoins();
		double chanceKnut = (double) numKnuts / total;
		double chanceSickle = (double) numSickles / total;
		double chanceGalleon = (double) numGalleons / total;
		Random rand = new Random();
		double d = rand.nextDouble();	
		if (numKnuts > 0 && d <= chanceKnut) 
			return 0;
		if (numSickles > 0 && chanceKnut < d && d <= chanceKnut + chanceSickle) 
			return 1;
		if (numGalleons > 0 && chanceKnut + chanceSickle < d && d <= chanceKnut + chanceSickle + chanceGalleon) 
			return 2;
		else
			throw new IllegalArgumentException("There are no coins");
	}

	/**
	 * utilizes drawRandCoin() to find a random sequence of coins. Does not remove
	 * any coins
	 * 
	 * @param n how long the sequence should be
	 * @return the sequence
	 */
	public int[] drawRandSequence(int n) {
		if (numCoins() <= 0)
			throw new IllegalArgumentException("There are no coins");
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = drawRandCoin();
		}
		return array;
	}

	/**
	 * compareSequences() compares both sequence if they are the same size -1 if 1
	 * round sequence 1 wins, +1 if coin sequence 2 wins and 0 if it is a tie
	 * 
	 * @param coinSeq1 first coin sequence
	 * @param coinSeq2 second coin sequence
	 * @return total
	 */
	public static int compareSequences(int[] coinSeq1, int[] coinSeq2) {
		if (coinSeq1.length != coinSeq2.length)
			throw new IllegalArgumentException("Coin sequence are the not the same length");
		int total = 0;
		for (int i = 0; i < coinSeq1.length; i++) {
			if (coinSeq1[i] > coinSeq2[i])
				total += 1;
			if (coinSeq1[i] < coinSeq2[i])
				total += -1;
		}
		if (total == 0)
			return 0;
		else if (total < 0)
			return -1;
		else
			return 1;
	}

	/**
	 * drawRandSequenceNoRepl() utilizes drawRandCoin() to find a random sequence of
	 * coins. It removes any selected coins.
	 * 
	 * @param n how long the sequence should be
	 * @return the sequence
	 */
	public int[] drawRandSequenceNoRepl(int n) {
		if (numCoins() < n)
			throw new IllegalArgumentException("Insufficent amount of coins");
		int[] array = new int[n];
		int coin = 0;
		for (int i = 0; i < n; i++) {
			coin = drawRandCoin();
			array[i] = coin;
			if (coin == 0)
				numKnuts -= 1;
			else if (coin == 1)
				numSickles -= 1;
			else if (coin == 2)
				numGalleons -= 1;
		}
		return array;
	}

	public static void main(String[] args) {
		CoinPurse k = new CoinPurse(1,5,10);
		int []b =k.withdraw(558);
		for(int i =0; i<b.length;i++)
			System.out.println(b[i]);
	}

}

