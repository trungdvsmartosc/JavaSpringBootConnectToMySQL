package smartosc.fresher.connectmysql.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import smartosc.fresher.connectmysql.kafka.KafkaProducerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final KafkaProducerProperties kafkaProducerProperties;

    public KafkaProducerConfig(KafkaProducerProperties kafkaProducerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        final KafkaProducerProperties.Producer producer = kafkaProducerProperties.getProducer();
        final String keySerializer = producer.getKeySerializer();
        final String valueSerializer = producer.getValueSerializer();

        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapAddress());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
