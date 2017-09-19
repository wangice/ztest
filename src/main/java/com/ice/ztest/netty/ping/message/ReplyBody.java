package com.ice.ztest.netty.ping.message;

import java.io.Serializable;

public class ReplyBody implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
