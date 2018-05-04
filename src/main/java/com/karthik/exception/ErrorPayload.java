/**
 * 
 */
package com.karthik.exception;

import java.io.Serializable;

/**
 * @author H158574
 *
 */
public class ErrorPayload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8844096022377963012L;

	private Integer httpStatusCode;
	private String errorMessage;

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
