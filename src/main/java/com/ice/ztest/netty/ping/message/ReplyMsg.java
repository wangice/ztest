package com.ice.ztest.netty.ping.message;

/**
 * 
 * 创建时间： 2017年8月27日 下午10:45:35
 * 
 * @author ice
 *
 */
public class ReplyMsg extends BaseMessage {
	private static final long serialVersionUID = 1L;

	private ReplyBody replyBody;

	public ReplyMsg() {
		super();
		setMsgType(MsgType.REPLY);
	}

	public ReplyBody getReplyBody() {
		return replyBody;
	}

	public void setReplyBody(ReplyBody replyBody) {
		this.replyBody = replyBody;
	}

}
