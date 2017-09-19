package com.ice.ztest.netty.ping.message;

import java.io.Serializable;

public class AskParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private String authToken;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
