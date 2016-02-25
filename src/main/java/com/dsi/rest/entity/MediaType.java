package com.dsi.rest.entity;

public enum MediaType {

	APPLICATION_JSON_TYPE("APPLICATION_JSON_TYPE"), TEXT_PLAIN_TYPE("TEXT_PLAIN_TYPE");

	private String text;

	MediaType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static MediaType fromString(String text) {
		if (text != null) {
			for (MediaType mt : MediaType.values()) {
				if (text.equalsIgnoreCase(mt.text)) {
					return mt;
				}
			}
		}
		return null;
	}

}
