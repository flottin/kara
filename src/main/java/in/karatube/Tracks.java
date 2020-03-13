package in.karatube;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.*;

@JsonRootName("tracks")
public class Tracks
{
	/**
	 * id of track
	 */
	private int id = 0;

	@JsonProperty("track")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<Track> trackList = new ArrayList<>();

	/**
	 *
	 * @param title: title of track
	 * @param artist: artist of track
	 * @param published: true if published on front end
	 */
	public void add(String title, String artist,  boolean published) {
		this.id ++;
//		Track track = new Track();
//
//		track.setId(this.id);
//		track.setTitle(title);
//		track.setArtist(artist);
//		track.setPublished(published);
//		trackList.add(track);
	}

	public List<Track> getList(){
		return trackList;
	}

}
