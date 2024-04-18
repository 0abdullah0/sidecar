package com.ropulva.sidecars.utils;

import java.util.Random;

public class PINRandomGenerator {
	Random rand = new Random();

	public String generatePINCode() {

		int randPIN = new Random().nextInt(999999) + 100000;

		return Integer.toString(randPIN);
	}

}
