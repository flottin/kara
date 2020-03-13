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
import in.karatube.repository.FileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Serializer {
    private String type;

    private ObjectMapper mapper;

    public final String PATH_DATA_DIR = "data";

    private FileSystem fs;

    public Serializer(FileSystem fs){

    }

    public Serializer setType(String type) {
        this.fs = fs;
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
            this.save(res, filename());
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

    private String filename(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HHmmss");
        String formattedDate = myDateObj.format(myFormatObj);
        return String.format("export.%s.%s", formattedDate, this.type);
    }

    private String getPath() throws IOException {
        LocalDateTime today = LocalDateTime.now();
        String path = String.format(
                "%s/%d/%d/%d",
                PATH_DATA_DIR,
                today.getYear(),
                today.getMonthValue(),
                today.getDayOfMonth()
        );
        File file = new File(path);
        boolean dirCreated = file.exists();
        if (!dirCreated){
            dirCreated = file.mkdirs();
        }
        if (dirCreated) {
            return path;
        }
        throw new IOException("getPath Error");
    }

    private void save(String txt, String filename) throws IOException{


        String path = getPath();
        String pathFile = String.format("%s/%s", path, filename()) ;
        FileOutputStream fos = new FileOutputStream( pathFile );
        fos.write(txt.getBytes());
        fos.flush();
        fos.close();
        System.out.println("File "  + filename + " saved !");
    }
}
