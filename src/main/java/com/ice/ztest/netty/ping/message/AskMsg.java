package com.ice.ztest.netty.ping.message;

public class AskMsg extends BaseMessage {

	private static final long serialVersionUID = 1L;

	private AskParam askParam;

	public AskMsg() {
		super();
		setMsgType(MsgType.ASK);
	}

	public AskParam getAskParam() {
		return askParam;
	}

	public void setAskParam(AskParam askParam) {
		this.askParam = askParam;
	}

}
