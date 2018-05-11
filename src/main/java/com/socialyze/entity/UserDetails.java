package com.socialyze.entity;

import java.util.UUID;

public class UserDetails {
	
	public String password;
	public String accessToken;
	
	public UserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDetails(String password, String accessToken) {
		super();
		this.password = password;
		this.accessToken = null;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}
	
}
