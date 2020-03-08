package in.karatube;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		Tracks tracks = new Tracks();
		tracks.add("Mon premier Titre", "Artist premier", false);
		tracks.add("Mon troisième Titre", "Artist troisieme", true);
		tracks.add("Mon quatrième Titre", "Artist quatrième",true);

		try{
			String csv = new Serializer("csv").get(tracks.getList());
			System.out.println( new Serializer("xml").get(tracks) );
			System.out.println( new Serializer("json").get(tracks) );
			System.out.println( new Serializer("yml").get(tracks) );
			System.out.println( csv );

			save(csv, filename());

		} catch (IOException e){
			System.out.print(e.getMessage());
		}



    }
    public static String filename(){
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
		String formattedDate = myDateObj.format(myFormatObj);
    	return "export." + formattedDate + ".csv";
	}

	public static void save(String txt, String filename) throws IOException{
		FileOutputStream fos = new FileOutputStream(filename);
		fos.write(txt.getBytes());
		fos.flush();
		fos.close();
		System.out.println("File "  + filename + " saved !");
	}
}
