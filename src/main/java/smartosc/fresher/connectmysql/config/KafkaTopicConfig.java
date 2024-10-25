package smartosc.fresher.connectmysql.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import smartosc.fresher.connectmysql.kafka.KafkaProducerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    private final KafkaProducerProperties kafkaProducerProperties;

    public KafkaTopicConfig(KafkaProducerProperties kafkaProducerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapAddress());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicTransaction() {
        return new NewTopic("transactions", 1, (short) 1);
    }
}
