package com.ms8.md.tracking.global.common;

public class CalorieCalculator {

	public static Integer calculateCalories(Integer age, Character gender, Integer distance, Integer time) {
		return (int) ((calculateBMR(age, gender) * calculateMET(distance, time) / 24) * ((double) time / 3600));
	}

	private static Integer calculateBMR(Integer age, Character gender) {
		if (gender.equals('M')) {
			if (age < 30) {
				return 1728;
			} else if (age < 50) {
				return 1667;
			} else {
				return 1494;
			}
		}
		else {
			if (age < 30) {
				return 1351;
			} else if (age < 50) {
				return 1317;
			} else {
				return 1253;
			}
		}
	}

	private static Double calculateMET(Integer distance, Integer time) {
		double speed = ((double) distance / 1000) / ((double) time / 3600);
		if (speed < 2.5) {
			return 1.9;
		} else if (speed < 6.5) {
			return 2.9;
		} else {
			return 3.5;
		}
	}

}
