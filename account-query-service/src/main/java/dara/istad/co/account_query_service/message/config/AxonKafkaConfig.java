package dara.istad.co.account_query_service.message.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.extensions.kafka.eventhandling.consumer.ConsumerFactory;
import org.axonframework.extensions.kafka.eventhandling.consumer.Fetcher;
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@Slf4j
public class AxonKafkaConfig {

    public static final String PROCESSOR_NAME = "account-group";

    @Bean
    public SubscribableKafkaMessageSource<String, byte[]> accountQueryKafkaMessageSource(
            Serializer serializer,
            ConsumerFactory<String, byte[]> consumerFactory,
            Fetcher<String, byte[], EventMessage<?>> fetcher,
            KafkaMessageConverter<String, byte[]> kafkaMessageConverter,
            @Value("${axon.kafka.default-topic:Axon.AccountCQRS.Events}") String defaultTopic
    ) {
        log.info("Creating subscribing Kafka message source for processor: {}", PROCESSOR_NAME);
        return SubscribableKafkaMessageSource.<String, byte[]>builder()
                .groupId(PROCESSOR_NAME)
                .topics(Collections.singletonList(defaultTopic))
                .serializer(serializer)
                .consumerFactory(consumerFactory)
                .fetcher(fetcher)
                .messageConverter(kafkaMessageConverter)
                .consumerCount(1)
                .autoStart()
                .build();
    }
}
