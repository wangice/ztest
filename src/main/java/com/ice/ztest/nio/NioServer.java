package com.ice.ztest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class NioServer {

	public void start(int port) {
		try {
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(port));
			Selector selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				int n = selector.select();
				if (n == 0) {
					continue;
				}
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isAcceptable()) {/* 如果关心accept. */

					}
					if (key.isReadable()) {/* 如果关心read. */

					}
					if (key.isConnectable()) {/**/

					}
					iterator.remove();/* 手动移除. */
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
