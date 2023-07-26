//package com.service.player.config;
//
//import com.service.player.events.TeamRequest;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.LongSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    public String brokerPort;
//
//    @Bean
//    public KafkaTemplate<Long, TeamRequest> kafkaTemplate(){
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerPort);
//        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        JsonSerializer<TeamRequest> serializer = new JsonSerializer<>();
//        serializer.configure(configs, false);
//        serializer.setAddTypeInfo(false);
//
//        DefaultKafkaProducerFactory<Long, TeamRequest> pf = new DefaultKafkaProducerFactory<>(configs, new LongSerializer(), serializer);
//        return new KafkaTemplate<>(pf);
//    }
//}
