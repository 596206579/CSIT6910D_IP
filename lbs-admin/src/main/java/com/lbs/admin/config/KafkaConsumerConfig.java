package com.lbs.admin.config;
import com.lbs.shared.pojo.dto.IosLocationDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka 消费者配置
 *
 * @author Eric Xue
 * @date 2021/02/08
 * @Describe:
 * 这个类用于配置Kafka消费者，以便它能够连接到Kafka服务器，接收并正确处理消息。
 * 它定义了消费者如何解析消息（这里使用JSON反序列化），以及如何将消息转换为特定的数据类型（IosLocationDto）。
 */
@Configuration
@AllArgsConstructor
@EnableKafka
public class KafkaConsumerConfig {
    // 注入Kafka配置属性
    private final KafkaProperties kafkaProperties;

    // 定义一个Spring Bean，用于创建消费者工厂
    // 消费者工厂的作用是创建Kafka消费者实例，它封装了消费者的所有配置
    @Bean
    public ConsumerFactory<String, IosLocationDto> locationConsumerFactory() {
        // 创建用于配置Kafka消费者的配置映射
        Map<String, Object> configs = new HashMap<>(4);
        // 设置Kafka集群的地址
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        // 设置消费者组ID
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        // 设置如果没有初始offset或offset无效时的起始位置，'earliest'表示从最早的记录开始
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 创建Json反序列化器，用于将Kafka接收的消息转换为IosLocationDto对象
        JsonDeserializer<IosLocationDto> jsonDeserializer = new JsonDeserializer<>(IosLocationDto.class);
        // 设置反序列化器的配置
        jsonDeserializer.setRemoveTypeHeaders(false);
        jsonDeserializer.addTrustedPackages("*"); // 信任所有包，用于类的反序列化
        jsonDeserializer.setUseTypeMapperForKey(true);

        // 创建并返回Kafka消费者工厂，使用StringDeserializer来解析Kafka消息的Key，使用jsonDeserializer来解析Value
        return new DefaultKafkaConsumerFactory<>(
                configs, new StringDeserializer(), jsonDeserializer);
    }

    // 定义一个Spring Bean，用于创建Kafka监听容器工厂
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IosLocationDto> iosLocationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, IosLocationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(locationConsumerFactory());
        return factory;
    }
}