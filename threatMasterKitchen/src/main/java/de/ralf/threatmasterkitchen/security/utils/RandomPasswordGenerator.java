package de.ralf.threatmasterkitchen.security.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPasswordGenerator {
	
	private final SecureRandom randomNumber;
	private final List<Character> crunchifyValueObj;
	
	public RandomPasswordGenerator() {
		this.randomNumber = new SecureRandom();
		crunchifyValueObj = new ArrayList<>();
        // Adding ASCII Decimal value between 33 and 53
        for (int i = 33; i < 53; i++) {
            crunchifyValueObj.add((char) i);
        }
        // Adding ASCII Decimal value between 54 and 85
        for (int i = 54; i < 85; i++) {
            crunchifyValueObj.add((char) i);
        }
        // Adding ASCII Decimal value between 86 and 128
        for (int i = 86; i < 127; i++) {
            crunchifyValueObj.add((char) i);
        }
        Collections.rotate(crunchifyValueObj, 5);
	}
	
	public String createRandomPassword() {
		StringBuilder sb = new StringBuilder();
		for (int length = 0; length < 21; length++) {
			sb.append(getRandomChar());
		}		
		return sb.toString();
	}
	
	private char getRandomChar() {
		return this.crunchifyValueObj.get(randomNumber.nextInt(this.crunchifyValueObj.size()));
	}

}
