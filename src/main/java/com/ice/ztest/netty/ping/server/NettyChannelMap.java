package com.ice.ztest.netty.ping.server;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class NettyChannelMap {
	private static Map<String, ChannelHandlerContext> map = new ConcurrentHashMap<>();

	public static void add(String clientId, ChannelHandlerContext channel) {
		map.put(clientId, channel);
	}

	public static ChannelHandlerContext get(String clientId) {
		return map.get(clientId);
	}

	public static void remove(SocketChannel channel) {
		for (Map.Entry<String, ChannelHandlerContext> entry : map.entrySet()) {
			if (entry.getValue() == channel) {
				map.remove(entry.getKey());
			}
		}
	}
}
