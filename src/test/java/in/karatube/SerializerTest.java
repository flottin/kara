package in.karatube;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class SerializerTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SerializerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SerializerTest.class );
    }

    @DataProvider
    public static Object[][] dataProviderPeople() {
        return new Object[][]{
                {"John", "Karmac"},
                {"Bob", "Dylan"},
                {"Dexter", "Holland"}
        };
    }


    @UseDataProvider(value = "dataProviderPeople")
    public void testSerializer() throws IOException, URISyntaxException {
        String dirpath = "./";

        // For a simple file system with Unix-style paths and behavior:
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());

        Serializer ser = new Serializer(fs);
        String expected = "Un texte à mettre dans le fichier en mémoire !";
        Path path = ser.save(expected, "test"        );
        Iterator<String> it =         Files.readAllLines(path).iterator();
        String actual = "";

        while(it.hasNext()){
            actual += it.next();
        }

        assertTrue( actual.equals(expected) );
        assertTrue( actual.equals(expected) );
        assertTrue( actual.equals(expected) );
        assertTrue( actual.equals(expected) );
        assertTrue( actual.equals(expected) );

//        boolean b = Pattern.matches("data/([0-9]{2})/([0-9]{2})/([0-9]{2})/export.([0-9]{2}).test",
  //              path.toString());
        assertTrue( true );
    }


    private void listDirectory(Path path) throws IOException {
        System.out.println(path.getFileName());
        DirectoryStream<Path> stream = Files.newDirectoryStream(path.getFileName());
        try {
            Iterator<Path> iterator = stream.iterator();
            while(iterator.hasNext()) {
                Path p = iterator.next();
                System.out.println(p);
            }
        } finally {
            stream.close();
        }
    }

}
