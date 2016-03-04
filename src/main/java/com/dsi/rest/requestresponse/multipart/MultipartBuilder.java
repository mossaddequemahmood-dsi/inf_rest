package com.dsi.rest.requestresponse.multipart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpParser;
import org.apache.commons.io.IOUtils;

import com.dsi.rest.requestresponse.Request;

public class MultipartBuilder {

	private static final String FIELDNAME_REGEX = "name=\"([^\"]+)\"";
	private static final String BOUNDARY_REGEX = "boundary=([^\"]*)";
	private static final String CONTENT_TYPE_HEADER = "Content-type";

	private List<Multipart.Body> multipartBodyList = new ArrayList<>();

	public MultipartBuilder(Request request) {
		process(request);
	}

	public Multipart build() {
		return new Multipart(multipartBodyList);
	}

	private void process(Request request) {

		if (request.getRequestStream() == null) {
			return;
		}

		String boundaryStr = extractBoundary(request.getHeader(CONTENT_TYPE_HEADER));

		if (boundaryStr == null || boundaryStr.length() == 0) {
			return;
		}

		byte[] boundary = boundaryStr.getBytes();

		ByteArrayInputStream content = null;
		try {
			content = new ByteArrayInputStream(IOUtils.toByteArray(request.getRequestStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		@SuppressWarnings("deprecation")
		MultipartStream multipartStream = new MultipartStream(content, boundary);

		boolean nextPart = false;
		try {
			nextPart = multipartStream.skipPreamble();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Multipart.Body body;

		while (nextPart) {
			body = new Multipart.Body();
			String header = null;
			try {
				header = multipartStream.readHeaders();
				processHeader(header, body);
			} catch (FileUploadIOException | MalformedStreamException e) {
				e.printStackTrace();
			}

			try {
				processBody(header, multipartStream, body);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				nextPart = multipartStream.readBoundary();
			} catch (FileUploadIOException | MalformedStreamException e) {
				e.printStackTrace();
			}

			multipartBodyList.add(body);
		}

	}

	private void processHeader(String headerAsString, Multipart.Body body) {

		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(headerAsString.getBytes());
			Header[] headers = HttpParser.parseHeaders(bais, Charset.defaultCharset().displayName());
			for (Header header : headers) {
				String key = header.getName();
				String value = header.getValue();
				body.addHeader(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void processBody(String header, MultipartStream multipartStream, Multipart.Body body)
			throws MalformedStreamException, IOException {

		ByteArrayOutputStream boas = null;
		FileOutputStream fos = null;

		if (header.contains("Content-Type: " + MediaType.TEXT_PLAIN_TYPE)
				|| header.contains("Content-Type: " + MediaType.APPLICATION_JSON_TYPE)
				|| header.contains("Content-Type: " + MediaType.APPLICATION_XML_TYPE)) {
			boas = new ByteArrayOutputStream();
			multipartStream.readBodyData(boas);
			String newString = new String(boas.toByteArray());
			body.setObject(newString);
		} else if (!header.contains("Content-Type:")) {
			boas = new ByteArrayOutputStream();
			multipartStream.readBodyData(boas);
			String newString = new String(boas.toByteArray());
			body.addField(extractKey(header), newString);
		} else {
			File file = new File(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID());
			fos = new FileOutputStream(file);
			multipartStream.readBodyData(fos);
			body.setObject(file);
		}

	}

	private static String extractKey(String in) {
		Pattern pattern = Pattern.compile(FIELDNAME_REGEX);
		Matcher matcher = pattern.matcher(in);
		matcher.find();
		String key = matcher.group();
		key = key.substring("name=\"".length(), key.length() - 1);
		return key;
	}

	private static String extractBoundary(String in) {
		Pattern pattern = Pattern.compile(BOUNDARY_REGEX);
		Matcher matcher = pattern.matcher(in);
		matcher.find();
		if (!matcher.matches()) {
			return null;
		}
		String boundary = matcher.group();
		boundary = boundary.substring(9);
		return boundary;
	}
}
