package co.istad.pipeline.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class RecordXml {


    @JsonProperty("RECID")
    private String recid;

    @JsonProperty("XMLDATA")
    private Data xmldata;
}