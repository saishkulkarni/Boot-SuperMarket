package org.jsp.super_market.helper;

import lombok.Data;

@Data
public class ResponseStructure<T> {
	String message;
	int statuscode;
	T data;
}
