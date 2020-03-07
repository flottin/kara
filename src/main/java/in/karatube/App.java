package in.karatube;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private List<Track> list = new ArrayList<Track>();
	
	
	public void add(int id, String title) {
		
		Track track = new Track();
    	track.id = id;
    	track.title = title;
    	
    	this.list.add(track);
	}
	
    public static void main( String[] args )
    {
    	App app = new App();
    	
    	
    	app.add(1, "Mon premier Titre");
    	app.add(2, "Mon second Titre");
    	
    	System.out.println( "Liste de Single karatube!" );
    	
    	ListIterator<Track> it = app.list.listIterator();
		while(it.hasNext()){
			Track track = it.next();
			System.out.println( track.title );
		}

    }
}


