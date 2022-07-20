package com.testng.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
	public static Random random;
	public static<T> T getRandomFromList(List<T> list){
		random = new Random();
		int value = random.nextInt(list.size());
		return list.get(value);
	}

	public static String getDummyEmail() {

		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		random = new Random();

		while (salt.length() < 10) { // length of the random string
			int index = (int) (random.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String saltStr = salt.toString();
		return saltStr + "@gmail.com";
	}

	public static String getRandomValue() {
		random = new Random();
		return String.format("%04d", random.nextInt(10000));
	}

	public static String getRandomPANChar() {
		return randomAlphabetic(1).toUpperCase() + randomAlphabetic(1).toUpperCase() + randomAlphabetic(1).toUpperCase()
				+ randomAlphabetic(1).toUpperCase();
	}

	public static String getRandomChar() {
		return randomAlphabetic(1).toUpperCase();
	}

	public static String getRandomPANNumber() {
		return "ICCPC" + RandomUtils.getRandomValue() + RandomUtils.getRandomChar();
	}

}
