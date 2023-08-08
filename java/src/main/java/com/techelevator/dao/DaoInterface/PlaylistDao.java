package com.techelevator.dao.DaoInterface;

import com.techelevator.model.Playlist;

import java.util.List;

public interface PlaylistDao {

	List<Playlist> getAllPlaylists();

	Playlist getPlaylistByName(String name);

	Playlist createPlaylist(Playlist playlist);

	Playlist updatePlaylist(Playlist playlist);
}
