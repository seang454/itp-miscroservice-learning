package co.istad.pipeline.stream;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StreamConfig {

    // Supplier for producing message into kafka topic
    // Function for processing message and send to destination kafka topic
    // Consumer for consuming message from kafka topic

    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<GenericRecord> processUser() {

        return record -> {
            try {
                if (record == null) return;

                GenericRecord after = (GenericRecord) record.get("after");
                if (after == null) return;

                Object xmlDataObj = after.get("XMLDATA");
                if (xmlDataObj == null) return;

                String xml;
                if (xmlDataObj instanceof GenericRecord xmlDataRecord) {
                    xml = xmlDataRecord.get("string").toString();
                } else if (xmlDataObj instanceof CharSequence cs) {
                    xml = cs.toString();
                } else {
                    log.warn("Unknown XMLDATA type: {}", xmlDataObj.getClass());
                    return;
                }

                User user = xmlMapper.readValue(xml, User.class);
                log.info("CDC USER => {}", user);
                System.out.println("mapped XML: " + user.getName());
                System.out.println("mapped XML: " + user.getRole());

            } catch (Exception e) {
                log.error("CDC processing failed", e);
            }

        };
    }







    @Bean
    public Function<GenericRecord, Product> processProductDebezium(){
        return record -> {


            if (record == null) {
                System.out.println("Received tombstone event");
                return null;
            }

            GenericRecord after = (GenericRecord) record.get("after");


            if (after == null) {
                System.out.println("Received DELETE event");
                return null;
            }

            Object codeObj = after.get("code");
            Object qtyObj = after.get("qty");

            String code = codeObj != null ? codeObj.toString() : "N/A";
            Integer qty = qtyObj != null ? ((Number) qtyObj).intValue() : 0;

            System.out.println("Debezium -> Product Code: " + code);
            System.out.println("Debezium -> Product Qty: " + qty);

            return new Product();
        };
    }

    @Bean
    public Function<Product, Product> processProductDetail(){
        return product -> {

            System.out.println("old product: " + product.getCode());
            System.out.println("old product: " + product.getQty());

            product.setCode("ISTAD - " + product.getCode());

            return product;
        };
    }
    @Bean
    public Consumer<Product> processProduct() {
        return product -> {
            System.out.println("obj product: " + product.getCode());
            System.out.println("obj product: " + product.getQty());
        };
    }

    // A simple processor: Takes a string, makes it uppercase, and sends it on
    @Bean
    public Consumer<String> processMessage() {
        return input -> {
            System.out.println("Processing: " + input);
        };
    }


}

