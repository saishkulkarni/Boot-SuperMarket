package org.jsp.super_market.exception;

public class AllException extends Exception {
	String msg = "";

	public AllException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}
}
