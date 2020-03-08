package in.karatube;

import java.io.IOException;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		Tracks tracks = new Tracks();
		tracks.add("Mon premier Titre");
		tracks.add("Mon troisième Titre", true);
		tracks.add("Mon quatrième Titre", true);

		try{
			System.out.println( new Serializer("xml").get(tracks) );
			System.out.println( new Serializer("json").get(tracks) );
			System.out.println( new Serializer("yml").get(tracks) );
			System.out.println( new Serializer("csv").get(tracks.getList()) );
		} catch (IOException e){
			System.out.print(e.getMessage());
		}
    }
}
