package co.istad.pipeline.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class XmlData {
    @JsonProperty("string")
    private String string;
}
