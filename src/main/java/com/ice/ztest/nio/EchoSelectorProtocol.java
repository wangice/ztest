package com.ice.ztest.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {

	private int bufSize;

	public EchoSelectorProtocol(int bufSize) {
		this.bufSize = bufSize;
	}

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		System.out.println("Accept");
		SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
		channel.configureBlocking(false);
		channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.clear();
		int read = channel.read(buf);
		if (read == -1) {
			channel.close();
		} else if (read > 0) {
			// 如果缓冲区总读入了数据，则将该信道感兴趣的操作设置为为可读可写
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip();
		SocketChannel channel = (SocketChannel) key.channel();
		channel.write(buf);
		if (!buf.hasRemaining()) {
			// 如果缓冲区中的数据已经全部写入了信道，则将该信道感兴趣的操作设置为可读
			key.interestOps(SelectionKey.OP_READ);
		}
		buf.compact();
	}

}
