package com.herokuapp.newsfeedapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to serilalize the error response provided by the api
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	@JsonProperty("status")
	private String status;

	@JsonProperty("code")
	private String code;

	@JsonProperty("message")
	private String message;

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status=" + status + ", code=" + code + ", message=" + message + "]";
	}
}
