package com.techelevator.dao.JdbcDao;

import com.techelevator.dao.DaoInterface.SongDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Song;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.services.MappingServices.SongMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSongDao implements SongDao {

	private final JdbcTemplate jdbcTemplate;
	private final SongMapper songMapper;

	public JdbcSongDao(JdbcTemplate jdbcTemplate, SongMapper songMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.songMapper = songMapper;
	}

	/**
	 * Retrieves all songs from the database.
	 *
	 * @return a list of songs
	 * @throws DaoException if there is an issue connecting to the server or database
	 */
	@Override
	public List<Song> getAllSongs() {
		List<Song> songs = new ArrayList<>();
		Song song = new Song();
		String sql = "SELECT song_id, song_name, artist, genre, user_genre, spotify_id FROM song";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				song = songMapper.mapRowToSong(results);
				songs.add(song);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return songs;
	}

	@Override
	public Song getSongByID(int songID) {
		Song song = new Song();
		String sql = "SELECT song_id, song_name, artist, genre, user_genre, spotify_id FROM song WHERE song_id = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, songID);
			if (results.next()) {
				song = songMapper.mapRowToSong(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return song;
	}

	/**
	 * Retrieves a song from the database based on its name.
	 *
	 * @param songName the name of the song to retrieve
	 * @return the song matching the given name, or an empty song if no match found
	 * @throws DaoException if there is an error with the database connection
	 */
	@Override
	public Song getSongByName(String songName) {
		Song song = new Song();
		String sql = "SELECT song_id, song_name, artist, genre, user_genre, spotify_id FROM song WHERE song_name = ?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, songName);
			if (results.next()) {
				song = songMapper.mapRowToSong(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return song;
	}
	/**
	 * Creates a new song in the database.
	 *
	 * @param song the song object containing the details of the song to be created
	 * @return the newly created song object
	 * @throws DaoException if there is an error with the database connection
	 */
	@Override
	public Song createSong(Song song) {
		Song newSong = new Song();
		newSong.setSong_name(song.getSong_name());
		newSong.setArtist(song.getArtist());
		newSong.setGenre(song.getGenre());
		newSong.setUser_genre(song.getUser_genre());
		newSong.setSpotify_id(song.getSpotify_id());

		String sql = "INSERT INTO song (song_name, artist, genre, user_genre, spotify_id) VALUES (?, ?, ?, ?, ?)";

		try {
			jdbcTemplate.update(sql, newSong.getSong_name(), newSong.getArtist(), newSong.getGenre(), newSong.getUser_genre(), newSong.getSpotify_id());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return getSongByName(newSong.getSong_name());
	}
	/**
	 * Updates the genre of a song in the database.
	 *
	 * @param song the song object containing the updated details of the song
	 * @return the updated song object
	 * @throws DaoException if there is an error with the database connection
	 */
	@Override
	public Song updateSong(Song song) {
		Song newSong = new Song();
		newSong.setSong_name(song.getSong_name());
		newSong.setArtist(song.getArtist());
		newSong.setGenre(song.getGenre());
		newSong.setUser_genre(song.getUser_genre());
		newSong.setSpotify_id(song.getSpotify_id());

		String sql = "UPDATE song SET song_name = ?, artist = ?, genre = ?, user_genre = ?, spotify_id = ? WHERE song_id = ?";

		try {
			jdbcTemplate.update(sql, newSong.getSong_name(), newSong.getArtist(), newSong.getGenre(), newSong.getUser_genre(), newSong.getSpotify_id(), song.getSong_id());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to server or database", e);
		}
		return getSongByName(newSong.getSong_name());
	}
}
