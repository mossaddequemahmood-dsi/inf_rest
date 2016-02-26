package com.dsi.rest.util;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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

	public static URI convertStringToURI(String uriStr) {
		URI uri = null;
		try {
			uri = new URI(uriStr);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}
}
