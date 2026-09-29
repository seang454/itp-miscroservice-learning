package co.istad.pipeline.stream;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.transform.Source;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebeziumEnvelope<T> {
    private String op;  // "c", "r", "u", "d"
    private T before;
    private T after;
}
