package in.karatube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

public class Serializer {
    private String type;
    private ObjectMapper mapper;

    public Serializer(String type) {
        this.type = type;
        if (type.equals("xml")) {
            this.mapper = getXmlMapper();
        } else if (type.equals("yml")) {
            this.mapper = getYamlMapper();
        } else {
            this.mapper = getJsonMapper();
        }
    }

    public Serializer() {
        this("json");
    }

    public String get(Object o) throws IOException {
        if (type.equals("csv")) {
            return getCsvMapper(o);
        }
        return this.mapper.writeValueAsString(o);
    }

    public ObjectMapper getXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
        return mapper;
    }

    public ObjectMapper getJsonMapper() {
        return new ObjectMapper();
    }

    public ObjectMapper getYamlMapper() {
        return new ObjectMapper(new YAMLFactory());
    }

    public String getCsvMapper(Object o) throws JsonProcessingException {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        CsvSchema csvSchema;
        csvSchema = csvMapper.schemaFor(Track.class).withHeader();

        return csvMapper.writer(csvSchema)
                .writeValueAsString(o);
    }
}
