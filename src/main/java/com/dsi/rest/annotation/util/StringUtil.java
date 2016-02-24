package com.dsi.rest.annotation.util;

import java.io.InputStream;
import java.util.Scanner;

public class StringUtil {

	private StringUtil() {
		throw new IllegalStateException("Util class can not be instantiated.");
	}

	public static String convertStreamToString(InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
