package utilities;

import java.util.Date;

public class RandomTickerGenerator {

	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static int GENERATOR_STATE = (int) (System.nanoTime() * 6364136223846793005L + 1442695040888963407L);

	private static String currentDateToString() {
		Date date = new Date();
		String dateString = "";
		dateString += String.format("%02d", date.getYear() % 100);
		dateString += String.format("%02d", date.getMonth() + 1);
		dateString += String.format("%02d", date.getDate());
		return dateString;
	}

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

	private static String getRandomString() {
		String result = "";
		for(int i = 0; i < 6; i++) {
			result += getRandomCharacter();
		}
		return result;
	}

	public static String generateTicker() {
		return currentDateToString() + "-" + getRandomString();
	}

}
