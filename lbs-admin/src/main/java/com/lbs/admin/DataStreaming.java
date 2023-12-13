package com.lbs.admin;

import com.lbs.shared.pojo.dto.ImuDto;
import com.lbs.shared.pojo.dto.IosLocationDto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Eric Xue
 * @since 2023/10/06
 * @describe
 * DataStreaming脚本，它用于模拟发送位置数据到Kafka的场景
 */

public class DataStreaming {
    public static void main(String[] args) throws InterruptedException {
        // Kafka服务器地址
        String bootstrapServers = "127.0.0.1:9092";

        // 创建并设置Kafka生产者的属性
        Properties properties = new Properties();
        // 设置连接到Kafka集群的地址
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 设置键的序列化器为字符串序列化器
        properties.setProperty(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置值的序列化器为Json序列化器
        properties.setProperty(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        // 创建Kafka生产者
        KafkaProducer<String, IosLocationDto> producer = new KafkaProducer<>(properties);

        // 生成一个随机的用户ID
        String userId = UUID.randomUUID().toString();
        // 初始经纬度，仅为测试所任意提供的一个合理数据
        double longitude = 116.407;
        double latitude = 39.9042;
        while (true) {
            IosLocationDto locationDto = new IosLocationDto();
            locationDto.setUserId(userId);
            locationDto.setLongitude(longitude);
            locationDto.setLatitude(latitude);
            locationDto.setSsid("WIFI");
            locationDto.setRssi(70.0);

            ImuDto imuDto = new ImuDto();
            imuDto.setX(1.0);
            imuDto.setY(1.0);
            imuDto.setZ(1.0);
            locationDto.setImu(imuDto);
            locationDto.setTimestamp(new Date());

            // 创建Kafka的生产者记录
            // 第一个参数是Kafka主题的名称，消息将被发送到名为"ios-location"的Kafka主题。
            // 第二个参数是消息的值，即locationDto。这个对象包含了要发送的实际数据。
            ProducerRecord<String, IosLocationDto> producerRecord =
                    new ProducerRecord<>("ios-location", locationDto);
            // 发送记录到Kafka
            producer.send(producerRecord);

            // 更新经纬度，每次各自增加0.0001
            longitude += 0.0001;
            latitude += 0.0001;

            Thread.sleep(1000);
        }
//
//        // 刷新生产者，并关闭
//        producer.flush();
//        producer.close();
    }
}
