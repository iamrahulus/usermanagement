package com.aws.codestar.usersmanagement.models;

public class UserOperationResponse {

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	@Override
	public String toString() {
		return "{" + "Code: " + this.getResponseCode() + ", Message: " + this.getResponseText() + "}";
	}

	String responseText;
	int responseCode;
}
