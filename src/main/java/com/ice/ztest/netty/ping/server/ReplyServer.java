package com.ice.ztest.netty.ping.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ReplyServer {

	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)//
					.channel(NioServerSocketChannel.class)//
					.option(ChannelOption.SO_BACKLOG, 100)//
					.handler(new LoggingHandler(LogLevel.INFO))//
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							ch.pipeline().addLast(new ObjectEncoder());
							ch.pipeline().addLast(new ReplyServerHandler());
						}
					});
			ChannelFuture future = bootstrap.bind(port).sync();/* 绑定端口. */
			future.channel().closeFuture().sync();/* 等待服务器关闭端口. */
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;

		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {

			}
		}
		new ReplyServer().bind(port);
	}
}
