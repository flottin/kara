package in.karatube;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;

public class App
{
    public static void main( String[] args ) throws IOException {
		Tracks tracks = new Tracks();
		tracks.add("Mon premier Titre", "Artist premier", false);
		tracks.add("Mon troisième Titre", "Artist troisieme", true);
		tracks.add("Mon quatrième Titre", "Artist quatrième",true);

		try{


			Serializer ser = new Serializer(fs);
			System.out.println( ser.setType("xml").get(tracks, true) );
			System.out.println( ser.setType("json").get(tracks, true) );
			System.out.println( ser.setType("yml").get(tracks, true) );
			String csv = ser.setType("csv").get(tracks.getList(), true);
			System.out.println( csv );

		} catch (IOException | URISyntaxException e){
			System.out.print(e.getMessage());
		}
    }


}
