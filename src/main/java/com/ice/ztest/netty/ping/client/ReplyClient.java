package com.ice.ztest.netty.ping.client;

import java.util.concurrent.TimeUnit;

import com.ice.ztest.netty.ping.message.AskMsg;
import com.ice.ztest.netty.ping.message.AskParam;
import com.ice.ztest.netty.ping.message.Constants;
import com.ice.ztest.netty.ping.message.LoginMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ReplyClient {
	private Channel channel;

	private int port;

	private String host;

	public ReplyClient(int port, String host) throws Exception {
		this.port = port;
		this.host = host;
		connect();
	}

	/** 连接. */
	public void connect() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)//
					.channel(NioSocketChannel.class)//
					.option(ChannelOption.TCP_NODELAY, true)//
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
							ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							ch.pipeline().addLast(new ObjectEncoder());
							ch.pipeline().addLast(new ReplyClientHandler());
						}
					});
			ChannelFuture future = b.connect(host, port).sync();
			if (future.isSuccess()) {
				channel = future.channel();
			}
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		Constants.setClientId("001");
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		ReplyClient client = new ReplyClient(port, "127.0.0.1");
		//
		if (client.channel == null) {
			return;
		}
		LoginMsg msg = new LoginMsg();
		msg.setUserName("ice");
		msg.setPwd("123456");
		client.channel.writeAndFlush(msg);
		while (true) {
			TimeUnit.SECONDS.sleep(3);
			AskMsg askMsg = new AskMsg();
			AskParam param = new AskParam();
			param.setAuthToken("authToken");
			askMsg.setAskParam(param);
			client.channel.writeAndFlush(askMsg);
		}
	}
}
