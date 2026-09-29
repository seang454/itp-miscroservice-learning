package co.istad.pipeline.stream;

public class UserXmlDeserializer extends XmlStringDeserializer<XmlData> {

    public UserXmlDeserializer() {
        super(XmlData.class);
    }
}
