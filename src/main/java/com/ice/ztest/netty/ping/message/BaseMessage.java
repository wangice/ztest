package com.ice.ztest.netty.ping.message;

import java.io.Serializable;

/***
 * 
 * 
 * 创建时间： 2017年8月27日 下午10:01:34
 * 
 * @author ice
 *
 */
public abstract class BaseMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private MsgType msgType;

	private String clientID;

	// 初始化客户端id
	public BaseMessage() {
		this.clientID = Constants.getClientId();
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

}
