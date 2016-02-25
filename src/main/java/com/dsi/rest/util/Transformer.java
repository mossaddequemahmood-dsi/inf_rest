package com.dsi.rest.util;

public interface Transformer<S1, S2> {

	S2 transform(S1 sourceObj);
}
