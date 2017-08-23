package com.ice.ztest.netty.protobuf.server;

import com.ice.ztest.netty.protobuf.codec.SubscribeReqProto;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {

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
							ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
							ch.pipeline()
									.addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
							ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
							ch.pipeline().addLast(new ProtobufEncoder());
							ch.pipeline().addLast(new SubReqServerHandler());
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
		new SubReqServer().bind(port);
	}
}
