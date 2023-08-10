package com.techelevator.dao.DaoInterface;

import com.techelevator.model.Playlist;
import com.techelevator.model.Song;

import java.util.List;

public interface PlaylistDao {

	List<Playlist> getAllPlaylists();

	Playlist getPlaylistByName(String name);

	List<Song> getSongsInPlaylist(int playlistId);

	Playlist createPlaylist(Playlist playlist);

	Playlist updatePlaylist(Playlist playlist);
}
