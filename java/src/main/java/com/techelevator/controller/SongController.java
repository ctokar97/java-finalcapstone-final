package com.techelevator.controller;

import com.techelevator.dao.DaoInterface.SongDao;
import com.techelevator.model.Song;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/song")
public class SongController {

	private final SongDao songDao;


	public SongController(SongDao songDao) {
		this.songDao = songDao;
	}

	/**
	 * This method retrieves all songs from the songDao and returns them as a list of songs.
	 *
	 * @return ResponseEntity<List < Song>> - a ResponseEntity containing a list of songs retrieved from the songDao
	 */
	@GetMapping
	public ResponseEntity<List<Song>> getAllSongs() {
		List<Song> songs;
		try {
			songs = songDao.getAllSongs();
			return ResponseEntity.ok(songs);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * This method retrieves a song from the songDao based on the given song name and returns it as a ResponseEntity.
	 *
	 * @param songName - the name of the song to retrieve
	 * @return ResponseEntity<Song> - a ResponseEntity containing the song retrieved from the songDao based on the given song name
	 */
	@GetMapping("/{songName}")
	public ResponseEntity<Song> getSongByName(@PathVariable String songName) {
		Song song;
		try {
			song = songDao.getSongByName(songName);
			return ResponseEntity.ok(song);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * This method creates a new song in the songDao based on the given song object and returns it as a ResponseEntity.
	 *
	 * @param song - the song object to create
	 * @return ResponseEntity<Song> - a ResponseEntity containing the newly created song in the songDao
	 */
	@PostMapping
	public ResponseEntity<Song> createSong(@RequestBody Song song) {
		Song songToCreate;
		try {
			songToCreate = songDao.createSong(song);
			return ResponseEntity.created(new URI("/song/" + songToCreate.getSong_id())).body(songToCreate);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Updates the genre of a song.
	 *
	 * @param song The updated song with the new genre.
	 * @return A ResponseEntity containing the updated song with the new genre.
	 */
	@PutMapping("/{songId}")
	public ResponseEntity<Song> updateSong(@PathVariable Integer songId, @RequestBody Song song) {
		Song updatedSong;
		try {
			if (songId != song.getSong_id()) {
				return ResponseEntity.badRequest().body(null);
			}
			updatedSong = songDao.updateSong(song);
			return ResponseEntity.ok(updatedSong);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
