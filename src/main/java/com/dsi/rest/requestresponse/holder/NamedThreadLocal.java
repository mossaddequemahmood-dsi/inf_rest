package com.dsi.rest.requestresponse.holder;

import com.dsi.rest.util.AssertUtil;

public class NamedThreadLocal<T> extends ThreadLocal<T> {

	private final String name;

	public NamedThreadLocal(String name) {
		AssertUtil.hasText(name, "Name must not be empty");
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
