package dara.istad.co.account_query_service.message.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.extensions.kafka.eventhandling.consumer.subscribable.SubscribableKafkaMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AccountQueryEventProcessorConfig {

    @Autowired
    public void configureKafkaEventProcessor(EventProcessingConfigurer eventProcessingConfigurer,
                                             SubscribableKafkaMessageSource<String, byte[]> accountQueryKafkaMessageSource) {
        log.info("configureKafkaEventProcessor for process group: {}", AxonKafkaConfig.PROCESSOR_NAME);
        eventProcessingConfigurer.registerSubscribingEventProcessor(
                AxonKafkaConfig.PROCESSOR_NAME,
                configuration -> accountQueryKafkaMessageSource
        );
    }
}
