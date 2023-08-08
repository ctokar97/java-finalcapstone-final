package com.techelevator.services.MappingServices;

import com.techelevator.model.Playlist;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class PlaylistMapper {

	/**
	 * Maps a SQL row to a Playlist object.
	 *
	 * @param rowset The SqlRowSet object containing the SQL row data.
	 * @return The Playlist object mapped from the SQL row.
	 */
	public Playlist mapRowToPlaylist(SqlRowSet rowset) {
		Playlist playlist = new Playlist();
		playlist.setPlaylist_id(rowset.getInt("playlist_id"));
		playlist.setParty_id(rowset.getInt("party_id"));
		playlist.setPlaylist_name(rowset.getString("playlist_name"));
		return playlist;
	}
}
