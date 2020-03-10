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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Serializer {
    private String type;
    private ObjectMapper mapper;

    public final String PATH_DATA_DIR = "data";

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

    public String filename(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HHmmss");
        String formattedDate = myDateObj.format(myFormatObj);
        return String.format("export.%s.%s", formattedDate, this.type);
    }

    public void save(String txt, String filename) throws IOException{
        LocalDateTime myDateObj = LocalDateTime.now();
        ArrayList<String> pathList=new ArrayList<>();
        pathList.add(PATH_DATA_DIR);
        pathList.add(String.valueOf(myDateObj.getYear()));
        pathList.add(String.valueOf(myDateObj.getMonthValue()));
        pathList.add(String.valueOf(myDateObj.getDayOfMonth()));
        String path = String.join("/", pathList );
        File file = new File(path);
        boolean dirCreated = file.exists();
        if (!dirCreated){
            dirCreated = file.mkdirs();
        }
        if (dirCreated){
            pathList.add(filename());
            String pathFile = String.join("/", pathList );
            FileOutputStream fos = new FileOutputStream( pathFile );
            fos.write(txt.getBytes());
            fos.flush();
            fos.close();
            System.out.println("File "  + filename + " saved !");
        }
    }
}
