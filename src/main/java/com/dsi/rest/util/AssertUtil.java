package com.dsi.rest.util;

public class AssertUtil {

	private AssertUtil() {
		throw new IllegalStateException("Util class can not be instantiated.");
	}

	public static void hasText(String text, String message) {
		if (text == null || text.trim().length() == 0) {
			throw new IllegalArgumentException(message);
		}
	}
}
