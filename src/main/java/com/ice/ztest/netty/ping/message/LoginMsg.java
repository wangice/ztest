package com.ice.ztest.netty.ping.message;

public class LoginMsg extends BaseMessage {
	private static final long serialVersionUID = 1L;
	/** 用户名. */
	private String userName;
	/** 密码. */
	private String pwd;

	public LoginMsg() {
		super();
		setMsgType(MsgType.LOGIN);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
