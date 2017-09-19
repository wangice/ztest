package com.ice.ztest.netty.ping.server;

import com.ice.ztest.netty.ping.message.AskMsg;
import com.ice.ztest.netty.ping.message.BaseMessage;
import com.ice.ztest.netty.ping.message.LoginMsg;
import com.ice.ztest.netty.ping.message.MsgType;
import com.ice.ztest.netty.ping.message.PingMsg;
import com.ice.ztest.netty.ping.message.ReplyBody;
import com.ice.ztest.netty.ping.message.ReplyMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class ReplyServerHandler extends SimpleChannelInboundHandler<BaseMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext cxt, BaseMessage msg) throws Exception {
		if (MsgType.LOGIN.equals(msg.getMsgType())) {
			LoginMsg loginMsg = (LoginMsg) msg;
			if ("ice".equals(loginMsg.getUserName()) && "123456".equals(loginMsg.getPwd())) {
				NettyChannelMap.add(loginMsg.getClientID(), cxt);
				System.out.println("client:" + loginMsg.getClientID());
			}
		} else {
			if (NettyChannelMap.get(msg.getClientID()) == null) {
				LoginMsg loginMsg = new LoginMsg();
				cxt.writeAndFlush(loginMsg);
			}
		}
		switch (msg.getMsgType()) {
		case PING:
			PingMsg pingMsg = (PingMsg) msg;
			PingMsg replyPing = new PingMsg();
			NettyChannelMap.get(pingMsg.getClientID()).writeAndFlush(replyPing);
			break;
		case ASK:
			// 收到客户端请求
			AskMsg askMsg = (AskMsg) msg;
			System.out.println(askMsg.getAskParam().getAuthToken());
			ReplyMsg replyMsg = new ReplyMsg();
			ReplyBody body = new ReplyBody();
			body.setMsg("服务端发送");
			replyMsg.setReplyBody(body);
			NettyChannelMap.get(askMsg.getClientID()).writeAndFlush(replyMsg);
			break;
		case REPLY:
			// 收到客户端回复
			ReplyMsg reply = (ReplyMsg) msg;
			System.out.println("receive client msg:" + reply.getReplyBody().getMsg());
			break;
		default:
			break;
		}
		ReferenceCountUtil.release(msg);
	}
}
