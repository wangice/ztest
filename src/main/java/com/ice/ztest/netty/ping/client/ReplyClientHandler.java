package com.ice.ztest.netty.ping.client;

import com.ice.ztest.netty.ping.message.BaseMessage;
import com.ice.ztest.netty.ping.message.LoginMsg;
import com.ice.ztest.netty.ping.message.MsgType;
import com.ice.ztest.netty.ping.message.PingMsg;
import com.ice.ztest.netty.ping.message.ReplyBody;
import com.ice.ztest.netty.ping.message.ReplyMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * 创建时间： 2017年8月27日 下午10:45:47
 * 
 * @author ice
 *
 */
public class ReplyClientHandler extends SimpleChannelInboundHandler<BaseMessage> {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			switch (e.state()) {
			case WRITER_IDLE:
				PingMsg pingMsg = new PingMsg();
				ctx.writeAndFlush(pingMsg);
				System.out.println("send ping to server------------");
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) throws Exception {
		MsgType msgType = msg.getMsgType();
		switch (msgType) {
		case LOGIN: // 向服务器发送登录请求
			LoginMsg loginMsg = new LoginMsg();
			loginMsg.setUserName("ice");
			loginMsg.setPwd("123456");
			ctx.writeAndFlush(loginMsg);
			break;
		case PING:
			System.out.println("receive ping from server--------");
			break;
		case ASK:
			ReplyBody body = new ReplyBody();
			body.setMsg("客户端发送请求");
			ReplyMsg askMsg = new ReplyMsg();
			askMsg.setReplyBody(body);
			ctx.writeAndFlush(askMsg);
			break;
		case REPLY:
			ReplyMsg replyMsg = (ReplyMsg) msg;
			ReplyBody replyBody = replyMsg.getReplyBody();
			System.out.println("receive client msg:" + replyBody.getMsg());
			break;
		default:
			break;
		}
		ReferenceCountUtil.release(msg);
	}

}
