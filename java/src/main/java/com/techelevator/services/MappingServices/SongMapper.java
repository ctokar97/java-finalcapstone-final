package com.techelevator.services.MappingServices;

import com.techelevator.model.Song;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class SongMapper {
	// Map a SQL RowSet to a Song object

	/**
	 * Maps a single row from a SqlRowSet to a Song object.
	 *
	 * @param rowset The SqlRowSet containing the data for the row.
	 * @return The mapped Song object.
	 */
	public Song mapRowToSong(SqlRowSet rowset) {
		Song song = new Song();
		song.setSong_id(rowset.getInt("song_id"));
		song.setSong_name(rowset.getString("song_name"));
		song.setArtist(rowset.getString("artist"));
		song.setGenre(rowset.getString("genre"));
		song.setUser_genre(rowset.getString("user_genre"));
		return song;
	}
}
