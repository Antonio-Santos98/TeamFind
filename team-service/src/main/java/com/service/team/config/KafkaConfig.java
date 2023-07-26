//package com.service.team.config;
//
//import com.service.team.events.TeamRequest;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.LongDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//    @Bean
//    public ConsumerFactory<Long, TeamRequest> consumerFactory() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:29092");
//        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
//        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "requestId");
//        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        JsonDeserializer<TeamRequest> deserializer = new JsonDeserializer<>(TeamRequest.class, false);
//        deserializer.addTrustedPackages("*");
//
//        return new DefaultKafkaConsumerFactory<>(configs, new LongDeserializer(), deserializer);
//    }
//
//    @Bean
//    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long,TeamRequest>> factory(){
//        ConcurrentKafkaListenerContainerFactory<Long, TeamRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}
