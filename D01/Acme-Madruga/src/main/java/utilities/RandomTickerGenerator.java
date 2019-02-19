package utilities;

import java.util.Date;

/**
 * Utility class used to generate random tickers.
 */
public class RandomTickerGenerator {

	////////////////////////////////////////////////////////////////////////////////
	// Internal fields

	/**
	 * Symbols to pick from for the random part of the ticker.
	 */
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * State of the random number generator.
	 */
	private static int GENERATOR_STATE = (int) (System.nanoTime() * 6364136223846793005L + 1442695040888963407L);

	////////////////////////////////////////////////////////////////////////////////
	// Internal methods

	/**
	 * Obtains the current date in the format YYMMDD, where YY stands for the last
	 * two digits of the current year, MM stands for the current month of the year
	 * in two digit form and DD stands for the current day of the year in two digit
	 * form.
	 */
	private static String currentDateToString() {
		Date date = new Date();
		String dateString = "";
		dateString += String.format("%02d", date.getYear() % 100);
		dateString += String.format("%02d", date.getMonth() + 1);
		dateString += String.format("%02d", date.getDate());
		return dateString;
	}

	/**
	 * Pick a random character from the alphabet string.
	 * 
	 * @see RandomTickerGenerator#ALPHABET
	 */
	private static char getRandomCharacter() {
		int result;
		int moduloBias = (Integer.MIN_VALUE - ALPHABET.length()) % ALPHABET.length();
		int unbiasedMaximum = Integer.MAX_VALUE - moduloBias;
		do {
			GENERATOR_STATE ^= GENERATOR_STATE << 13;
			GENERATOR_STATE ^= GENERATOR_STATE >>> 17;
			GENERATOR_STATE ^= GENERATOR_STATE << 5;
			result = GENERATOR_STATE & 0x7F_FF_FF_FF;
		} while (result > unbiasedMaximum);
		return ALPHABET.charAt(result % ALPHABET.length());
	}

	/**
	 * Generate a random string using 5 random characters as given by the
	 * getRandomCharacter() method.
	 * 
	 * @see RandomTickerGenerator#getRandomCharacter()
	 */
	private static String getRandomString() {
		String result = "";
		for(int i = 0; i < 5; i++) {
			result += getRandomCharacter();
		}
		return result;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Generator methods

	/**
	 * Generates a random ticker. Tickers follow the form YYMMDD-XXXXX, where YY
	 * stands for the last two digits of the current year, MM stands for the current
	 * month of the year in two digit form and DD stands for the current day of the
	 * year in two digit form and XXXXX stands for 5 random uppercase letters.
	 */
	public static String generateTicker() {
		return currentDateToString() + "-" + getRandomString();
	}

}
