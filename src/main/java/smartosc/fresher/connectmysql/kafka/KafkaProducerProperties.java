package smartosc.fresher.connectmysql.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProducerProperties {

    private String bootstrapAddress;

    private Producer producer = new Producer();

    @Getter
    @Setter
    public static class Producer {
        private String keySerializer;
        private String valueSerializer;
    }
}
