package com.dsi.rest.mutipart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Multipart {

	private List<Body> multipartBodyList;

	public Multipart(List<Body> multipartBodyList) {
		this.multipartBodyList = multipartBodyList;
	}

	public List<Body> getMultipartBodyList() {
		return multipartBodyList;
	}

	@Override
	public String toString() {
		return "Multipart [multipartBodyList=" + multipartBodyList + "]";
	}

	static class Body {

		private boolean formField = false;
		private Object object;
		private Map<String, List<String>> headerMap = new HashMap<String, List<String>>();
		private Map<String, String> fields = new HashMap<String, String>();

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public Map<String, List<String>> getHeaderMap() {
			return headerMap;
		}

		public String getfield(String key) {
			return fields.get(key);
		}

		public void addField(String key, String value) {
			formField = true;
			fields.put(key, value);
		}

		public void addHeader(String key, String value) {
			List<String> headers = headerMap.get(key);
			if (headers == null) {
				headers = new ArrayList<String>();
			}
			headers.add(value);
			headerMap.put(key, headers);
		}

		public boolean isFormField() {
			return formField;
		}

		public void setFormField(boolean formField) {
			this.formField = formField;
		}

		@Override
		public String toString() {
			return "Body [formField=" + formField + ", object=" + object + ", headerMap=" + headerMap + ", fields="
					+ fields + "]";
		}

	}
}
