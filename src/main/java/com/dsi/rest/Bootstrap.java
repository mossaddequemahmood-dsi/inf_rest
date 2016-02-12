package com.dsi.rest;

public interface Bootstrap<T> {
	void init(T bootstrapedObj);
}
