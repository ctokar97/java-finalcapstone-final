package com.techelevator.dao.JdbcDao;

import com.techelevator.dao.DaoInterface.PlaylistDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Playlist;
import com.techelevator.services.MappingServices.PlaylistMapper;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlaylistDao implements PlaylistDao {

	private final JdbcTemplate jdbcTemplate;
	private final PlaylistMapper playlistMapper;

	public JdbcPlaylistDao(JdbcTemplate jdbcTemplate, PlaylistMapper playlistMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.playlistMapper = playlistMapper;
	}


	/**
	 * Retrieves all playlists from the database.
	 *
	 * @return a list of Playlist objects representing all playlists in the database.
	 * @throws DaoException if unable to connect to the server or database.
	 */
	@Override
	public List<Playlist> getAllPlaylists() {
		List<Playlist> playlists = new ArrayList<>();
		Playlist playlist = new Playlist();
		String sql = "SELECT * FROM playlist";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				playlist = playlistMapper.mapRowToPlaylist(results);
				playlists.add(playlist);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return playlists;
	}

	/**
	 * Retrieves a playlist from the database by its name.
	 *
	 * @param name the name of the playlist to retrieve
	 * @return the playlist with the given name, or an empty playlist if not found
	 * @throws DaoException if there is an error connecting to the database
	 */
	@Override
	public Playlist getPlaylistByName(String name) {
		String sql = "SELECT * FROM playlist WHERE playlist_name = ?";
		Playlist playlist = null;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
			if ( results.next() ) {
				playlist = playlistMapper.mapRowToPlaylist(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return playlist;
	}

	/**
	 * Creates a new playlist in the database.
	 *
	 * @param playlist the playlist object to be created
	 * @return the created playlist object
	 * @throws DaoException if unable to connect to the database server or database
	 */
	@Override
	public Playlist createPlaylist(Playlist playlist) {
		Playlist newPlaylist = new Playlist();
		newPlaylist.setPlaylist_name(playlist.getPlaylist_name());
		newPlaylist.setParty_id(playlist.getParty_id());

		String sql = "INSERT INTO playlist (playlist_name, party_id) VALUES (?, ?)";

		try {
			jdbcTemplate.update(sql, newPlaylist.getPlaylist_name(), newPlaylist.getParty_id());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return getPlaylistByName(newPlaylist.getPlaylist_name());
	}

	/**
	 * Updates a playlist in the database.
	 *
	 * @param playlist the playlist object containing the updated information
	 * @return the updated playlist
	 * @throws DaoException if there is an error connecting to the database
	 */
	@Override
	public Playlist updatePlaylist(Playlist playlist) {
		Playlist newPlaylist = new Playlist();
		newPlaylist.setPlaylist_id(playlist.getPlaylist_id());
		newPlaylist.setPlaylist_name(playlist.getPlaylist_name());
		newPlaylist.setParty_id(playlist.getParty_id());

		String sql = "UPDATE playlist SET playlist_name = ?, party_id = ? WHERE playlist_id = ?";

		try {
			jdbcTemplate.update(sql, newPlaylist.getPlaylist_name(), newPlaylist.getParty_id(), newPlaylist.getPlaylist_id());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return getPlaylistByName(newPlaylist.getPlaylist_name());
	}
}
