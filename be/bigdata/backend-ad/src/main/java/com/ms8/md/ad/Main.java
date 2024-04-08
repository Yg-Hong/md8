package com.ms8.md.ad;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		SparkConf conf = new SparkConf().setMaster("yarn").setAppName("KafkaSparkStreamExample");
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));

		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "j10a208.p.ssafy.io:9092");
		kafkaParams.put("key.serializer", StringSerializer.class);
		kafkaParams.put("value.serializer", StringSerializer.class);
		kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		kafkaParams.put("group.id", "anomaly-detection");
		kafkaParams.put("auto.offset.reset", "latest");
		kafkaParams.put("enable.auto.commit", false);

		Collection<String> topics = Arrays.asList("tracking");

		JavaInputDStream<ConsumerRecord<String, String>> stream =
			KafkaUtils.createDirectStream(
				jssc,
				LocationStrategies.PreferConsistent(),
				ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
			);

		stream.foreachRDD(rdd -> {
			rdd.foreachPartition(partitionOfRecords -> {
				Properties props = new Properties();
				props.put("bootstrap.servers", "j10a208.p.ssafy.io:9092");
				props.put("key.serializer", StringSerializer.class.getName());
				props.put("value.serializer", StringSerializer.class.getName());
				KafkaProducer<String, String> producer = new KafkaProducer<>(props);

				String url = "jdbc:postgresql://j10a208.p.ssafy.io:5432/ms8";
				String user = "ms8";
				String password = "ms8!@";

				while (partitionOfRecords.hasNext()) {
					ConsumerRecord<String, String> record = partitionOfRecords.next();

					String newValue = record.value().substring(1, record.value().length() - 1);
					String[] values = newValue.split(",");
					System.out.println("values = " + values);
					double distance = Double.parseDouble(values[3]);
					double time = Double.parseDouble(values[4]);

					if (distance / time <= 166) {
						ProducerRecord<String, String> newRecord = new ProducerRecord<>("tracking-reco", record.key(), newValue);
						try {
							producer.send(newRecord).get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}else{
						try (Connection conn = DriverManager.getConnection(url, user, password)) {
							String sql = "UPDATE md.tracking SET is_valid_to_recommend = false WHERE tracking_id = ?";
							try (PreparedStatement stmt = conn.prepareStatement(sql)) {
								stmt.setInt(1, Integer.parseInt(values[0]));
								stmt.executeUpdate();
								System.out.println("SQL 실행후");
							}finally {
								conn.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				producer.close();
			});
		});

		jssc.start();
		jssc.awaitTermination();
	}
}
