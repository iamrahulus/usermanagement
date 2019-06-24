package com.aws.codestar.usersmanagement.models;

public class RegistrationRequest {

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "{userName: " + this.userName + ", password: " + this.password + ", emailId: " + this.emailId + "}";
	}

	String userName;
	String password;
	String emailId;
}
