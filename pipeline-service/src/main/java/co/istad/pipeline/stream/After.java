package co.istad.pipeline.stream;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class After {
    @JsonProperty("Value")
    @JsonAlias({"value", "VALUE"})
    private RecordXml value;
}