package com.ice.ztest.netty.ping.message;

public class PingMsg extends BaseMessage {

	private static final long serialVersionUID = 1L;

	public PingMsg() {
		super();
		setMsgType(MsgType.PING);
	}

}
