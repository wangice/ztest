package com.ice.ztest.netty.protobuf.test;

import com.ice.ztest.netty.protobuf.codec.SubscribeReqProto;

public class TestSubscribeReqProto {

	/** 编码. */
	public static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}

	/** 解码. */
	public static SubscribeReqProto.SubscribeReq decode(byte[] body) throws Exception {
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}

	/** 创建编码. */
	public static SubscribeReqProto.SubscribeReq createSubscribeReq() {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("ice");
		builder.setProductName("milk");
		builder.setAddress("shenzhen");
		return builder.build();
	}
}
