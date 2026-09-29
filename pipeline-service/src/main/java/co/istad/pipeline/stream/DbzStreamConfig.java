//package co.istad.pipeline.stream;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import oracle1.CORE_BANKING.RECORD_XML.Envelope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import tools.jackson.databind.ObjectMapper;
//
//import java.util.function.Consumer;
//import java.util.function.Function;
//
//@Configuration
//public class DbzStreamConfig {
//    @Bean
//    public Consumer<Message<Envelope>> captureDebeziumRecordXml(){
//        return record -> {
//            System.out.println("Record"+ record.getPayload());
//        };
//    }
//
//
//    @Bean
//    public Function<Message<Object>, Record> consumeDbzEvent(ObjectMapper objectMapper) {
//        return record -> {
//            try {
//                DebeziumEnvelope<Record> capturedRecord =
//                        objectMapper.convertValue(
//                                record.getPayload(),
//                                new TypeReference<DebeziumEnvelope<Record>>() {}
//                        );
//                return switch (capturedRecord.getOp()) {
//                    case "r", "c" -> {
//                        System.out.println("Prepare to insert new record");
//                        Record after = capturedRecord.getAfter();
//                        System.out.println(after.getXmldata().getName());
//                        yield after;
//                    }
//                    case "u" -> {
//                        System.out.println("Prepare to update existing record");
//                        Record after = capturedRecord.getAfter();
//                        System.out.println("Updated: " + after.getXmldata().getName());
//                        yield after;
//                    }
//                    case "d" -> {
//                        System.out.println("Prepare to delete existing record");
//                        System.out.println("Delete ID = " + capturedRecord.getBefore().getRecid());
//                        yield capturedRecord.getBefore();
//                    }
//                    default -> throw new IllegalStateException("Invalid Operation..!");
//                };
//            } catch (JsonProcessingException e) {
//                System.out.println("Error deserialized");
//                throw new RuntimeException("Error deserialized");
//            }
//        };
//    }
//
//
//}
