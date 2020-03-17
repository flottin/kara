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
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Serializer {

    private String type;

    private ObjectMapper mapper;

    private FileSystem fs;

    public final String PATH_DATA_DIR = "data";

    public Serializer(FileSystem fs){
        this.fs = fs;
    }

    public Serializer setType(String type) {

        this.type = type;
        if (type.equals("xml")) {
            this.mapper = getXmlMapper();
        } else if (type.equals("yml")) {
            this.mapper = getYamlMapper();
        } else {
            this.mapper = getJsonMapper();
        }
        return this;
    }

    /**
     * get the string serialize
     * @param o : the object to serialize
     * @param save : save in file if true
     * @return
     * @throws IOException
     */
    public String get(Object o, boolean save) throws IOException {
        String res;
        if (type.equals("csv")) {
            res = getCsvMapper(o);
        } else {
            res = this.mapper.writeValueAsString(o);
        }

        if (save && !res.isEmpty()){

           save(res, this.type);
        }

        return res;
    }

    private ObjectMapper getXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
        return mapper;
    }

    private ObjectMapper getJsonMapper() {
        return new ObjectMapper();
    }

    private ObjectMapper getYamlMapper() {
        return new ObjectMapper(new YAMLFactory());
    }

    private String getCsvMapper(Object o) throws JsonProcessingException {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        CsvSchema csvSchema;
        csvSchema = csvMapper.schemaFor(Track.class).withHeader();

        return csvMapper.writer(csvSchema)
                .writeValueAsString(o);
    }

    public Path getPath(String extension) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HHmmss");
        String formattedDate = today.format(myFormatObj);

        return this.fs.getPath(
            PATH_DATA_DIR,
            String.valueOf(today.getYear()),
            String.valueOf(today.getMonthValue()),
            String.valueOf(today.getDayOfMonth()),
            String.format("export.%s.%s", formattedDate, extension)
        );
    }

    public Path save(String txt, String extension) throws IOException{
        Path path = getPath(extension);
        if (Files.notExists(path.getParent())){
            Files.createDirectories(path.getParent());
        }
        Files.write(path, txt.getBytes());
        return path;
    }
}