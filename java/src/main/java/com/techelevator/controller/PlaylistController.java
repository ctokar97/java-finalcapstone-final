package com.techelevator.controller;

import com.techelevator.dao.DaoInterface.PlaylistDao;
import com.techelevator.model.Playlist;
import com.techelevator.model.Song;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/playlist")
public class PlaylistController {

	private final PlaylistDao playlistDao;

	public PlaylistController(PlaylistDao playlistDao) {
		this.playlistDao = playlistDao;
	}

	@GetMapping
	public ResponseEntity<List<Playlist>> getAllPlaylists() {
		List<Playlist> playlists;
		try {
			playlists = playlistDao.getAllPlaylists();
			if ( playlists.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(playlists);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@GetMapping("/{playlistName}")
	public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String playlistName) {
		Playlist playlist;
		try {
			playlist = playlistDao.getPlaylistByName(playlistName);
			return ResponseEntity.ok(playlist);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Retrieves the list of songs in a playlist identified by the given playlist ID.
	 *
	 * @param playlistId the ID of the playlist
	 * @return ResponseEntity<List < Song>> a ResponseEntity containing a list of Song objects if successful,
	 *         or a ResponseEntity with a bad request if an exception occurs
	 */
	@GetMapping("/{playlistId}/songs")
	public ResponseEntity<List<Song>> getSongsInPlaylist(@PathVariable Integer playlistId) {
		List<Song> songs;
		try {
			songs = playlistDao.getSongsInPlaylist(playlistId);
			return ResponseEntity.ok(songs);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping
	public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
		Playlist createdPlaylist;
		try {
			createdPlaylist = playlistDao.createPlaylist(playlist);
			return ResponseEntity.created(new URI("/playlist/" + createdPlaylist.getPlaylist_id())).body(createdPlaylist);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/{playlistId}")
	public ResponseEntity<Playlist> updatePlaylist(@PathVariable Integer playlistId, @RequestBody Playlist playlist) {
		Playlist updatedPlaylist;
		try {
			if(playlistId != playlist.getPlaylist_id()) {
				return ResponseEntity.badRequest().body(null);
			}
			updatedPlaylist = playlistDao.updatePlaylist(playlist);
			return ResponseEntity.ok(updatedPlaylist);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}


}