package com.ice.ztest.netty.protobuf.server;

import com.ice.ztest.netty.protobuf.codec.SubscribeReqProto;
import com.ice.ztest.netty.protobuf.test.TestSubscribeReqProto;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
		if ("ice".equalsIgnoreCase(req.getUserName())) {
			System.out.println("service accept req: " + req.toString());
			ctx.writeAndFlush(TestSubscribeReqProto.createSubscribeReq());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
