package in.karatube;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

    /**
     * Rigourous Test :-)
     */
    public void testSerializer()
    {
        Serializer ser = new Serializer("xml");

        //ser.

        assertTrue( true );
    }
}
