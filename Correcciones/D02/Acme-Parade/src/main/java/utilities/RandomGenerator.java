/*
 * RandomGenerator.java
 * 
 * Copyright (C) 2019 Group 16 Desing & Testing II
 */

package utilities;

// Java program generate a random AlphaNumeric String
// using Math.random() method
public class RandomGenerator {

	// function to generate a random string of length n 
	public static String getAlphaNumericString(final int n) {

		// chose a Character random from this String 
		final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString 
		final StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between 
			// 0 to AlphaNumericString variable length 
			final int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb 
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}
}
