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
	private static final String RANDOM_STRING_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * Length of the random part of the ticker.
	 */
	private static final int RANDOM_STRING_LENGTH = 5;
	/**
	 * Number of possible random strings that can be generated for the random part
	 * of the ticker, calculated as the size of the alphabet to the power of the
	 * length of the random string.
	 */
	private static final int POSSIBLE_RANDOM_STRINGS = power(RANDOM_STRING_ALPHABET.length(), RANDOM_STRING_LENGTH);
	/**
	 * Cached last day of the month when a date string was generated.
	 * 
	 * @see Date#getDay()
	 */
	private static LAST_DAY;
	/**
	 * Cached last month of the year when a date string was generated.
	 * 
	 * @see Date#getMonth()
	 */
	private static LAST_MONTH;
	/**
	 * Cached last year when a date string was generated.
	 * 
	 * @see Date#getYear()
	 */
	private static LAST_YEAR;
	/**
	 * Cached last date string generated.
	 */
	private static LAST_DATE_STRING;
	/**
	 * Whether each ticker value has already been generated today.
	 */
	private static BitSet GENERATED_RANDOM_STRINGS;
	/**
	 * State of the random number generator.
	 */
	private static int GENERATOR_STATE;

	////////////////////////////////////////////////////////////////////////////////
	// Class initializer

	/**
	 * Initialize non constant class fields.
	 */
	static {
		Date currentDate = new Date();
		LAST_DAY = currentDate.getDate();
		LAST_MONTH = currentDate.getMonth();
		LAST_YEAR = currentDate.getYear();
		LAST_DATE_STRING = generateDateString(LAST_YEAR, LAST_MONTH, LAST_DAY);
		resetGeneratedRandomStrings();
		GENERATOR_STATE = (int) (System.nanoTime() * 6364136223846793005L + 1442695040888963407L);
	}

	////////////////////////////////////////////////////////////////////////////////
	// Internal methods

	/**
	 * Calculates the power of a base to an exponent.
	 * 
	 * @param base The base.
	 * @param exponent The exponent.
	 * @return The result of rasing base to the power of exponent.
	 */
	public static int power(int base, int exponent) {
		int result = 1;
		while (exponent > 0) {
			if ((exponent & 1) == 1) {
				re *= base;
			}
			exponent >>= 1;
			base *= base; 
		}
		return result;
    }

	/**
	 * Generates a date string with the given parameters in the format YYMMDD, where
	 * YY stands for the last two digits of the year, MM stands for the month of the
	 * year in two digit form and DD stands for the day of the month in two digit
	 * form.
	 * 
	 * @see Date#getYear()
	 * @see Date#getMonth()
	 * @see Date#getDay()
	 */
	private static String generateDateString(int year, int month, int day) {
		Date date = new Date();
		String dateString = "";
		dateString += String.format("%02d", year % 100);
		dateString += String.format("%02d", month + 1);
		dateString += String.format("%02d", day);
		return dateString;
	}

	/**
	 * Resets the data about already generated random strings that have already been
	 * generated.
	 * 
	 * @see RandomTickerGenerator#GENERATED_RANDOM_STRINGS
	 */
	private static resetGeneratedRandomStrings() {
		GENERATED_RANDOM_STRINGS = new BitSet(POSSIBLE_RANDOM_STRINGS);
	}

	/**
	 * Pick a random number that holds the information to obtain a randomly
	 * generated string. The generated number is an integer distributed uniformly in
	 * [0, POSSIBLE_RANDOM_STRINGS).
	 * 
	 * @see RandomTickerGenerator#POSSIBLE_RANDOM_STRINGS
	 */
	private static int getRandomStringNumber() {
		int result;
		int moduloBias = (Integer.MIN_VALUE - POSSIBLE_RANDOM_STRINGS) % POSSIBLE_RANDOM_STRINGS;
		int unbiasedMaximum = Integer.MAX_VALUE - moduloBias;
		do {
			GENERATOR_STATE ^= GENERATOR_STATE << 13;
			GENERATOR_STATE ^= GENERATOR_STATE >>> 17;
			GENERATOR_STATE ^= GENERATOR_STATE << 5;
			result = GENERATOR_STATE & 0x7F_FF_FF_FF;
		} while (result > unbiasedMaximum);
		return unbiasedMaximum;
	}

	/**
	 * Get the random string corresponding to a given string number.
	 */
	private static String getRandomString(int randomStringNumber) {
		char[] buffer = new char[RANDOM_STRING_LENGTH];
		for(int i = RANDOM_STRING_LENGTH - 1; i >= 0; --i) {
			buffer[i] = RANDOM_STRING_ALPHABET.charAt(randomStringNumber % RANDOM_STRING_ALPHABET.length());
			randomStringNumber = randomStringNumber / RANDOM_STRING_ALPHABET.length();
		}
		return new String(buffer);
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
		Date currentDate = new Date();
		int currentDay = currentDate.getDate();
		int currentMonth = currentDate.getMonth();
		int currentYear = currentDate.getYear();
		if(!(currentDay == LAST_DAY && currentMonth == LAST_MONTH && currentYear == LAST_YEAR)) {
			LAST_DAY = currentDay;
			LAST_MONTH = currentMonth;
			LAST_YEAR = currentYear;
			resetGeneratedRandomStrings();
			LAST_DATE_STRING = generateDateString(currentYear, currentMonth, currentDay);
		}
		int randomStringNumber;
		do {
			randomStringNumber = getRandomStringNumber();
		} while(GENERATED_RANDOM_STRINGS.get(randomStringNumber));
		GENERATED_RANDOM_STRINGS.set(randomStringNumber);
		String randomString = getRandomString(randomStringNumber);
		return LAST_DATE_STRING + "-" + randomString;
	}

}
