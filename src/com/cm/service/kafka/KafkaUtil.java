package com.cm.service.kafka;

import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import com.cm.security.ConfigUtil;

public class KafkaUtil {
	
	private static KafkaUtil self = null;
	
	public static synchronized KafkaUtil getInstance() {
		if (self == null) {
			self = new KafkaUtil();
		}
		return self;
	}
	public static final AtomicBoolean closed = new AtomicBoolean(false);
	public static final int MSGCOUNT = 500;
	public static final UUID uid = UUID.randomUUID();
	
	public static KafkaConsumer<String, String> kc;
	private static String server;
	
	private static HashMap<String, Object> connections = new HashMap<String, Object>();
	
	public static String getKafkaAddr() {
		ConfigUtil cfg = ConfigUtil.getInstance();
        return cfg.getKafka();
    }
	
	public static KafkaProducer<String, String> getProducer(String topic) {
		
		server = getKafkaAddr();
		Properties props = new Properties();
		/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
		props.put("bootstrap.servers", server);
		props.put("acks", "0");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		/* key的序列化类 */
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		/* value的序列化类 */
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, String> producer;
		try {
			producer = (KafkaProducer<String, String>) connections.get(topic);
			if (producer != null) {
				producer.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		producer = new KafkaProducer<String, String>(props);
		connections.put(topic, producer);
		return producer;
	}
	
    public static KafkaProducer<String, Object> getProducer2(String topic) {
		
		server = getKafkaAddr();
		Properties props = new Properties();
		/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
		props.put("bootstrap.servers", server);
		props.put("acks", "0");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		/* key的序列化类 */
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		/* value的序列化类 */
		props.put("value.serializer", "util.EncodeingKafka");

		KafkaProducer<String, Object> producer;
		try {
			producer = (KafkaProducer<String, Object>) connections.get(topic);
			if (producer != null) {
				producer.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		producer = new KafkaProducer<String, Object>(props);
		connections.put(topic, producer);
		return producer;
	}
	
	public static KafkaConsumer<String, String> getConsumer() {
		if (kc == null) {
			server = getKafkaAddr();
			Properties consumer_props = new Properties();
			/* 定义kakfa 服务的地址，不需要将所有broker指定上 */
			consumer_props.put("bootstrap.servers", server);
			/* 制定consumer group */
			consumer_props.put("group.id", UUID.randomUUID().toString());
			/* 是否自动确认offset */
			consumer_props.put("enable.auto.commit", "false");
			consumer_props.put("auto.offset.reset", "latest");
			/* 自动确认offset的时间间隔 */
			consumer_props.put("auto.commit.interval.ms", "1000");
			consumer_props.put("session.timeout.ms", "30000");
			consumer_props.put("consumer.timeout.ms", "10000");
			/* key的序列化类 */
			consumer_props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			/* value的反序列化类 */
			consumer_props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

			kc = new KafkaConsumer<String, String>(consumer_props);
		}
		return kc;
	}
	
	public void shutdown() {
        closed.set(true);
        kc.wakeup();
    }
}

