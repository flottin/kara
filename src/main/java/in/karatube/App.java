package in.karatube;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws JsonProcessingException {
		Tracks tracks = new Tracks();
		tracks.add("Mon premier Titre", "Artist premier", false);
		tracks.add("Mon troisième Titre", "Artist troisieme", true);
		tracks.add("Mon quatrième Titre", "Artist quatrième",true);

		try{
			System.out.println( new Serializer("xml").get(tracks, true) );
			System.out.println( new Serializer("json").get(tracks, true) );
			System.out.println( new Serializer("yml").get(tracks, true) );
			String csv = new Serializer("csv").get(tracks.getList(), true);
			System.out.println( csv );

		} catch (IOException e){
			System.out.print(e.getMessage());
		}
    }
}
