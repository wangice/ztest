package com.ice.ztest.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class KafkaConsumerTest {
	public static void main(String[] args) {
		System.out.println("begin consumer");
		connectionKafka();
		System.out.println("finish consumer");
	}

	@SuppressWarnings("resource")
	public static void connectionKafka() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "60.205.186.24:9092");
		props.put("group.id", "test-consumer-group");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList("test"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
				System.out.println();
			}
		}
	}
}
