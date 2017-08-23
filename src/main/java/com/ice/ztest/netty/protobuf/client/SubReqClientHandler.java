package com.ice.ztest.netty.protobuf.client;

import com.ice.ztest.netty.protobuf.codec.SubscribeReqProto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 2; i++) {
			ctx.write(this.subReq(i));
		}
		ctx.flush();
	}

	private SubscribeReqProto.SubscribeReq subReq(int i) {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(i);
		builder.setUserName("ice");
		builder.setProductName("bing");
		builder.setAddress("shenzhen");
		return builder.build();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Receive server response:(" + msg + ")");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
